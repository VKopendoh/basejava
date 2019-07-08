package com.vkopendoh.webapp.exception;

public class NotExistStorageException extends StorageException {
    public NotExistStorageException(Object uuid) {
        super("Resume " + uuid + " not exist", uuid.toString());
    }
}
