package com.vkopendoh.webapp.storage;

import com.vkopendoh.webapp.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage {
    private List<Resume> storage = new ArrayList<>();

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    protected Resume getByIndex(int index, String uuid) {
        return storage.get(index);
    }

    @Override
    protected void setByIndex(int index, Resume resume) {
        storage.set(index, resume);
    }

    @Override
    protected void add(int index, Resume resume) {
        storage.add(resume);
    }

    @Override
    protected int getIndex(String uuid) {
        int index = 0;
        for (Resume resume : storage) {
            if (resume.getUuid().equals(uuid)) {
                return index;
            }
            index++;
        }
        return -1;
    }

    @Override
    public Resume[] getAll() {
        return storage.toArray(new Resume[0]);
    }

    @Override
    public int size() {
        return storage.size();
    }

    @Override
    protected void removeByIndex(int index, String uuid) {
        storage.remove(index);
    }
}
