package com.vkopendoh.webapp.storage.serializer;

import java.io.IOException;

@FunctionalInterface
public interface ConsumerWithException {
    void accept() throws IOException;
}
