package com.vkopendoh.webapp.storage;

import com.vkopendoh.webapp.model.Resume;

import java.util.ArrayList;

public class ListStorage extends AbstractStorage {
    private ArrayList<Resume> storage = new ArrayList<>();

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    protected Resume getByIndex(int index) {
        return storage.get(index);
    }

    @Override
    protected void insert(int index, Resume resume) {
        storage.add(resume);
    }

    @Override
    protected int getIndex(String uuid) {
        return storage.indexOf(new Resume(uuid));
    }

    @Override
    public Resume[] getAll() {
        Resume[] resumes = storage.toArray(new Resume[0]);
        return resumes;
    }

    @Override
    protected void remove(int index) {
        storage.remove(index);
    }
}
