package com.vkopendoh.webapp.storage;

import com.vkopendoh.webapp.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage<Integer> {
    private List<Resume> storage = new ArrayList<>();

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    protected Resume doGet(Integer searchKey) {
        int index = searchKey;
        return storage.get(index);
    }

    @Override
    public List<Resume> getList() {
        return storage.subList(0, storage.size());
    }

    @Override
    protected void doUpdate(Integer searchKey, Resume resume) {
        int index = searchKey;
        storage.set(index, resume);
    }

    @Override
    protected void add(Integer searchKey, Resume resume) {
        storage.add(resume);
    }

    @Override
    protected Integer getSearchKey(String uuid) {
        int index = 0;
        for (Resume resume : storage) {
            if (resume.getUuid().equals(uuid)) {
                return index;
            }
            index++;
        }
        return null;
    }

    @Override
    protected boolean isExist(Integer searchKey) {
        return searchKey != null;
    }

    @Override
    public int size() {
        return storage.size();
    }

    @Override
    protected void removeByKey(Integer searchKey) {
        int index = searchKey;
        storage.remove(index);

    }
}
