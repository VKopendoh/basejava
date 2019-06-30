package com.vkopendoh.webapp.storage;

import com.vkopendoh.webapp.exception.StorageException;

public class MapStorageTest extends AbstractArrayStorageTest {
    public MapStorageTest() {
        super(new MapStorage());
    }

    @Override
    public void saveOverlow() throws StorageException {
        throw new StorageException("Not an error","MapStorage");
    }
}