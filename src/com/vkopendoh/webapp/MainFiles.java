package com.vkopendoh.webapp;

import java.io.File;
import java.util.Objects;

public class MainFiles {
    private static StringBuilder space = new StringBuilder(" ");

    public static void main(String[] args) {
        File file = new File("..\\basejava");
        getFiles(file);
    }

    private static void getFiles(File file) {
        System.out.println(space + file.getName().toUpperCase());
        if (file.listFiles() != null) {
            for (File name : Objects.requireNonNull(file.listFiles())) {
                space.append(" ");
                if (name.isDirectory()) {
                    getFiles(name);
                } else {
                    System.out.println(space + name.getName());
                }
                space.deleteCharAt(space.length() - 1);

            }
        }

    }

}
