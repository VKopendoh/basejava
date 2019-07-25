package com.vkopendoh.webapp.storage;

import com.vkopendoh.webapp.storage.serializer.DataStreamSerializer;

public class DataPathStorageTest extends AbstractStorageTest {
    public DataPathStorageTest() {
        super(new PathStorage(STORAGE_DIR, new DataStreamSerializer()));
    }
}