package com.vkopendoh.webapp;

import java.io.File;

public class MainFiles {
    public static void main(String[] args) {
        File file = new File("..\\basejava");
        getFiles(file);
    }

    static void getFiles(File file) {
        System.out.println("Folder: " + file.getName());
        for (File name : file.listFiles()) {
            if (name.isDirectory()) {
                getFiles(name);
            } else {
                System.out.println("File: " + name.getName());
            }
        }
    }

}
