package com.vkopendoh.webapp.storage;

import com.vkopendoh.webapp.Config;

public class SqlStorageTest extends AbstractStorageTest {
    public SqlStorageTest() {
        super(Config.get().getStorage());
    }
}