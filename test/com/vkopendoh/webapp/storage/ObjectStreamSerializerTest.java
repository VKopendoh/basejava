package com.vkopendoh.webapp.storage;

import com.vkopendoh.webapp.storage.serializer.ObjectStreamSerializer;

public class ObjectStreamSerializerTest extends AbstractStorageTest {
    public ObjectStreamSerializerTest() {
        super(new FileStorage(STORAGE_DIR, new ObjectStreamSerializer()));
    }
}