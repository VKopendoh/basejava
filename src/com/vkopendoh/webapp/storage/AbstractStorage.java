package com.vkopendoh.webapp.storage;

import com.vkopendoh.webapp.exception.ExistStorageException;
import com.vkopendoh.webapp.exception.NotExistStorageException;
import com.vkopendoh.webapp.model.Resume;

public abstract class AbstractStorage implements Storage {
    protected Storage storage;

    @Override
    public void save(Resume resume) {
        Object searchKey = getSearchKey(resume.getUuid());
        if (searchKeyExist(searchKey)) {
            throw new ExistStorageException(resume.getUuid());
        }
        add(searchKey, resume);
    }

    @Override
    public Resume get(String uuid) {
        Object searchKey = getSearchKey(uuid);
        if (!searchKeyExist(searchKey)) {
            throw new NotExistStorageException(uuid);
        }
        return doGet(searchKey);
    }

    @Override
    public void delete(String uuid) {
        Object searchKey = getSearchKey(uuid);
        if (!searchKeyExist(searchKey)) {
            throw new NotExistStorageException(uuid);
        }
        removeByKey(searchKey);
    }

    @Override
    public void update(Resume resume) {
        Object searchKey = getSearchKey(resume.getUuid());
        if (!searchKeyExist(searchKey)) {
            throw new NotExistStorageException(resume.getUuid());
        }
        setByKey(searchKey, resume);
    }

    protected abstract void add(Object searchKey, Resume resume);

    protected abstract void setByKey(Object searchKey, Resume resume);

    protected abstract Resume doGet(Object searchKey);

    protected abstract boolean searchKeyExist(Object searchKey);

    protected abstract Object getSearchKey(String uuid);

    protected abstract void removeByKey(Object searchKey);
}
