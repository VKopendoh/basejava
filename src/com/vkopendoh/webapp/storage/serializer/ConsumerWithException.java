package com.vkopendoh.webapp.storage.serializer;

import java.io.IOException;

@FunctionalInterface
public interface ConsumerWithException<T> {
    void accept(T t) throws IOException;
}
