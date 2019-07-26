package com.vkopendoh.webapp.storage.serializer;

import com.vkopendoh.webapp.storage.serializer.ConsumerWithException;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.Objects;

public interface WriterIOException<T> {
   default void writeWithException(Collection<T> collection, DataOutputStream dos, ConsumerWithException<T> action) throws IOException
    {
        Objects.requireNonNull(action);
        for (T t : collection) {
            action.accept(t);
        }
    }
}
