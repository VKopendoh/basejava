package com.vkopendoh.webapp;

import java.io.File;
import java.util.Objects;

public class MainFiles {
    public static void main(String[] args) {
        File file = new File("..\\basejava");
        getFiles(file);
    }

   private static void getFiles(File file) {
        System.out.println("Folder: " + file.getName());
        if(file.listFiles() != null){
            for (File name : Objects.requireNonNull(file.listFiles())) {
                if (name.isDirectory()) {
                    getFiles(name);
                } else {
                    System.out.println("File: " + name.getName());
                }
            }
        }

    }

}
