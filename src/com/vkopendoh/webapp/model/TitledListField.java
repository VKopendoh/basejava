package com.vkopendoh.webapp.model;

import java.util.List;
import java.util.Objects;

public class TitledListField {
    private String title;

    private List<DatesTextField> content;

    public TitledListField(String title, List<DatesTextField> content) {
        this.title = title;
        this.content = content;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(List<DatesTextField> description) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public List<DatesTextField> getContent() {
        return content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TitledListField that = (TitledListField) o;
        return Objects.equals(title, that.title) &&
                Objects.equals(content, that.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, content);
    }
}
