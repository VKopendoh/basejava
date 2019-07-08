package com.vkopendoh.webapp.storage;

import com.vkopendoh.webapp.exception.StorageException;
import com.vkopendoh.webapp.model.Resume;
import org.junit.Assert;
import org.junit.Test;

public abstract class AbstractArrayStorageTest extends AbstractStorageTest {
    private final int STORAGE_SIZE = 10_000;
    private Storage storage;

    protected AbstractArrayStorageTest(Storage storage) {
        super(storage);
        //this.storage = storage;
    }

    @Test(expected = StorageException.class)
    public void saveOverflow() throws StorageException {
        storage.clear();
        try {
            while (storage.size() < STORAGE_SIZE) {
                storage.save(new Resume("test"));
            }
        } catch (StorageException e) {
            Assert.fail(e.getMessage());
        }
        storage.save(new Resume("test"));
    }
}
