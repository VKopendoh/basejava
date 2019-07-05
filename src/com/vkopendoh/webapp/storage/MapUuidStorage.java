package com.vkopendoh.webapp.storage;

import com.vkopendoh.webapp.model.Resume;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapUuidStorage extends AbstractStorage {
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
        return storage.containsKey(searchKey);
    }

    @Override
    protected String choiceKey(Resume resume) {
        return resume.getUuid();
    }

    @Override
    protected Object getSearchKey(String key) {
        return key;
    }

    @Override
    protected Resume doGet(Object searchKey) {
        return storage.get(searchKey);
    }

    @Override
    public List<Resume> getList() {
        return new ArrayList<>(storage.values());
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
    public int size() {
        return storage.size();
    }
}
