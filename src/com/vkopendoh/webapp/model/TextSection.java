package com.vkopendoh.webapp.model;

public class TextSection {
    private SectionType SectionType;
    private String content;

    public TextSection(SectionType sectionType, String content) {
        this.SectionType = sectionType;
        this.content = content;
    }

    public SectionType getSectionType() {
        return SectionType;
    }

    public String getContent() {
        return content;
    }

    public void setSectionTypee(SectionType SectionType) {
        this.SectionType = SectionType;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
