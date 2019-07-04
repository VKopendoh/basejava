package com.vkopendoh.webapp.storage;

import com.vkopendoh.webapp.exception.ExistStorageException;
import com.vkopendoh.webapp.exception.NotExistStorageException;
import com.vkopendoh.webapp.model.Resume;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public abstract class AbstractStorage implements Storage {
    protected Storage storage;

    private final static Comparator<Resume> RESUME_COMPARATOR_BY_NAME = new Comparator<Resume>() {
        @Override
        public int compare(Resume o1, Resume o2) {
            return o1.getFullName().compareTo(o2.getFullName());
        }
    };

    private static final Comparator<Resume> RESUME_COMPARATOR_BY_UUID = new Comparator<Resume>() {
        @Override
        public int compare(Resume o1, Resume o2) {
            return o1.getUuid().compareTo(o2.getUuid());
        }
    };

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

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    @Override
    public List<Resume> getAllSorted() {
        List<Resume> resumeList = getList();
        Collections.sort(resumeList, RESUME_COMPARATOR_BY_NAME.thenComparing(RESUME_COMPARATOR_BY_UUID));
        return resumeList;
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

    protected abstract List<Resume> getList();

    protected abstract boolean searchKeyExist(Object searchKey);

    protected abstract Object getSearchKey(String uuid);

    protected abstract void removeByKey(Object searchKey);
}
