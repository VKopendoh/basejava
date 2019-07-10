package com.vkopendoh.webapp.model;

import java.util.List;
import java.util.Objects;

public class TextListSection implements Section<List<String>> {

    private List<String> content;

    public TextListSection(List<String> content) {
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
        return Objects.equals(content, that.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(content);
    }
}
