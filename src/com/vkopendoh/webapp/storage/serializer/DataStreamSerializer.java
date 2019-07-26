package com.vkopendoh.webapp.storage.serializer;

import com.vkopendoh.webapp.model.*;

import java.io.*;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DataStreamSerializer implements SerializationStrategy, WriterIOException {
    private static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/yyyy");

    @Override
    public void doWrite(Resume resume, OutputStream os) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(resume.getUuid());
            dos.writeUTF(resume.getFullName());
            Map<ContactType, String> contacts = resume.getContacts();
            dos.writeInt(contacts.size());
            writeWithException(contacts.entrySet(), dos, (ConsumerWithException<Map.Entry<ContactType, String>>) entry -> {
                dos.writeUTF(entry.getKey().name());
                dos.writeUTF(entry.getValue());
            });
            Map<SectionType, Section> sections = resume.getSections();
            dos.writeInt(sections.size());
            writeWithException(sections.entrySet(), dos, (ConsumerWithException<Map.Entry<SectionType, Section>>) entry -> {
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
                        dos.writeInt(content.size());
                        writeWithException(content, dos, (ConsumerWithException<String>) dos::writeUTF);
                        break;
                    case EDUCATION:
                    case EXPERIENCE:
                        OrganizationSection orgSection = (OrganizationSection) entry.getValue();
                        List<Organization> contentOrg = orgSection.getContent();
                        dos.writeInt(contentOrg.size());

                        writeWithException(contentOrg, dos, (ConsumerWithException<Organization>) org -> {
                            dos.writeUTF(org.getHomePage().getName());
                            dos.writeUTF(nullWriter(org.getHomePage().getUrl()));
                            List<Organization.Experience> experiences = org.getExperiences();
                            dos.writeInt(experiences.size());
                            writeWithException(experiences, dos, (ConsumerWithException<Organization.Experience>) exp -> {
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
            int size = dis.readInt();
            for (int i = 0; i < size; i++) {
                resume.addContact(ContactType.valueOf(dis.readUTF()), dis.readUTF());
            }
            size = dis.readInt();
            for (int i = 0; i < size; i++) {
                SectionType sectionType = SectionType.valueOf(dis.readUTF());
                switch (sectionType) {
                    case PERSONAL:
                    case OBJECTIVE:
                        resume.addSection(sectionType, new TextSection(dis.readUTF()));
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        int sizeTls = dis.readInt();
                        List<String> content = new ArrayList<>();
                        for (int j = 0; j < sizeTls; j++) {
                            content.add(dis.readUTF());
                        }
                        resume.addSection(sectionType, new TextListSection(content));
                        break;
                    case EDUCATION:
                    case EXPERIENCE:
                        int sizeOls = dis.readInt();
                        List<Organization> organizations = new ArrayList<>();
                        for (int j = 0; j < sizeOls; j++) {
                            Link homePage = new Link(dis.readUTF(), nullReader(dis.readUTF()));
                            int sizeExp = dis.readInt();
                            List<Organization.Experience> experiences = new ArrayList<>();
                            for (int k = 0; k < sizeExp; k++) {
                                experiences.add(new Organization.Experience(YearMonth.parse(dis.readUTF(), dtf)
                                        , YearMonth.parse(dis.readUTF(), dtf)
                                        , dis.readUTF(), nullReader(dis.readUTF())));
                            }
                            organizations.add(new Organization(homePage, experiences));
                        }
                        resume.addSection(sectionType, new OrganizationSection(organizations));
                        break;
                }
            }
            return resume;
        }
    }

    private String nullWriter(String str) {
        return str == null ? "null" : str;
    }

    private String nullReader(String str) {
        return str.equals("null") ? null : str;
    }
}