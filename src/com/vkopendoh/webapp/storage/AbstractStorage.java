package com.vkopendoh.webapp.storage;

import com.vkopendoh.webapp.exception.ExistStorageException;
import com.vkopendoh.webapp.exception.NotExistStorageException;
import com.vkopendoh.webapp.model.Resume;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public abstract class AbstractStorage implements Storage {
    protected Storage storage;

    private final static Comparator<Resume> RESUME_COMPARATOR_BY_NAME = (o1, o2) -> o1.getFullName().compareTo(o2.getFullName());

    private static final Comparator<Resume> RESUME_COMPARATOR_BY_UUID = (o1, o2) -> o1.getUuid().compareTo(o2.getUuid());

    @Override
    public void save(Resume resume) {
        Object searchKey = getSearchKey(choiceKey(resume));
        if (searchKeyExist(searchKey)) {
            throw new ExistStorageException(resume.getUuid());
        }
        add(searchKey, resume);
    }

    @Override
    public Resume get(String key) {
        Object searchKey = getSearchKey(key);
        if (!searchKeyExist(searchKey)) {
            throw new NotExistStorageException(key);
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
    public void delete(String key) {
        Object searchKey = getSearchKey(key);
        if (!searchKeyExist(searchKey)) {
            throw new NotExistStorageException(key);
        }
        removeByKey(searchKey);
    }

    @Override
    public void update(Resume resume) {
        Object searchKey = getSearchKey(choiceKey(resume));
        if (!searchKeyExist(searchKey)) {
            throw new NotExistStorageException(choiceKey(resume));
        }
        setByKey(searchKey, resume);
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
