package com.vkopendoh.webapp.model;

public class TextSection implements Section<String> {
    private String content;

    public TextSection(String content) {
        this.content = content;
    }

    @Override
    public String getContent() {
        return content;
    }

    @Override
    public void setContent(String content) {
        content = content;
    }
}
