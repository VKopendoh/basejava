package com.vkopendoh.webapp.storage;

import com.vkopendoh.webapp.exception.StorageException;

public class MapUuidStorageTest extends AbstractStorageTest {
    public MapUuidStorageTest() {
        super(new MapUuidStorage());
    }

    @Override
    public void saveOverflow() throws StorageException {
        throw new StorageException("Not an error","MapUuidStorage");
    }
}