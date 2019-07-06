package com.vkopendoh.webapp;

import com.vkopendoh.webapp.model.Resume;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MainReflection {
    public static void main(String[] args) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Resume resume = new Resume("Test");
        Method methodToString = resume.getClass().getDeclaredMethod("toString");
        System.out.println("Invoke resume.toString via reflections: " + methodToString.invoke(resume));
    }
}

