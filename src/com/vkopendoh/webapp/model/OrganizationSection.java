package com.vkopendoh.webapp.model;

import java.util.List;
import java.util.Objects;

public class OrganizationSection implements Section<List<Organization>> {

    private List<Organization> content;

    public OrganizationSection(List<Organization> content) {
        Objects.requireNonNull(content, "organizations must not be null");
        this.content = content;
    }

    public List<Organization> getContent() {
        return content;
    }

    public void setContent(List<Organization> content) {
        this.content = content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrganizationSection that = (OrganizationSection) o;
        return content.equals(that.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(content);
    }
}
