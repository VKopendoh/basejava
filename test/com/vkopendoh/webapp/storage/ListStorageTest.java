package com.vkopendoh.webapp.storage;

import com.vkopendoh.webapp.exception.StorageException;

public class ListStorageTest extends AbstractStorageTest {
    public ListStorageTest() {
        super(new ListStorage());
    }

    @Override
    public void saveOverflow() throws StorageException {
        throw new StorageException("Not an error", "ListStorage");
    }
}