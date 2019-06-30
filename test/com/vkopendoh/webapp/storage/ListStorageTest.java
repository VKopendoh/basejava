package com.vkopendoh.webapp.storage;

import com.vkopendoh.webapp.exception.StorageException;

public class ListStorageTest extends AbstractArrayStorageTest {
    public ListStorageTest() {
        super(new ListStorage());
    }

    @Override
    public void saveOverlow() throws StorageException {
        throw new StorageException("Not an error", "ListStorage");
    }
}