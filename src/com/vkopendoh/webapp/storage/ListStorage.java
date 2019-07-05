package com.vkopendoh.webapp.storage;

import com.vkopendoh.webapp.model.Resume;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ListStorage extends AbstractStorage {
    private List<Resume> storage = new ArrayList<>();

    private static final Comparator<Resume> RESUME_COMPARATOR = new Comparator<Resume>() {
        @Override
        public int compare(Resume o1, Resume o2) {
            return o1.getUuid().compareTo(o2.getUuid());
        }
    };

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    protected Resume doGet(Object searchKey) {
        int index = (int) searchKey;
        return storage.get(index);
    }

    @Override
    public List<Resume> getList() {
        return storage;
    }

    @Override
    protected void setByKey(Object searchKey, Resume resume) {
        int index = (int) searchKey;
        storage.set(index, resume);
    }

    @Override
    protected void add(Object searchKey, Resume resume) {
        storage.add(resume);
    }

    @Override
    protected Object getSearchKey(String key) {
        int index = 0;
        for (Resume resume : storage) {
            if (resume.getUuid().equals(key)) {
                return index;
            }
            index++;
        }
        return -1;
    }

    @Override
    protected boolean searchKeyExist(Object searchKey) {
        int index = (int) searchKey;
        return index > -1;
    }

    @Override
    protected String choiceKey(Resume resume) {
        return resume.getUuid();
    }

    @Override
    public int size() {
        return storage.size();
    }

    @Override
    protected void removeByKey(Object searchKey) {
        int index = (int) searchKey;
        storage.remove(index);

    }
}
