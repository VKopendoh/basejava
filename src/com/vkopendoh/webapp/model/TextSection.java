package com.vkopendoh.webapp.model;

public class TextSection implements Section<String>{
    private SectionType SectionType;
    private String content;

    public TextSection(SectionType sectionType, String content) {
        this.SectionType = sectionType;
        this.content = content;
    }

    public SectionType getSectionType() {
        return SectionType;
    }

    public void setSectionTypee(SectionType SectionType) {
        this.SectionType = SectionType;
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
