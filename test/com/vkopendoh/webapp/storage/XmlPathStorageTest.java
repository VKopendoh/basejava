package com.vkopendoh.webapp.storage;

import com.vkopendoh.webapp.storage.serializer.XmlStreamSerializer;

public class XmlPathStorageTest extends AbstractStorageTest {
    public XmlPathStorageTest() {
        super(new PathStorage(STORAGE_DIR, new XmlStreamSerializer()));
    }
}