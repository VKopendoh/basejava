package com.vkopendoh.webapp;

import com.vkopendoh.webapp.model.Resume;

public class TestData {
    public static final String UUID_1 = "59875c7d-d733-43f1-9665-d6558759742e";
    public static final String UUID_2 = "f8bc677b-b619-4d7e-bbde-69d991c2a98b";
    public static final String UUID_3 = "2b442e15-4a75-4b75-be31-7ad3462898f5";
    public static final String DUMMY = "3b69b8c1-c9a8-4a93-9a88-94c7cd554691";
    public static final String FULL_NAME_1 = "name1";
    public static final Resume RESUME_1 = ResumeTestData.createResume(UUID_1, FULL_NAME_1);
    public static final Resume RESUME_2 = ResumeTestData.createResume(UUID_2, "name2");
    public static final Resume RESUME_3 = ResumeTestData.createResume(UUID_3, "name3");
    public static final Resume RESUME_DUMMY = ResumeTestData.createResume(DUMMY, "name_dummy");
}
