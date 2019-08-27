package com.vkopendoh.webapp.web;

import com.vkopendoh.webapp.Config;
import com.vkopendoh.webapp.model.Resume;
import com.vkopendoh.webapp.storage.Storage;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

public class ResumeServlet extends HttpServlet {
    Storage storage;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");

        String uuid = request.getParameter("uuid");
        storage = Config.get().getStorage();
        StringBuilder writer = new StringBuilder(" <table border=\"1\">");
        writer.append(addTableRowWithData("Full Name", "UUID"));
        if (uuid != null) {
            Resume resume = storage.get(uuid);
            writer.append(addTableRowWithData(resume.getFullName(), resume.getUuid()));
        } else {
            ArrayList<Resume> resumes = (ArrayList<Resume>) storage.getAllSorted();
            for (Resume resume : resumes) {
                writer.append(addTableRowWithData(resume.getFullName(), resume.getUuid()));
            }
        }
        writer.append(" </table>");
        response.getWriter().write(writer.toString());

    }

    private String addTableRowWithData(String fullName, String uuid) {
        return "<tr>" +
                "<td>" + fullName + "</td>" +
                "<td>" + uuid + "</td>";
    }
}
