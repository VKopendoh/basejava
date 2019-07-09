package com.vkopendoh.webapp.model;

import java.util.List;

public class ObjectListSection {
    private SectionType sectionType;

    private List<RefDateDescription> content;

    public ObjectListSection(SectionType sectionType, List<RefDateDescription> content) {
        this.sectionType = sectionType;
        this.content = content;
    }
}
