package com.vkopendoh.webapp.web;

import com.vkopendoh.webapp.Config;
import com.vkopendoh.webapp.model.*;
import com.vkopendoh.webapp.storage.Storage;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ResumeServlet extends HttpServlet {
    Storage storage = Config.get().getStorage();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String uuid = request.getParameter("uuid");
        String fullName = request.getParameter("fullName");

        Resume r = new Resume(uuid, fullName);

        for (ContactType type : ContactType.values()) {
            String value = request.getParameter(type.name());
            if (value != null && value.trim().length() != 0) {
                r.addContact(type, value);
            } else {
                r.getContacts().remove(type);
            }
        }

        for (SectionType type : SectionType.values()) {
            switch (type) {
                case PERSONAL:
                case OBJECTIVE:
                    String value = request.getParameter(type.name());
                    if (value != null && value.trim().length() != 0) {
                        r.addSection(type, new TextSection(value));
                    } else {
                        r.getSections().remove(type);
                    }
                    break;
                case ACHIEVEMENT:
                case QUALIFICATIONS:
                    String[] content = request.getParameter(type.name()).split("\r\n");
                    List<String> newContent = new ArrayList<>();
                    if (content != null) {
                        for (String text : content) {
                            if (text != null && text.trim().length() != 0) {
                                newContent.add(text);
                            }
                        }
                        r.addSection(type, new TextListSection(newContent));
                    } else {
                        r.getSections().remove(type);
                    }
                    break;
                case EDUCATION:
                case EXPERIENCE:
                    List<Organization> experienceList = new ArrayList<>();
                    List<Organization> educationList = new ArrayList<>();
                    String[] orgNames = request.getParameterValues(type + "." + "orgName");
                    String[] orgUrls = request.getParameterValues(type + "." + "orgUrl");
                    String[] positions = request.getParameterValues(type + "." + "position");
                    String[] descriptions = request.getParameterValues(type + "." + "description");
                    String[] startDates = request.getParameterValues(type + "." + "startDate");
                    String[] endDates = request.getParameterValues(type + "." + "startDate");
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM");
                    if (orgNames != null) {
                        for (int i = 0; i < orgNames.length; i++) {
                            if (type == SectionType.EXPERIENCE && startDates[i].trim().length() != 0 && endDates[i].trim().length() != 0) {
                                experienceList.add(new Organization(orgNames[i], orgUrls[i],
                                        new Organization.Experience(YearMonth.parse(startDates[i], formatter), YearMonth.parse(endDates[i], formatter),
                                                positions[i], descriptions[i])));
                            } else if (type == SectionType.EDUCATION && startDates[i].trim().length() != 0 && endDates[i].trim().length() != 0) {
                                educationList.add(new Organization(orgNames[i], orgUrls[i],
                                        new Organization.Experience(YearMonth.parse(startDates[i], formatter), YearMonth.parse(endDates[i], formatter),
                                                positions[i], descriptions[i])));
                            }
                            if (!experienceList.isEmpty()) {
                                r.addSection(SectionType.EXPERIENCE, new OrganizationSection(experienceList));
                            }
                            if (!educationList.isEmpty()) {
                                r.addSection(SectionType.EDUCATION, new OrganizationSection(educationList));
                            }
                        }
                    }


                    break;
            }
        }
        if (uuid.isEmpty()) {
            r.setUuid(UUID.randomUUID().toString());
            storage.save(r);
        } else {
            storage.update(r);
        }
        response.sendRedirect("resume");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uuid = request.getParameter("uuid");
        String action = request.getParameter("action");
        if (action == null) {
            request.setAttribute("resumes", storage.getAllSorted());
            request.getRequestDispatcher("/WEB-INF/jsp/list.jsp").forward(request, response);
            return;
        }
        Resume r;
        switch (action) {
            case "delete":
                storage.delete(uuid);
                response.sendRedirect("resume");
                return;
            case "view":
            case "edit":
                r = storage.get(uuid);
                break;
            case "create":
                r = new Resume();
                break;
            default:
                throw new IllegalArgumentException("Action " + action + " is illegal");
        }
        request.setAttribute("resume", r);
        request.getRequestDispatcher(
                ("view".equals(action) ? "/WEB-INF/jsp/view.jsp" : "/WEB-INF/jsp/edit.jsp")
        ).forward(request, response);

    }
}
