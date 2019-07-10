package com.vkopendoh.webapp.model;

import java.util.List;

public class RefDateDescription {
    private String title;

    private String startDate;

    private String endDate;

    private List<String> description;

    public RefDateDescription(String title, String startDate, String endDate, List<String> description) {
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.description = description;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public void setContent(List<String> description) {
        this.description = description;
    }
}
