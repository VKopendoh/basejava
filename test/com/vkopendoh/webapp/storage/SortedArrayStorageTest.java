package com.vkopendoh.webapp.storage;

public class SortedArrayStorageTest extends AbstractArrayStorageTest {
    private Storage storage = new SortedArrayStorage();

    @Override
    Storage getStorage() {
        return this.storage;
    }
}