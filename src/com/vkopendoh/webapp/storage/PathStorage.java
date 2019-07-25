package com.vkopendoh.webapp.storage;

import com.vkopendoh.webapp.exception.StorageException;
import com.vkopendoh.webapp.model.Resume;
import com.vkopendoh.webapp.storage.serializer.SerializationStrategy;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PathStorage extends AbstractStorage<Path> {
    private Path directory;
    private SerializationStrategy strategy;

    protected PathStorage(String dir, SerializationStrategy strategy) {
        directory = Paths.get(dir);
        Objects.requireNonNull(directory, "directory must not be null");
        if (!Files.isDirectory(directory) || !Files.isWritable(directory)) {
            throw new IllegalArgumentException(dir + " is not directory or is not writable");
        }
        this.strategy = strategy;
    }

    @Override
    protected void add(Path path, Resume resume) {
        try {
            Files.createFile(path);
        } catch (IOException e) {
            throw new StorageException("Can't create file", String.valueOf(path.getFileName()), e);
        }
        doUpdate(path, resume);
    }

    @Override
    protected void doUpdate(Path path, Resume resume) {
        try {
            strategy.doWrite(resume, Files.newOutputStream(path));
        } catch (IOException e) {
            throw new StorageException("Can't update file", path.getFileName().toString(), e);
        }
    }

    @Override
    protected Resume doGet(Path path) {
        try {
            return strategy.doRead(Files.newInputStream(path));
        } catch (IOException e) {
            throw new StorageException("Can't get file", path.getFileName().toString(), e);
        }
    }

    @Override
    protected List<Resume> getList() {
        List<Resume> resumes = new ArrayList<>();
        try {
            Files.list(directory).forEach(path -> {
                try {
                    resumes.add(strategy.doRead(Files.newInputStream(path)));
                } catch (IOException e) {
                    throw new StorageException("IO error", path.getFileName().toString(), e);
                }
            });
        } catch (IOException e) {
            throw new StorageException("IO error", directory.getFileName().toString(), e);
        }
        return resumes;
    }

    @Override
    protected boolean isExist(Path path) {
        return Files.exists(path);
    }

    @Override
    protected Path getSearchKey(String uuid) {
        return directory.resolve(uuid);
    }

    @Override
    protected void removeByKey(Path path) {
        try {
            Files.delete(path);
        } catch (Exception e) {
            throw new StorageException("Can't delete", path.getFileName().toString(), e);
        }
    }

    @Override
    public void clear() {
        try {
            Files.list(directory).forEach(this::removeByKey);
        } catch (IOException e) {
            throw new StorageException("Path delete error", null);
        }
    }

    @Override
    public int size() {
        return getList().size();
    }
}
