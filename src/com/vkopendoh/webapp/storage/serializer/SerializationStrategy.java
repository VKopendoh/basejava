package com.vkopendoh.webapp.storage.serializer;

import com.vkopendoh.webapp.model.Resume;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface SerializationStrategy {
    void doWrite(Resume resume, OutputStream os) throws IOException;

    Resume doRead(InputStream is) throws IOException;
}
