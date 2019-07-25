package com.vkopendoh.webapp.storage.serializer;

import com.vkopendoh.webapp.model.Resume;
import com.vkopendoh.webapp.util.JsonParser;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class JsonStreamSerializer implements SerializationStrategy {
    @Override
    public void doWrite(Resume resume, OutputStream os) throws IOException {
        try(Writer writer = new OutputStreamWriter(os,StandardCharsets.UTF_8)){
            JsonParser.write(resume,writer);
        }
    }

    @Override
    public Resume doRead(InputStream is) throws IOException {
        try(Reader reader = new InputStreamReader(is, StandardCharsets.UTF_8)){
            return JsonParser.read(reader,Resume.class);
        }
    }
}
