package com.vkopendoh.webapp.storage;

import com.vkopendoh.webapp.sql.SqlHelper;

public class SqlStorageTest extends AbstractStorageTest {
    public SqlStorageTest() {
        super(new SqlStorage(new SqlHelper()));

    }
}