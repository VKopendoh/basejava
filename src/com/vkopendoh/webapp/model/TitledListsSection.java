package com.vkopendoh.webapp.model;

import java.util.List;
import java.util.Objects;

public class TitledListsSection implements Section<List<TitledListField>> {

    private List<TitledListField> content;

    public TitledListsSection(List<TitledListField> content) {
        this.content = content;
    }

    public List<TitledListField> getContent() {
        return content;
    }

    public void setContent(List<TitledListField> content) {
        this.content = content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TitledListsSection that = (TitledListsSection) o;
        return Objects.equals(content, that.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(content);
    }
}
