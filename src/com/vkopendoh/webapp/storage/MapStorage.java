package com.vkopendoh.webapp.storage;

import com.vkopendoh.webapp.model.Resume;

import java.util.HashMap;
import java.util.Map;

public class MapStorage extends AbstractStorage {
    private Map<String, Resume> storage = new HashMap<>();

    @Override
    protected void add(int index, Resume resume) {
        storage.put(resume.getUuid(), resume);
    }

    @Override
    protected void setByIndex(int index, Resume resume) {
        storage.put(resume.getUuid(), resume);
    }

    @Override
    protected int getIndex(String uuid) {
        if (storage.containsKey(uuid)) {
            return 0;
        }
        return -1;

    }

    @Override
    protected Resume getByIndex(int index, String uuid) {
        return storage.get(uuid);
    }

    @Override
    protected void removeByIndex(int index, String uuid) {
        storage.remove(uuid);
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
