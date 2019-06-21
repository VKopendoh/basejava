package com.vkopendoh.webapp.storage;

import com.vkopendoh.webapp.exception.ExistStorageException;
import com.vkopendoh.webapp.exception.NotExistStorageException;
import com.vkopendoh.webapp.model.Resume;

public abstract class AbstractStorage implements Storage {

    @Override
    public void save(Resume resume) {
        int index = getIndex(resume.getUuid());
        if (index > -1) {
            throw new ExistStorageException(resume.getUuid());
        }
        insert(index, resume);
        incSize(resume);
    }

    @Override
    public Resume get(String uuid) {
        int index = resumeExist(uuid);
        return getByIndex(index);
    }

    @Override
    public void delete(String uuid) {
        int index = resumeExist(uuid);
        remove(index);
        decSize();
    }

    @Override
    public void update(Resume resume) {
        int index = resumeExist(resume.getUuid());
        Resume resumeToUpdate = getByIndex(index);
        resumeToUpdate = resume;
    }

    private int resumeExist(String uuid) {
        int index = getIndex(uuid);
        if (index < 0) {
            throw new NotExistStorageException(uuid);
        }
        return index;
    }

    protected abstract void insert(int index, Resume resume);

    protected abstract int getIndex(String uuid);

    protected abstract Resume getByIndex(int index);

    protected abstract void remove(int index);

    protected abstract void incSize(Resume resume);

    protected abstract void decSize();
}
