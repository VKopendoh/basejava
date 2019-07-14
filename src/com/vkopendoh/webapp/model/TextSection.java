package com.vkopendoh.webapp.model;

import java.util.Objects;

public class TextSection extends Section<String> {
    private static final long serialVersionUID = 1L;

    private String content;

    public TextSection(String content) {
        Objects.requireNonNull(content, "text must not be null");
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TextSection that = (TextSection) o;
        return content.equals(that.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(content);
    }
}
