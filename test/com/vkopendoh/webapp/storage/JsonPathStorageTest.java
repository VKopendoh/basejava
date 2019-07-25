package com.vkopendoh.webapp.storage;

import com.vkopendoh.webapp.storage.serializer.JsonStreamSerializer;

public class JsonPathStorageTest extends AbstractStorageTest {
    public JsonPathStorageTest() {
        super(new PathStorage(STORAGE_DIR, new JsonStreamSerializer()));
    }
}