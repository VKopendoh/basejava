package com.vkopendoh.webapp.model;

import com.sun.org.apache.xerces.internal.xs.datatypes.ObjectList;

import java.util.Map;
import java.util.Objects;
import java.util.UUID;

/**
 * Initial resume class
 */
public class Resume implements Comparable<Resume> {
    private String uuid;

    private String fullName;

    public void setContacts(Map<String, String> contacts) {
        this.contacts = contacts;
    }

    public void setPersonal(TextSection personal) {
        this.personal = personal;
    }

    public void setObjective(TextSection objective) {
        this.objective = objective;
    }

    public void setAchievement(TextListSection achievement) {
        Achievement = achievement;
    }

    public void setQualifications(TextListSection qualifications) {
        this.qualifications = qualifications;
    }

    public void setExperience(ObjectListSection experience) {
        Experience = experience;
    }

    public void setEducation(ObjectListSection education) {
        Education = education;
    }

    public Map<String, String> getContacts() {
        return contacts;
    }

    public TextSection getPersonal() {
        return personal;
    }

    public TextSection getObjective() {
        return objective;
    }

    public TextListSection getAchievement() {
        return Achievement;
    }

    public TextListSection getQualifications() {
        return qualifications;
    }

    public ObjectListSection getExperience() {
        return Experience;
    }

    public ObjectListSection getEducation() {
        return Education;
    }

    private Map<String,String> contacts;

    private TextSection personal;

    private TextSection objective;

    private TextListSection Achievement;

    private TextListSection qualifications;

    private ObjectListSection Experience;

    private ObjectListSection Education;

    public Resume(String fullName) {
        this(UUID.randomUUID().toString(), fullName);
    }

    public Resume(String uuid, String fullName) {
        this.fullName = fullName;
        this.uuid = uuid;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    @Override
    public String toString() {
        return uuid + " | " + fullName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Resume resume = (Resume) o;
        return Objects.equals(uuid, resume.uuid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid);
    }

    @Override
    public int compareTo(Resume o) {
        return uuid.compareTo(o.uuid);
    }
}
