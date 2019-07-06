package com.vkopendoh.webapp.storage;

import com.vkopendoh.webapp.exception.ExistStorageException;
import com.vkopendoh.webapp.exception.NotExistStorageException;
import com.vkopendoh.webapp.model.Resume;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public abstract class AbstractStorage implements Storage {
    protected Storage storage;

    private final static Comparator<Resume> RESUME_COMPARATOR = (o1, o2) -> {
        int compare;
        compare = o1.getFullName().compareTo(o2.getFullName());
        if (compare == 0) {
            compare = o1.getUuid().compareTo(o2.getUuid());
        }
        return compare;
    };

    @Override
    public void save(Resume resume) {
        add(getSearchKeyNotExisted(resume), resume);
    }

    @Override
    public Resume get(String key) {
        return doGet(getSearchKeyExisted(key));
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    @Override
    public List<Resume> getAllSorted() {
        List<Resume> resumeList = getList();
        resumeList.sort(RESUME_COMPARATOR);
        return resumeList;
    }

    @Override
    public void delete(String key) {
        removeByKey(getSearchKeyExisted(key));
    }

    @Override
    public void update(Resume resume) {
        setByKey(getSearchKeyExisted(choiceKey(resume)), resume);
    }

    private Object getSearchKeyNotExisted(Resume resume) {
        Object searchKey = getSearchKey(choiceKey(resume));
        if (searchKeyExist(searchKey)) {
            throw new ExistStorageException(choiceKey(resume));
        }
        return searchKey;
    }

    private Object getSearchKeyExisted(String key) {
        Object searchKey = getSearchKey(key);
        if (!searchKeyExist(searchKey)) {
            throw new NotExistStorageException(key);
        }
        return searchKey;
    }

    protected abstract void add(Object searchKey, Resume resume);

    protected abstract void setByKey(Object searchKey, Resume resume);

    protected abstract Resume doGet(Object searchKey);

    protected abstract List<Resume> getList();

    protected abstract boolean searchKeyExist(Object searchKey);

    protected abstract String choiceKey(Resume resume);

    protected abstract Object getSearchKey(String key);

    protected abstract void removeByKey(Object searchKey);
}
