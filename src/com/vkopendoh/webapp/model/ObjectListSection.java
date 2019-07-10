package com.vkopendoh.webapp.model;

import java.util.List;

public class ObjectListSection implements Section<List<RefDateDescription>>{
    private SectionType sectionType;

    private List<RefDateDescription> content;

    public ObjectListSection(SectionType sectionType, List<RefDateDescription> content) {
        this.sectionType = sectionType;
        this.content = content;
    }

    @Override
    public List<RefDateDescription> getContent() {
        return content;
    }

    @Override
    public void setContent(List<RefDateDescription> content) {
        content = content;
    }
}
