package com.vkopendoh.webapp.model;

import java.util.List;

public class TextListSection {
    private SectionType sectionType;

    private List<String> content;

    public TextListSection(SectionType sectionType, List<String> content) {
        this.sectionType = sectionType;
        this.content = content;
    }

    public SectionType getSectionType() {
        return sectionType;
    }

    public List<String> getContent() {
        return content;
    }

    public void setSectionType(SectionType sectionType) {
        this.sectionType = sectionType;
    }

    public void setContent(List<String> content) {
        this.content = content;
    }
}
