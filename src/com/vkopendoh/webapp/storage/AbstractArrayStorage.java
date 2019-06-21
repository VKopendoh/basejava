package com.vkopendoh.webapp.storage;

import com.vkopendoh.webapp.exception.StorageException;
import com.vkopendoh.webapp.model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage extends AbstractStorage implements Storage {
    protected final int STORAGE_SIZE = 10_000;
    protected Resume[] storage = new Resume[STORAGE_SIZE];
    protected int size = 0;

    @Override
    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    @Override
    public void save(Resume resume) {
        super.save(resume);
        if (size == storage.length) {
            throw new StorageException("array overflow", resume.getUuid());
        }
        size++;
    }

    @Override
    protected Resume getByIndex(int resumeExist) {
        return storage[resumeExist];
    }

    @Override
    public void delete(String uuid) {
        super.delete(uuid);
        storage[size - 1] = null;
        size--;
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
}
