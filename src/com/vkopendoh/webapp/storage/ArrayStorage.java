package com.vkopendoh.webapp.storage;

import com.vkopendoh.webapp.model.Resume;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage {

    @Override
    protected int getIndex(String uuid) {
        for (int index = 0; index < size; index++) {
            if (uuid.equals(storage[index].getUuid())) {
                return index;
            }
        }
        return -1;
    }

    @Override
    protected Resume getByIndex(int index) {
        return storage[index];
    }

    @Override
    protected void insert(int index, Resume resume) {

        if (index < 0) {
            storage[size] = resume;
        } else {
            storage[index] = resume;
        }
    }

    @Override
    protected void remove(int index) {
        storage[index] = storage[size - 1];
    }


}
