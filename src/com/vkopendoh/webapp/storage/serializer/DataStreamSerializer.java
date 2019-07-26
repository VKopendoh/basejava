package com.vkopendoh.webapp.storage.serializer;

import com.vkopendoh.webapp.model.*;

import java.io.*;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class DataStreamSerializer implements SerializationStrategy {
    private static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/yyyy");

    @Override
    public void doWrite(Resume resume, OutputStream os) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(resume.getUuid());
            dos.writeUTF(resume.getFullName());
            Map<ContactType, String> contacts = resume.getContacts();
            writeWithException(contacts.entrySet(), dos, entry -> {
                dos.writeUTF(entry.getKey().name());
                dos.writeUTF(entry.getValue());
            });
            Map<SectionType, Section> sections = resume.getSections();
            writeWithException(sections.entrySet(), dos, entry -> {
                SectionType sectionType = entry.getKey();
                dos.writeUTF(sectionType.name());
                switch (sectionType) {
                    case PERSONAL:
                    case OBJECTIVE:
                        TextSection textSection = (TextSection) entry.getValue();
                        dos.writeUTF(textSection.getContent());
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        TextListSection section = (TextListSection) entry.getValue();
                        List<String> content = section.getContent();
                        writeWithException(content, dos, dos::writeUTF);
                        break;
                    case EDUCATION:
                    case EXPERIENCE:
                        OrganizationSection orgSection = (OrganizationSection) entry.getValue();
                        List<Organization> contentOrg = orgSection.getContent();
                        writeWithException(contentOrg, dos, org -> {
                            dos.writeUTF(org.getHomePage().getName());
                            dos.writeUTF(nullWriter(org.getHomePage().getUrl()));
                            List<Organization.Experience> experiences = org.getExperiences();
                            writeWithException(experiences, dos, exp -> {
                                dos.writeUTF(exp.getStartDate().format(dtf));
                                dos.writeUTF(exp.getEndDate().format(dtf));
                                dos.writeUTF(exp.getTitle());
                                dos.writeUTF(nullWriter(exp.getDescription()));
                            });
                        });
                        break;
                }
            });
        }
    }

    @Override
    public Resume doRead(InputStream is) throws IOException {
        try (DataInputStream dis = new DataInputStream(is)) {
            Resume resume = new Resume(dis.readUTF(), dis.readUTF());
            readWithException(dis, o -> resume.addContact(ContactType.valueOf(dis.readUTF()), dis.readUTF()));
            readWithException(dis, section -> {
                SectionType sectionType = SectionType.valueOf(dis.readUTF());
                switch (sectionType) {
                    case PERSONAL:
                    case OBJECTIVE:
                        resume.addSection(sectionType, new TextSection(dis.readUTF()));
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        List<String> content = new ArrayList<>();
                        readWithException(dis, s -> content.add(dis.readUTF()));
                        resume.addSection(sectionType, new TextListSection(content));
                        break;
                    case EDUCATION:
                    case EXPERIENCE:
                        List<Organization> organizations = new ArrayList<>();
                        readWithException(dis, organization -> {
                            Link homePage = new Link(dis.readUTF(), nullReader(dis.readUTF()));
                            List<Organization.Experience> experiences = new ArrayList<>();
                            readWithException(dis, o -> experiences.add(new Organization.Experience(YearMonth.parse(dis.readUTF(), dtf)
                                    , YearMonth.parse(dis.readUTF(), dtf)
                                    , dis.readUTF(), nullReader(dis.readUTF()))));
                            organizations.add(new Organization(homePage, experiences));
                        });
                        resume.addSection(sectionType, new OrganizationSection(organizations));
                        break;
                }
            });
            return resume;
        }
    }

    private String nullWriter(String str) {
        return str == null ? "" : str;
    }

    private String nullReader(String str) {
        return str.equals("") ? null : str;
    }

    private <T> void writeWithException(Collection<T> collection, DataOutputStream dos, ConsumerWithException<T> action) throws IOException {
        dos.writeInt(collection.size());
        Objects.requireNonNull(action);
        for (T t : collection) {
            action.accept(t);
        }
    }

    private void readWithException(DataInputStream dis, ConsumerWithException action) throws IOException {
        int size = dis.readInt();
        Objects.requireNonNull(action);
        for (int i = 0; i < size; i++) {
            action.accept(null);
        }
    }
}
