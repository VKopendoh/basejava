package com.vkopendoh.webapp.storage;

import com.vkopendoh.webapp.exception.StorageException;
import com.vkopendoh.webapp.model.Resume;

import java.util.Arrays;
import java.util.List;

public abstract class AbstractArrayStorage extends AbstractStorage<Integer> {
    protected static final int STORAGE_SIZE = 10_000;
    protected Resume[] storage = new Resume[STORAGE_SIZE];
    protected int size = 0;

    @Override
    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    @Override
    protected Resume doGet(Integer searchKey) {
        int index = searchKey;
        return storage[index];
    }

    @Override
    protected List<Resume> getList() {
        return Arrays.asList(Arrays.copyOf(storage, size));
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    protected void add(Integer searchKey, Resume resume) {
        if (size == storage.length) {
            throw new StorageException("array overflow", resume.getUuid());
        }
        int index = searchKey;
        addToArray(index, resume);
        size++;
    }

    @Override
    protected void removeByKey(Integer searchKey) {
        int index = searchKey;
        removeFromArray(index);
        storage[size] = null;
        size--;
    }

    @Override
    protected boolean isExist(Integer searchKey) {
        return searchKey >= 0;
    }

    @Override
    protected void doUpdate(Integer searchKey, Resume resume) {
        int index = searchKey;
        storage[index] = resume;
    }

    protected abstract void addToArray(int index, Resume resume);

    protected abstract void removeFromArray(int index);
}
