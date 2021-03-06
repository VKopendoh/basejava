package com.vkopendoh.webapp.storage;

import com.vkopendoh.webapp.model.Resume;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage {

    @Override
    protected Integer getSearchKey(String key) {
        for (int index = 0; index < size; index++) {
            if (key.equals(storage[index].getUuid())) {
                return index;
            }
        }
        return -1;
    }

    @Override
    protected void addToArray(int index, Resume resume) {
        storage[size] = resume;
    }

    @Override
    protected void removeFromArray(int index) {
        storage[index] = storage[size - 1];
    }
}
