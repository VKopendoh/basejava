package com.vkopendoh.webapp.storage;

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
            System.out.println("Error: Can't save, resume with uuid: " + resume.getUuid() + "  array overflow.");
            return;
        }
        int index = getIndex(resume.getUuid());
        if (index > -1) {
            System.out.println("Error: Can't save, resume with uuid: " + resume.getUuid() + " already exist.");
            return;
        }
        insert(index, resume);
    }

    @Override
    public Resume get(String uuid) {
        int index = getIndex(uuid);
        if (index > -1) {
            return storage[index];
        }
        System.out.println("Error: Can't get, resume with uuid: " + uuid + " not exist.");
        return null;
    }

    @Override
    public void delete(String uuid) {
        int index = getIndex(uuid);
        if (index < 0) {
            System.out.println("Error: Can't delete, resume with uuid: " + uuid + " not exist.");
            return;
        }
        deleteResume(index);
    }

    @Override
    public void update(Resume resume) {
        int index = getIndex(resume.getUuid());
        if (index > -1) {
            storage[index] = resume;
        } else {
            System.out.println("Error: Can't update, resume with uuid: " + resume.getUuid() + " not exist.");
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

    protected abstract void deleteResume(int index);
}
