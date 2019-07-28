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
            Iterator<Map.Entry<ContactType, String>> it = contacts.entrySet().iterator();
            writeWithException(contacts.entrySet(), dos, () -> {
                Map.Entry<ContactType, String> entry = it.next();
                dos.writeUTF(entry.getKey().name());
                dos.writeUTF(entry.getValue());
            });
            Map<SectionType, Section> sections = resume.getSections();
            Iterator<Map.Entry<SectionType, Section>> sectionIterator = sections.entrySet().iterator();
            writeWithException(sections.entrySet(), dos, () -> {
                Map.Entry<SectionType, Section> sectionEntry = sectionIterator.next();
                dos.writeUTF(sectionEntry.getKey().name());
                switch (sectionEntry.getKey()) {
                    case PERSONAL:
                    case OBJECTIVE:
                        TextSection textSection = (TextSection) sectionEntry.getValue();
                        dos.writeUTF(textSection.getContent());
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        TextListSection textListSection = (TextListSection) sectionEntry.getValue();
                        Iterator<String> tlsIterator = textListSection.getContent().iterator();
                        writeWithException(textListSection.getContent(), dos, () -> {
                            String tlsEnry = tlsIterator.next();
                            dos.writeUTF(tlsEnry);
                        });
                        break;
                    case EDUCATION:
                    case EXPERIENCE:
                        OrganizationSection orgSection = (OrganizationSection) sectionEntry.getValue();
                        Iterator<Organization> orgIterator = orgSection.getContent().iterator();
                        writeWithException(orgSection.getContent(), dos, () -> {
                            Organization org = orgIterator.next();
                            dos.writeUTF(org.getHomePage().getName());
                            dos.writeUTF(nullWriter(org.getHomePage().getUrl()));
                            Iterator<Organization.Experience> expIterator = org.getExperiences().iterator();
                            writeWithException(org.getExperiences(), dos, () -> {
                                Organization.Experience exp = expIterator.next();
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
            readWithException(dis, () -> resume.addContact(ContactType.valueOf(dis.readUTF()), dis.readUTF()));
            readWithException(dis, () -> {
                SectionType sectionType = SectionType.valueOf(dis.readUTF());
                switch (sectionType) {
                    case PERSONAL:
                    case OBJECTIVE:
                        resume.addSection(sectionType, new TextSection(dis.readUTF()));
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        List<String> content = new ArrayList<>();
                        DataStreamSerializer.this.readWithException(dis, () -> content.add(dis.readUTF()));
                        resume.addSection(sectionType, new TextListSection(content));
                        break;
                    case EDUCATION:
                    case EXPERIENCE:
                        List<Organization> organizations = new ArrayList<>();
                        DataStreamSerializer.this.readWithException(dis, () -> {
                            Link homePage = new Link(dis.readUTF(), DataStreamSerializer.this.nullReader(dis.readUTF()));
                            List<Organization.Experience> experiences = new ArrayList<>();
                            DataStreamSerializer.this.readWithException(dis, () -> experiences.add(new Organization.Experience(YearMonth.parse(dis.readUTF(), dtf)
                                    , YearMonth.parse(dis.readUTF(), dtf)
                                    , dis.readUTF(), DataStreamSerializer.this.nullReader(dis.readUTF()))));
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

    private void writeWithException(Collection collection, DataOutputStream dos, ConsumerWithException
            action) throws IOException {
        dos.writeInt(collection.size());
        Objects.requireNonNull(action);
        for (Object o : collection) {
            action.accept();
        }
    }

    private void readWithException(DataInputStream dis, ConsumerWithException action) throws IOException {
        int size = dis.readInt();
        Objects.requireNonNull(action);
        for (int i = 0; i < size; i++) {
            action.accept();
        }
    }
}
