package com.vkopendoh.webapp.storage;

import com.vkopendoh.webapp.exception.ExistStorageException;
import com.vkopendoh.webapp.exception.NotExistStorageException;
import com.vkopendoh.webapp.exception.StorageException;
import com.vkopendoh.webapp.model.Resume;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public abstract class AbstractArrayStorageTest {
    private static String UUID_1 = "uuid1";
    private static String UUID_2 = "uuid2";
    private static String UUID_3 = "uuid3";
    private final int STORAGE_SIZE = 10_000;
    private Storage storage;

    @Before
    public void setUp() {
        storage = getStorage();
        storage.clear();
        storage.save(new Resume(UUID_1));
        storage.save(new Resume(UUID_2));
        storage.save(new Resume(UUID_3));
    }

    abstract Storage getStorage();

    @Test
    public void clear() {
        storage.clear();
        Assert.assertEquals(0, storage.size());
    }

    @Test
    public void save() {
        Assert.assertEquals(storage.get(UUID_1).toString(), UUID_1);
        Assert.assertEquals(storage.get(UUID_2).toString(), UUID_2);
        Assert.assertEquals(storage.get(UUID_3).toString(), UUID_3);
    }

    @Test
    public void get() {
        Assert.assertEquals(storage.get(UUID_1).toString(), UUID_1);
        Assert.assertEquals(storage.get(UUID_2).toString(), UUID_2);
        Assert.assertEquals(storage.get(UUID_3).toString(), UUID_3);
    }

    @Test
    public void delete() {
        storage.delete(UUID_1);
        Assert.assertEquals(2, storage.size());
        storage.delete(UUID_2);
        Assert.assertEquals(1, storage.size());
        storage.delete(UUID_3);
        Assert.assertEquals(0, storage.size());
    }

    @Test
    public void update() {
        Resume r = storage.get(UUID_1);
        storage.update(r);
        Assert.assertSame(r, storage.get(UUID_1));
    }

    @Test
    public void getAll() {
        Assert.assertArrayEquals(storage.getAll(), new Resume[]{new Resume(UUID_1), new Resume(UUID_2), new Resume(UUID_3)});
    }

    @Test
    public void size() {
        Assert.assertEquals(3, storage.size());
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() {
        storage.get("dummy");
    }

    @Test(expected = ExistStorageException.class)
    public void resumeAlreadyExist() {
        storage.save(new Resume(UUID_1));
        storage.save(new Resume(UUID_2));
        storage.save(new Resume(UUID_3));
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteNotExist() {
        storage.delete("dummy");
    }

    @Test
    public void storageOverlow() throws StorageException {
        try {
            while (storage.size() < STORAGE_SIZE + 1) {
                storage.save(new Resume());
            }
            Assert.fail("Excepted StorageException");
        } catch (StorageException e) {
            Assert.assertEquals("array overflow", e.getMessage());
        }

    }
}