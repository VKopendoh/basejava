package com.vkopendoh.webapp.storage;

public class ObjectStreamPathStorageTest extends AbstractStorageTest {
    public ObjectStreamPathStorageTest() {
        super(new PathStorage(STORAGE_DIR, new ObjectStreamStorage()));
    }
}