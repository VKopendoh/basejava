package com.vkopendoh.webapp.model;

import java.util.List;
import java.util.Objects;

public class Organization {
    private Link title;

    private List<Experience> content;

    public Organization(String name, String url, List<Experience> content) {
        Objects.requireNonNull(content, "list content in Organization must not be null");
        this.title = new Link(name, url);
        this.content = content;
    }

    public void setTitle(Link title) {
        this.title = title;
    }

    public void setContent(List<Experience> content) {
        this.content = content;
    }

    public Link getTitle() {
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
                content.equals(that.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, content);
    }
}
