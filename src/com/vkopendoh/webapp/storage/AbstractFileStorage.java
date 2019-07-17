package com.vkopendoh.webapp.storage;

import com.vkopendoh.webapp.exception.StorageException;
import com.vkopendoh.webapp.model.Resume;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class AbstractFileStorage extends AbstractStorage<File> {
    private File directory;

    protected AbstractFileStorage(String dir) {
        File directory = new File(dir);
        Objects.requireNonNull(directory, "directory must not be null");
        if (!directory.isDirectory()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not directory");
        }
        if (!(directory.canRead() || directory.canWrite())) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not readable/writable");
        }
        this.directory = directory;
    }

    @Override
    protected void add(File file, Resume resume) {
        try {
            file.createNewFile();
            // doWrite(resume,new BufferedOutputStream(new FileOutputStream(file)));
        } catch (IOException e) {
            throw new StorageException("Can't create file", file.getName(), e);
        }
        doUpdate(file, resume);
    }

    @Override
    protected void doUpdate(File file, Resume resume) {
        try {
            doWrite(resume, new BufferedOutputStream(new FileOutputStream(file)));
        } catch (IOException e) {
            throw new StorageException("IO error", file.getName(), e);
        }
    }

    @Override
    protected Resume doGet(File file) {
        try {
            return doRead(new BufferedInputStream(new FileInputStream(file)));
        } catch (IOException e) {
            throw new StorageException("IO error", file.getName(), e);
        }
    }

    @Override
    protected List<Resume> getList() {
        List<Resume> resumes = new ArrayList<>();
        for (File file : Objects.requireNonNull(directory.listFiles())) {
            try {
                resumes.add(doRead(new BufferedInputStream(new FileInputStream(file))));
            } catch (IOException e) {
                throw new StorageException("IO error", file.getName(), e);
            }
        }
        return resumes;
    }

    @Override
    protected boolean isExist(File file) {
        return file.exists();
    }

    @Override
    protected File getSearchKey(String uuid) {
        return new File(directory, uuid);
    }

    @Override
    protected void removeByKey(File file) {
        try {
            file.delete();
        } catch (Exception e) {
            throw new StorageException("Can't delete", file.getName(), e);
        }
    }

    @Override
    public void clear() {
        for (File name : Objects.requireNonNull(directory.listFiles())) {
            removeByKey(name);
        }
    }

    @Override
    public int size() {
        return Objects.requireNonNull(directory.list()).length;
    }

    protected abstract void doWrite(Resume resume, OutputStream file) throws IOException;

    protected abstract Resume doRead(InputStream file) throws IOException;
}
