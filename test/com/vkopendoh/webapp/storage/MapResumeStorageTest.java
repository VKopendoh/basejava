package com.vkopendoh.webapp.storage;

import com.vkopendoh.webapp.exception.StorageException;

public class MapResumeStorageTest extends AbstractStorageTest {
    public MapResumeStorageTest() {
        super(new MapResumeStorage());
    }

    @Override
    public void saveOverflow() throws StorageException {
        throw new StorageException("Not an error", "MapUuidStorage");
    }


}