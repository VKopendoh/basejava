package com.vkopendoh.webapp.storage;

import com.vkopendoh.webapp.exception.StorageException;
import com.vkopendoh.webapp.model.Resume;

import java.util.Arrays;
import java.util.List;

public abstract class AbstractArrayStorage extends AbstractStorage {
    protected final int STORAGE_SIZE = 10_000;
    protected Resume[] storage = new Resume[STORAGE_SIZE];
    protected int size = 0;

    @Override
    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    @Override
    protected Resume doGet(Object searchKey) {
        int index = (int) searchKey;
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
    protected void add(Object searchKey, Resume resume) {
        if (size == storage.length) {
            throw new StorageException("array overflow", resume.getUuid());
        }
        int index = (int) searchKey;
        addToArray(index, resume);
        size++;
    }

    @Override
    protected void removeByKey(Object searchKey) {
        int index = (int) searchKey;
        removeFromArray(index);
        storage[size] = null;
        size--;
    }

    @Override
    protected boolean isExist(Object searchKey) {
        int index = (int) searchKey;
        return index > -1;
    }

    @Override
    protected void doUpdate(Object searchKey, Resume resume) {
        int index = (int) searchKey;
        storage[index] = resume;
    }

    protected abstract void addToArray(int index, Resume resume);

    protected abstract void removeFromArray(int index);
}
