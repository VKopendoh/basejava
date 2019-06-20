package com.vkopendoh.webapp.storage;

import com.vkopendoh.webapp.exception.NotExistStorageException;
import com.vkopendoh.webapp.model.Resume;

public class ListStorage extends AbstractStorage {
    private Entry first;

    public ListStorage() {
        this.first = null;
    }

    private class Entry {
        private Resume resume;
        private Entry next;

        Entry(Resume resume) {
            this.resume = resume;
        }

        public Resume getResume() {
            return resume;
        }

        public ListStorage.Entry getNext() {
            return next;
        }
    }

    @Override
    public void save(Resume resume) {
        Entry newEntry = new Entry(resume);
        newEntry.next = first;
        first = newEntry;
    }

    @Override
    public Resume get(String uuid) {
        Entry current = first;
        while (current != null) {
            Resume curResume = current.getResume();
            if (curResume.getUuid() == uuid) {
                return curResume;
            }
            current = current.getNext();
        }
        new NotExistStorageException(uuid);
        return null;
    }

    @Override
    public void delete(String uuid) {
        Entry current = first;
        Entry previous = first;
        while (current != null) {
            Resume curResume = current.getResume();
            if (curResume.getUuid() == uuid) {
                previous.next = current.getNext();
                break;
            }
            previous = current;
            current = current.getNext();
        }
    }

    @Override
    public void clear() {
        Entry current = first;
        while (current != null) {
            current = current.getNext();
            first = current;
        }
        first = null;
    }

    @Override
    public void update(Resume resume) {
        Entry current = first;
        while (current != null) {
            Resume curResume = current.getResume();
            if (curResume.getUuid() == resume.getUuid()) {
                current.resume = resume;
            }
            current = current.getNext();
        }
        new NotExistStorageException(resume.getUuid());
    }

    @Override
    public Resume[] getAll() {
        Entry current = first;
        int capacity = this.size();
        Resume[] resumes = new Resume[capacity];
        for (int i = 0; i < capacity; i++) {
            resumes[i] = current.getResume();
            current = current.getNext();
        }
        return resumes;
    }

    public void print() {
        for (Entry current = first; current != null; current = current.getNext()) {
            Resume currentResume = current.getResume();
            System.out.println(currentResume.getUuid() + " ");
        }
    }

    @Override
    public int size() {
        int i = 0;
        Entry current = first;
        while (current != null) {
            i++;
            current = current.getNext();
        }
        return i;
    }

    private boolean isExist() {
        return first == null;
    }
}
