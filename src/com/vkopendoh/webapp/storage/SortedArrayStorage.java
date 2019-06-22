package com.vkopendoh.webapp.storage;

import com.vkopendoh.webapp.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    protected int getIndex(String uuid) {
        Resume searchKey = new Resume(uuid);
        return Arrays.binarySearch(storage, 0, size, searchKey);
    }

    @Override
    protected void addToArray(int index, Resume resume) {
        index = -index - 1;
        System.arraycopy(storage, index, storage, index + 1, size - index);
        storage[index] = resume;
    }

    @Override
    protected void removeFromArray(int index) {
        System.arraycopy(storage, index + 1, storage, index, size - index - 1);
    }
}
