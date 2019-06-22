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
    protected Resume getByIndex(int index) {
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
    protected void incSize(Resume resume) {
        if (size == storage.length) {
            throw new StorageException("array overflow", resume.getUuid());
        }
        size++;
    }

    @Override
    protected void decSize() {
        storage[size - 1] = null;
        size--;
    }

    @Override
    protected void set(int index, Resume resume) {
        storage[index] = resume;
    }
}
