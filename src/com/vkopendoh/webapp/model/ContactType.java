package com.vkopendoh.webapp.model;

public enum ContactType {
    PHONE("Тел"),
    SKYPE("Skype"),
    EMAIL("Почта"),
    LINKEDIN(""),
    GITHUB(""),
    STACKOVERFLOW(""),
    HOMEPAGE("");

    private String title;

    ContactType(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
