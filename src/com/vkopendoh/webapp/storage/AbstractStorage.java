package com.vkopendoh.webapp.storage;

import com.vkopendoh.webapp.exception.ExistStorageException;
import com.vkopendoh.webapp.exception.NotExistStorageException;
import com.vkopendoh.webapp.model.Resume;

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
        add(getNotExistSearchKey(resume), resume);
    }

    @Override
    public Resume get(String uuid) {
        return doGet(getExistSearchKey(uuid));
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
    public void delete(String uuid) {
        removeByKey(getExistSearchKey(uuid));
    }

    @Override
    public void update(Resume resume) {
        doUpdate(getExistSearchKey(resume.getUuid()), resume);
    }

    private Object getNotExistSearchKey(Resume resume) {
        Object searchKey = getSearchKey(resume.getUuid());
        if (isExist(searchKey)) {
            throw new ExistStorageException(resume.getUuid());
        }
        return searchKey;
    }

    private Object getExistSearchKey(String uuid) {
        Object searchKey = getSearchKey(uuid);
        if (!isExist(searchKey)) {
            throw new NotExistStorageException(uuid);
        }
        return searchKey;
    }

    protected abstract void add(Object searchKey, Resume resume);

    protected abstract void doUpdate(Object searchKey, Resume resume);

    protected abstract Resume doGet(Object searchKey);

    protected abstract List<Resume> getList();

    protected abstract boolean isExist(Object searchKey);

    protected abstract Object getSearchKey(String uuid);

    protected abstract void removeByKey(Object searchKey);
}
