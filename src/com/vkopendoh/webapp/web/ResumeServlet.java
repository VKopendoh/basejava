package com.vkopendoh.webapp.web;

import com.vkopendoh.webapp.Config;
import com.vkopendoh.webapp.exception.NotExistStorageException;
import com.vkopendoh.webapp.model.*;
import com.vkopendoh.webapp.storage.Storage;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ResumeServlet extends HttpServlet {
    Storage storage = Config.get().getStorage();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String uuid = request.getParameter("uuid");
        String fullName = request.getParameter("fullName");
        Resume r = storage.get(uuid);
        r.setFullName(fullName);
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
                case ACHIEVEMENT:
                case QUALIFICATIONS:
                    String[] content = request.getParameterValues(type.name());
                    List<String> newContent = new ArrayList<>();
                    for (String text : content) {
                        if (text != null && text.trim().length() != 0) {
                            newContent.add(text);
                        }
                    }
                    if (newContent != null) {
                        r.addSection(type, new TextListSection(newContent));
                    } else {
                        r.getSections().remove(type);
                    }
            }
        }
        storage.update(r);
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
                try {
                    r = storage.get(uuid);
                } catch (NotExistStorageException e) {
                    storage.save(new Resume(uuid, ""));
                    r = storage.get(uuid);
                }

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
