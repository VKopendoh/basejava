package com.vkopendoh.webapp.storage;

import com.vkopendoh.webapp.model.Resume;

import java.util.HashMap;
import java.util.Map;

public class MapStorage extends AbstractStorage {
    private Map<String, Resume> storage = new HashMap<>();

    @Override
    protected void add(Object searchKey, Resume resume) {
        storage.put(resume.getUuid(), resume);
    }

    @Override
    protected void setByKey(Object searchKey, Resume resume) {
        storage.put(String.valueOf(searchKey), resume);
    }

    @Override
    protected boolean searchKeyExist(Object searchKey) {
        if (searchKey == null) {
            return false;
        }
        return true;
    }

    @Override
    protected Object getSearchKey(String uuid) {
        if (storage.containsKey(uuid)) {
            return uuid;
        }
        return null;
    }

    @Override
    protected Resume doGet(Object searchKey) {
        return storage.get(searchKey);
    }

    @Override
    protected void removeByKey(Object searchKey) {
        storage.remove(searchKey);
    }

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public Resume[] getAll() {
        Resume[] resumes = new Resume[storage.size()];
        int count = 0;
        for (Map.Entry<String, Resume> resumeEntry : storage.entrySet()) {
            resumes[count] = resumeEntry.getValue();
            count++;
        }
        return resumes;
    }

    @Override
    public int size() {
        return storage.size();
    }
}
