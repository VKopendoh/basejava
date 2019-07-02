package com.vkopendoh.webapp.storage;

import com.vkopendoh.webapp.model.Resume;

import java.util.Collection;
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
        if (storage.containsKey(searchKey)) {
            return true;
        }
        return false;
    }

    @Override
    protected Object getSearchKey(String uuid) {
        return uuid;
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
        Collection<Resume> resumeCollection = storage.values();
        return resumeCollection.toArray(new Resume[0]);
    }

    @Override
    public int size() {
        return storage.size();
    }
}
