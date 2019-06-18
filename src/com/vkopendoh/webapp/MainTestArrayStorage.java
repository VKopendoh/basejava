package com.vkopendoh.webapp;

import com.vkopendoh.webapp.model.Resume;
import com.vkopendoh.webapp.storage.ArrayStorage;
import com.vkopendoh.webapp.storage.Storage;

/**
 * Test for your com.vkopendoh.webapp.storage.ArrayStorage implementation
 */
public class MainTestArrayStorage {
    static final Storage ARRAY_STORAGE = new ArrayStorage();
    //static final Storage ARRAY_STORAGE = new SortedArrayStorage();

    public static void main(String[] args) {
        Resume resume1 = new Resume("uuid1");
        Resume resume2 = new Resume("uuid2");
        Resume resume3 = new Resume("uuid3");
        Resume resume4 = new Resume("uuid3");

        ARRAY_STORAGE.save(resume1);
        ARRAY_STORAGE.save(resume2);
        ARRAY_STORAGE.save(resume3);

        System.out.println("Get r1: " + ARRAY_STORAGE.get(resume1.getUuid()));
        System.out.println("Size: " + ARRAY_STORAGE.size());

        System.out.println("Get dummy: " + ARRAY_STORAGE.get("dummy"));

        ARRAY_STORAGE.update(resume2);
        System.out.println("com.vkopendoh.webapp.model.Resume r2: " + ARRAY_STORAGE.get(resume2.getUuid()) + " updated");
        System.out.println("Size: " + ARRAY_STORAGE.size());

        printAll();
        ARRAY_STORAGE.update(resume4);

        printAll();
        ARRAY_STORAGE.delete(resume1.getUuid());
        printAll();
        ARRAY_STORAGE.clear();
        printAll();

        System.out.println("Size: " + ARRAY_STORAGE.size());
    }

    static void printAll() {
        System.out.println("\nGet All");
        for (Resume resume : ARRAY_STORAGE.getAll()) {
            System.out.println(resume);
        }
    }
}
