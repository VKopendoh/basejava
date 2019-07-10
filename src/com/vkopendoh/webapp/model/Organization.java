package com.vkopendoh.webapp.model;

import java.util.List;
import java.util.Objects;

public class Organization {
    private String title;

    private List<Experience> content;

    public Organization(String title, List<Experience> content) {
        this.title = title;
        this.content = content;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(List<Experience> content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public List<Experience> getContent() {
        return content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Organization that = (Organization) o;
        return Objects.equals(title, that.title) &&
                Objects.equals(content, that.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, content);
    }
}
