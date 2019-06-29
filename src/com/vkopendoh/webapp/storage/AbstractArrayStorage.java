package com.vkopendoh.webapp.storage;

import com.vkopendoh.webapp.exception.StorageException;
import com.vkopendoh.webapp.model.Resume;

import java.util.Arrays;

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
    protected Resume getByIndex(int index, String uuid) {
        return storage[index];
    }

    @Override
    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    protected void add(int index, Resume resume) {
        if (size == storage.length) {
            throw new StorageException("array overflow", resume.getUuid());
        }
        addToArray(index, resume);
        size++;
    }

    @Override
    protected void removeByIndex(int index, String uuid) {
        removeFromArray(index);
        storage[size] = null;
        size--;
    }

    @Override
    protected void setByIndex(int index, Resume resume) {
        storage[index] = resume;
    }

    protected abstract void addToArray(int index, Resume resume);

    protected abstract void removeFromArray(int index);
}
