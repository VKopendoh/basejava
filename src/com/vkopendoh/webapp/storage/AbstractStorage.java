package com.vkopendoh.webapp.storage;

import com.vkopendoh.webapp.exception.ExistStorageException;
import com.vkopendoh.webapp.exception.NotExistStorageException;
import com.vkopendoh.webapp.model.Resume;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;

public abstract class AbstractStorage<SK> implements Storage {
    protected Storage storage;

    private final static Comparator<Resume> RESUME_COMPARATOR = Comparator.comparing(Resume::getFullName).thenComparing(Resume::getUuid);

    @Override
    public void save(Resume resume) {
        add(getNotExistSearchKey(resume), resume);
    }

    @Override
    public Resume get(String uuid) {
        return doGet(getExistSearchKey(uuid));
    }

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
    public void update(Resume resume)  {
        try {
            doUpdate(getExistSearchKey(resume.getUuid()), resume);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private SK getNotExistSearchKey(Resume resume) {
        SK searchKey = getSearchKey(resume.getUuid());
        if (isExist(searchKey)) {
            throw new ExistStorageException(resume.getUuid());
        }
        return searchKey;
    }

    private SK getExistSearchKey(String uuid) {
        SK searchKey = getSearchKey(uuid);
        if (!isExist(searchKey)) {
            throw new NotExistStorageException(uuid);
        }
        return searchKey;
    }

    protected abstract void add(SK searchKey, Resume resume);

    protected abstract void doUpdate(SK searchKey, Resume resume) throws IOException;

    protected abstract Resume doGet(SK searchKey);

    protected abstract List<Resume> getList();

    protected abstract boolean isExist(SK searchKey);

    protected abstract SK getSearchKey(String uuid);

    protected abstract void removeByKey(SK searchKey);
    //Correction
}
