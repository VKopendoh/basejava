package com.vkopendoh.webapp.model;

import java.util.List;
import java.util.Objects;

public class TextListSection extends Section<List<String>> {
    private static final long serialVersionUID = 1L;

    private List<String> content;

    public TextListSection() {
    }

    public TextListSection(List<String> content) {
        Objects.requireNonNull(content, "textList must not be null");
        this.content = content;
    }

    public List<String> getContent() {
        return content;
    }

    public void setContent(List<String> content) {
        this.content = content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TextListSection that = (TextListSection) o;
        return content.equals(that.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(content);
    }
}
