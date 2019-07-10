package com.vkopendoh.webapp.model;

import java.util.List;

public class TextListSection implements Section<List<String>> {

    private List<String> content;

    public TextListSection(List<String> content) {
        this.content = content;
    }

    @Override
    public List<String> getContent() {
        return content;
    }

    @Override
    public void setContent(List<String> content) {
        content = content;
    }
}
