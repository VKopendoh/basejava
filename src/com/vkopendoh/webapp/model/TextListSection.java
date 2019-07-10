package com.vkopendoh.webapp.model;

import java.util.List;

public class TextListSection implements Section<List<String>> {
    private SectionType sectionType;

    private List<String> content;

    public TextListSection(SectionType sectionType, List<String> content) {
        this.sectionType = sectionType;
        this.content = content;
    }

    public SectionType getSectionType() {
        return sectionType;
    }

    public void setSectionType(SectionType sectionType) {
        this.sectionType = sectionType;
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
