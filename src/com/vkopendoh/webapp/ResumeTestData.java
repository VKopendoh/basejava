package com.vkopendoh.webapp;

import com.vkopendoh.webapp.model.*;

import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class ResumeTestData {
    public static void main(String[] args) {
        Resume r = createResume("Григорий Кислин", "uuid1");
        System.out.println(r.getFullName() + "\n");
        Map<ContactType, String> allContacts = r.getContacts();
        for (Map.Entry<ContactType, String> contact : allContacts.entrySet()) {
            System.out.println(contact.getKey().getTitle() + " : " + contact.getValue());
        }
        System.out.println();
        Map<SectionType, Section> allSections = r.getSections();

        for (Map.Entry<SectionType, Section> section : allSections.entrySet()) {
            SectionType sectionType = section.getKey();
            Section sectionContent = section.getValue();
            System.out.println(sectionType.getTitle());
            switch (sectionType) {
                case OBJECTIVE:
                case PERSONAL:
                    TextSection textSection = (TextSection) sectionContent;
                    System.out.println(textSection.getContent() + "\n");
                    break;
                case ACHIEVEMENT:
                case QUALIFICATIONS:
                    TextListSection textListSection = (TextListSection) sectionContent;
                    for (String ach : textListSection.getContent()) {
                        System.out.println(ach);
                    }
                    System.out.println();
            }
        }
    }
    public static Resume createResume(String uuid, String name){
        Resume resume = new Resume(uuid, name);
        Map<ContactType, String> contacts = new EnumMap<>(ContactType.class);
        contacts.put(ContactType.PHONE, "+7(921) 855-0482");
        contacts.put(ContactType.SKYPE, "grigory.kislin");
        contacts.put(ContactType.EMAIL, "gkislin@yandex.ru");
        contacts.put(ContactType.LINKEDIN, "Профиль LinkedIn");
        contacts.put(ContactType.GITHUB, "Профиль GitHub");
        contacts.put(ContactType.STACKOVERFLOW, "Профиль Stackoverflow");
        contacts.put(ContactType.HOMEPAGE, "Домашняя страница");

        TextSection objective = new TextSection("Ведущий стажировок и корпоративного обучения по Java Web и Enterprise технологиям");

        TextSection personal = new TextSection("Аналитический склад ума, сильная логика, креативность, инициативность. Пурист кода и архитектуры.");

        List<String> achievmentsList = new ArrayList<>();
        achievmentsList.add("С 2013 года: разработка проектов \"Разработка Web приложения\",\"Java Enterprise\", \"Многомодульный maven. Многопоточность. XML (JAXB/StAX). Веб сервисы (JAX-RS/SOAP). Удаленное взаимодействие (JMS/AKKA)\". Организация онлайн стажировок и ведение проектов. Более 1000 выпускников.");
        achievmentsList.add("Реализация двухфакторной аутентификации для онлайн платформы управления проектами Wrike. Интеграция с Twilio, DuoSecurity, Google Authenticator, Jira, Zendesk.");
        achievmentsList.add("Налаживание процесса разработки и непрерывной интеграции ERP системы River BPM. Интеграция с 1С, Bonita BPM, CMIS, LDAP. Разработка приложения управления окружением на стеке: Scala/Play/Anorm/JQuery. Разработка SSO аутентификации и авторизации различных ERP модулей, интеграция CIFS/SMB java сервера.");
        achievmentsList.add("Реализация c нуля Rich Internet Application приложения на стеке технологий JPA, Spring, Spring-MVC, GWT, ExtGWT (GXT), Commet, HTML5, Highstock для алгоритмического трейдинга.");
        achievmentsList.add("Создание JavaEE фреймворка для отказоустойчивого взаимодействия слабо-связанных сервисов (SOA-base архитектура, JAX-WS, JMS, AS Glassfish). Сбор статистики сервисов и информации о состоянии через систему мониторинга Nagios. Реализация онлайн клиента для администрирования и мониторинга системы по JMX (Jython/ Django).");
        achievmentsList.add("Реализация протоколов по приему платежей всех основных платежных системы России (Cyberplat, Eport, Chronopay, Сбербанк), Белоруcсии(Erip, Osmp) и Никарагуа.");
        TextListSection achievment = new TextListSection(achievmentsList);

        List<String> qualificationsList = new ArrayList<>();
        qualificationsList.add("С 2013 года: разработка проектов \"Разработка Web приложения\",\"Java Enterprise\", \"Многомодульный maven. Многопоточность. XML (JAXB/StAX). Веб сервисы (JAX-RS/SOAP). Удаленное взаимодействие (JMS/AKKA)\". Организация онлайн стажировок и ведение проектов. Более 1000 выпускников.");
        qualificationsList.add("Реализация двухфакторной аутентификации для онлайн платформы управления проектами Wrike. Интеграция с Twilio, DuoSecurity, Google Authenticator, Jira, Zendesk.");
        qualificationsList.add("Налаживание процесса разработки и непрерывной интеграции ERP системы River BPM. Интеграция с 1С, Bonita BPM, CMIS, LDAP. Разработка приложения управления окружением на стеке: Scala/Play/Anorm/JQuery. Разработка SSO аутентификации и авторизации различных ERP модулей, интеграция CIFS/SMB java сервера.");
        qualificationsList.add("Реализация c нуля Rich Internet Application приложения на стеке технологий JPA, Spring, Spring-MVC, GWT, ExtGWT (GXT), Commet, HTML5, Highstock для алгоритмического трейдинга.");
        qualificationsList.add("Создание JavaEE фреймворка для отказоустойчивого взаимодействия слабо-связанных сервисов (SOA-base архитектура, JAX-WS, JMS, AS Glassfish). Сбор статистики сервисов и информации о состоянии через систему мониторинга Nagios. Реализация онлайн клиента для администрирования и мониторинга системы по JMX (Jython/ Django).");
        qualificationsList.add("Реализация протоколов по приему платежей всех основных платежных системы России (Cyberplat, Eport, Chronopay, Сбербанк), Белоруcсии(Erip, Osmp) и Никарагуа.\n");
        TextListSection qualifications = new TextListSection(qualificationsList);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/yyyy");

        List<Experience> expList1 = new ArrayList<>();
        expList1.add(new Experience(YearMonth.parse("10/2013", formatter), YearMonth.now(), "Автор проекта.", "Создание, организация и проведение Java онлайн проектов и стажировок."));
        Organization exp1 = new Organization("Java Online Projects", "", expList1);

        List<Experience> expList2 = new ArrayList<>();
        expList2.add(new Experience(YearMonth.parse("10/2014", formatter), YearMonth.parse("01/2016", formatter), "Старший разработчик (backend)", "Проектирование и разработка онлайн платформы управления проектами Wrike (Java 8 API, Maven, Spring, MyBatis, Guava, Vaadin, PostgreSQL, Redis). Двухфакторная аутентификация, авторизация по OAuth1, OAuth2, JWT SSO.\n"));
        Organization exp2 = new Organization("Wrike", "", expList2);

        List<Experience> expList3 = new ArrayList<>();
        expList3.add(new Experience(YearMonth.parse("04/2012", formatter), YearMonth.parse("10/2014", formatter), "Java архитектор", "Организация процесса разработки системы ERP для разных окружений: релизная политика, версионирование, ведение CI (Jenkins), миграция базы (кастомизация Flyway), конфигурирование системы (pgBoucer, Nginx), AAA via SSO. Архитектура БД и серверной части системы. Разработка интергационных сервисов: CMIS, BPMN2, 1C (WebServices), сервисов общего назначения (почта, экспорт в pdf, doc, html). Интеграция Alfresco JLAN для online редактирование из браузера документов MS Office. Maven + plugin development, Ant, Apache Commons, Spring security, Spring MVC, Tomcat,WSO2, xcmis, OpenCmis, Bonita, Python scripting, Unix shell remote scripting via ssh tunnels, PL/Python"));
        Organization exp3 = new Organization("RIT Center", "", expList3);

        List<Experience> expList4 = new ArrayList<>();
        expList4.add(new Experience(YearMonth.parse("12/2010", formatter), YearMonth.parse("04/2012", formatter), "Ведущий программист", "Участие в проекте Deutsche Bank CRM (WebLogic, Hibernate, Spring, Spring MVC, SmartGWT, GWT, Jasper, Oracle). Реализация клиентской и серверной части CRM. Реализация RIA-приложения для администрирования, мониторинга и анализа результатов в области алгоритмического трейдинга. JPA, Spring, Spring-MVC, GWT, ExtGWT (GXT), Highstock, Commet, HTML5."));
        Organization exp4 = new Organization("Luxoft (Deutsche Bank)", "", expList4);

        List<Experience> expList5 = new ArrayList<>();
        expList5.add(new Experience(YearMonth.parse("06/2008", formatter), YearMonth.parse("12/2010", formatter), "Ведущий специалист", "Дизайн и имплементация Java EE фреймворка для отдела \"Платежные Системы\" (GlassFish v2.1, v3, OC4J, EJB3, JAX-WS RI 2.1, Servlet 2.4, JSP, JMX, JMS, Maven2). Реализация администрирования, статистики и мониторинга фреймворка. Разработка online JMX клиента (Python/ Jython, Django, ExtJS)"));
        Organization exp5 = new Organization("Yota", "", expList5);

        List<Experience> expList6 = new ArrayList<>();
        expList6.add(new Experience(YearMonth.parse("03/2007", formatter), YearMonth.parse("06/2008", formatter), "Разработчик ПО", "Реализация клиентской (Eclipse RCP) и серверной (JBoss 4.2, Hibernate 3.0, Tomcat, JMS) частей кластерного J2EE приложения (OLAP, Data mining)."));
        Organization exp6 = new Organization("Enkata", "", expList6);

        List<Experience> expList7 = new ArrayList<>();
        expList7.add(new Experience(YearMonth.parse("01/2005", formatter), YearMonth.parse("02/2007", formatter), "Разработчик ПО", "Разработка информационной модели, проектирование интерфейсов, реализация и отладка ПО на мобильной IN платформе Siemens @vantage (Java, Unix)."));
        final Organization exp7 = new Organization("Siemens AG", "", expList7);

        List<Experience> expList8 = new ArrayList<>();
        expList8.add(new Experience(YearMonth.parse("09/1997", formatter), YearMonth.parse("01/2005", formatter), "Инженер по аппаратному и программному тестированию", "Тестирование, отладка, внедрение ПО цифровой телефонной станции Alcatel 1000 S12 (CHILL, ASM)."));
        final Organization exp8 = new Organization("Alcatel", "", expList8);

        List<Organization> experienceList = new ArrayList<>();
        experienceList.add(exp1);
        experienceList.add(exp2);
        experienceList.add(exp3);
        experienceList.add(exp4);
        experienceList.add(exp5);
        experienceList.add(exp6);
        experienceList.add(exp7);
        experienceList.add(exp8);
        OrganizationSection experience = new OrganizationSection(experienceList);


        List<Experience> eduList1 = new ArrayList<>();
        eduList1.add(new Experience(YearMonth.parse("03/2013", formatter), YearMonth.parse("05/2013", formatter), "\"Functional Programming Principles in Scala\" by Martin Odersky\"", ""));
        Organization edu1 = new Organization("Coursera", "", eduList1);

        List<Experience> eduList2 = new ArrayList<>();
        eduList2.add(new Experience(YearMonth.parse("03/2011", formatter), YearMonth.parse("04/2011", formatter), "\"Курс \\\"Объектно-ориентированный анализ ИС. Концептуальное моделирование на UML.\\\"\"", ""));
        Organization edu2 = new Organization("Luxoft", "", eduList2);

        List<Experience> eduList3 = new ArrayList<>();
        eduList3.add(new Experience(YearMonth.parse("01/2005", formatter), YearMonth.parse("04/2005", formatter), "3 месяца обучения мобильным IN сетям (Берлин)", ""));
        Organization edu3 = new Organization("Siemens AG", "", eduList3);

        List<Experience> eduList4 = new ArrayList<>();
        eduList4.add(new Experience(YearMonth.parse("09/1997", formatter), YearMonth.parse("03/1998", formatter), "6 месяцев обучения цифровым телефонным сетям (Москва)", ""));
        Organization edu4 = new Organization("Alcatel", "", eduList4);

        List<Experience> eduList5 = new ArrayList<>();
        eduList5.add(new Experience(YearMonth.parse("09/1993", formatter), YearMonth.parse("07/1996", formatter), "Аспирантура (программист С, С++)", ""));
        eduList5.add(new Experience(YearMonth.parse("09/1987", formatter), YearMonth.parse("07/1993", formatter), "Инженер (программист Fortran, C)", ""));
        Organization edu5 = new Organization("Санкт-Петербургский национальный исследовательский университет информационных технологий, механики и оптики", "", eduList5);

        List<Experience> eduList6 = new ArrayList<>();
        eduList6.add(new Experience(YearMonth.parse("09/1984", formatter), YearMonth.parse("06/1987", formatter), "Закончил с отличием", ""));
        Organization edu6 = new Organization("Заочная физико-техническая школа при МФТИ", "", eduList6);

        List<Organization> educationList = new ArrayList<>();
        educationList.add(edu1);
        educationList.add(edu2);
        educationList.add(edu3);
        educationList.add(edu4);
        educationList.add(edu5);
        educationList.add(edu6);
        OrganizationSection education = new OrganizationSection(educationList);

        resume.setContacts(contacts);
        Map<SectionType, Section> sections = new EnumMap<>(SectionType.class);
        sections.put(SectionType.OBJECTIVE, objective);
        sections.put(SectionType.ACHIEVEMENT, achievment);
        sections.put(SectionType.EDUCATION, education);
        sections.put(SectionType.EXPERIENCE, experience);
        sections.put(SectionType.PERSONAL, personal);
        sections.put(SectionType.QUALIFICATIONS, qualifications);
        resume.setSections(sections);
        return resume;
    }

}