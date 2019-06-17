package com.vkopendoh.webapp.storage;

import com.vkopendoh.webapp.exception.ExistStorageException;
import com.vkopendoh.webapp.exception.NotExistStorageException;
import com.vkopendoh.webapp.exception.StorageException;
import com.vkopendoh.webapp.model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage implements Storage {
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
        if (size == storage.length) {
            throw new StorageException("array overflow", resume.getUuid());
        }
        int index = getIndex(resume.getUuid());
        if (index > -1) {
            throw new ExistStorageException(resume.getUuid());
        }
        insert(index, resume);
        size++;
    }

    @Override
    public Resume get(String uuid) {
        int index = getIndex(uuid);
        if (index > -1) {
            return storage[index];
        }
        throw new NotExistStorageException(uuid);
    }

    @Override
    public void delete(String uuid) {
        int index = getIndex(uuid);
        if (index < 0) {
            throw new NotExistStorageException(uuid);
        }
        remove(index);
        storage[size - 1] = null;
        size--;
    }

    @Override
    public void update(Resume resume) {
        int index = getIndex(resume.getUuid());
        if (index > -1) {
            storage[index] = resume;
        } else {
            throw new NotExistStorageException(resume.getUuid());
        }
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

    protected abstract int getIndex(String uuid);

    protected abstract void insert(int index, Resume resume);

    protected abstract void remove(int index);

}
