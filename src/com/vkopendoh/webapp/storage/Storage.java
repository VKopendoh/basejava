package com.vkopendoh.webapp.storage;

import com.vkopendoh.webapp.model.Resume;

public interface Storage {
    public void clear();

    public void save(Resume resume);

    public Resume get(String uuid);

    public void delete(String uuid);

    public void update(Resume resume);

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll();

    public int size();
}
