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
    protected Resume doGet(Object searchKey) {
        int index = (int) searchKey;
        return storage.get(index);
    }

    @Override
    protected void setByKey(Object searchKey, Resume resume) {
        int index = (int) searchKey;
        storage.set(index, resume);
    }

    @Override
    protected void add(Object searchKey, Resume resume) {
        storage.add(resume);
    }

    @Override
    protected Object getSearchKey(String uuid) {
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
    protected boolean searchKeyExist(Object searchKey) {
        int index = (int) searchKey;
        if (index > -1) {
            return true;
        }
        return false;
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
    protected void removeByKey(Object searchKey) {
        int index = (int) searchKey;
        storage.remove(index);

    }
}
