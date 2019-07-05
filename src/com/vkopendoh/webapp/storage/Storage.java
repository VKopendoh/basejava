package com.vkopendoh.webapp.storage;

import com.vkopendoh.webapp.model.Resume;

import java.util.List;

public interface Storage {
    void clear();

    void save(Resume resume);

    Resume get(String key);

    void delete(String key);

    void update(Resume resume);

    List<Resume> getAllSorted();

    int size();
}
