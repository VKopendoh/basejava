package com.vkopendoh.webapp.storage;

import com.vkopendoh.webapp.storage.serializer.ObjectStreamSerializer;

public class ObjectStreamPathStorageTest extends AbstractStorageTest {
    public ObjectStreamPathStorageTest() {
        super(new PathStorage(STORAGE_DIR, new ObjectStreamSerializer()));
    }
}