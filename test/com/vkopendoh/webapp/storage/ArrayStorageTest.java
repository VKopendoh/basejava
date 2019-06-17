package com.vkopendoh.webapp.storage;

public class ArrayStorageTest extends AbstractArrayStorageTest {
    private Storage storage = new ArrayStorage();

    @Override
    protected Storage getStorage() {
        return this.storage;
    }
}