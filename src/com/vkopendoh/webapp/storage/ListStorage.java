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
    protected Resume getByIndex(int index) {
        return storage.get(index);
    }

    @Override
    protected void insert(int index, Resume resume) {
        storage.add(resume);
    }

    @Override
    protected int getIndex(String uuid) {
        int index = 0;
        for(Resume resume:storage){
            if(resume.getUuid() == uuid){
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
        return 0;
    }

    @Override
    protected void remove(int index) {
        storage.remove(index);
    }

    @Override
    protected void incSize(Resume resume) {

    }

    @Override
    protected void decSize() {

    }
}
