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
    public List<Resume> getList() {
        return storage.subList(0, storage.size());
    }

    @Override
    protected void doUpdate(Object searchKey, Resume resume) {
        int index = (int) searchKey;
        storage.set(index, resume);
    }

    @Override
    protected void add(Object searchKey, Resume resume) {
        storage.add(resume);
    }

    @Override
    protected Object getSearchKey(String key) {
        int index = 0;
        for (Resume resume : storage) {
            if (resume.getUuid().equals(key)) {
                return index;
            }
            index++;
        }
        return -1;
    }

    @Override
    protected boolean isExist(Object searchKey) {
        int index = (int) searchKey;
        return index > -1;
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
