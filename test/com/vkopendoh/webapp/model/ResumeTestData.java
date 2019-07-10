package com.vkopendoh.webapp.model;

import com.sun.org.apache.xerces.internal.xs.StringList;

import java.util.*;

public class ResumeTestData {
    public static void main(String[] args) {
        Resume resume = new Resume("Григорий Кислин");
        Map<String, String> contacts = new HashMap<>();
        contacts.put("Тел", "+7(921) 855-0482");
        contacts.put("Почта", "gkislin@yandex.ru");
        contacts.put("Ссылка 5", "Профиль LinkedIn");
        contacts.put("Ссылка 2", "Профиль GitHub");
        contacts.put("Ссылка 3", "Профиль Stackoverflow");
        contacts.put("Ссылка 4", "Домашняя страница");

        TextSection  objective = new TextSection(SectionType.OBJECTIVE, "Ведущий стажировок и корпоративного обучения по Java Web и Enterprise технологиям");

        TextSection  personal = new TextSection(SectionType.PERSONAL, "Аналитический склад ума, сильная логика, креативность, инициативность. Пурист кода и архитектуры.");

        List<String> achievmentsList = new ArrayList<>();
        achievmentsList.add("С 2013 года: разработка проектов \"Разработка Web приложения\",\"Java Enterprise\", \"Многомодульный maven. Многопоточность. XML (JAXB/StAX). Веб сервисы (JAX-RS/SOAP). Удаленное взаимодействие (JMS/AKKA)\". Организация онлайн стажировок и ведение проектов. Более 1000 выпускников.");
        achievmentsList.add("Реализация двухфакторной аутентификации для онлайн платформы управления проектами Wrike. Интеграция с Twilio, DuoSecurity, Google Authenticator, Jira, Zendesk.");
        achievmentsList.add("Налаживание процесса разработки и непрерывной интеграции ERP системы River BPM. Интеграция с 1С, Bonita BPM, CMIS, LDAP. Разработка приложения управления окружением на стеке: Scala/Play/Anorm/JQuery. Разработка SSO аутентификации и авторизации различных ERP модулей, интеграция CIFS/SMB java сервера.");
        achievmentsList.add("Реализация c нуля Rich Internet Application приложения на стеке технологий JPA, Spring, Spring-MVC, GWT, ExtGWT (GXT), Commet, HTML5, Highstock для алгоритмического трейдинга.");
        achievmentsList.add("Создание JavaEE фреймворка для отказоустойчивого взаимодействия слабо-связанных сервисов (SOA-base архитектура, JAX-WS, JMS, AS Glassfish). Сбор статистики сервисов и информации о состоянии через систему мониторинга Nagios. Реализация онлайн клиента для администрирования и мониторинга системы по JMX (Jython/ Django).");
        achievmentsList.add("Реализация протоколов по приему платежей всех основных платежных системы России (Cyberplat, Eport, Chronopay, Сбербанк), Белоруcсии(Erip, Osmp) и Никарагуа.");
        TextListSection achievment = new TextListSection(SectionType.ACHIEVEMENT,achievmentsList);

        List<String> qualificationsList = new ArrayList<>();
        qualificationsList.add("С 2013 года: разработка проектов \"Разработка Web приложения\",\"Java Enterprise\", \"Многомодульный maven. Многопоточность. XML (JAXB/StAX). Веб сервисы (JAX-RS/SOAP). Удаленное взаимодействие (JMS/AKKA)\". Организация онлайн стажировок и ведение проектов. Более 1000 выпускников.");
        qualificationsList.add("Реализация двухфакторной аутентификации для онлайн платформы управления проектами Wrike. Интеграция с Twilio, DuoSecurity, Google Authenticator, Jira, Zendesk.");
        qualificationsList.add("Налаживание процесса разработки и непрерывной интеграции ERP системы River BPM. Интеграция с 1С, Bonita BPM, CMIS, LDAP. Разработка приложения управления окружением на стеке: Scala/Play/Anorm/JQuery. Разработка SSO аутентификации и авторизации различных ERP модулей, интеграция CIFS/SMB java сервера.");
        qualificationsList.add("Реализация c нуля Rich Internet Application приложения на стеке технологий JPA, Spring, Spring-MVC, GWT, ExtGWT (GXT), Commet, HTML5, Highstock для алгоритмического трейдинга.\n");
        qualificationsList.add("Создание JavaEE фреймворка для отказоустойчивого взаимодействия слабо-связанных сервисов (SOA-base архитектура, JAX-WS, JMS, AS Glassfish). Сбор статистики сервисов и информации о состоянии через систему мониторинга Nagios. Реализация онлайн клиента для администрирования и мониторинга системы по JMX (Jython/ Django).\n");
        qualificationsList.add("Реализация протоколов по приему платежей всех основных платежных системы России (Cyberplat, Eport, Chronopay, Сбербанк), Белоруcсии(Erip, Osmp) и Никарагуа.\n");
        TextListSection qualifications = new TextListSection(SectionType.QUALIFICATIONS,qualificationsList);

        List<String> expList1 = new ArrayList<>();
        expList1.add("Автор проекта.");
        expList1.add("Создание, организация и проведение Java онлайн проектов и стажировок.");
        RefDateDescription exp1 = new RefDateDescription("Java Online Projects", "10/2013","Сейчас", expList1);

        List<String> expList2 = new ArrayList<>();
        expList2.add("Старший разработчик (backend)");
        expList2.add("Проектирование и разработка онлайн платформы управления проектами Wrike (Java 8 API, Maven, Spring, MyBatis, Guava, Vaadin, PostgreSQL, Redis). Двухфакторная аутентификация, авторизация по OAuth1, OAuth2, JWT SSO.\n");
        RefDateDescription exp2 = new RefDateDescription("Wrike", "10/2014","01/2016", expList2);

        List<String> expList3 = new ArrayList<>();
        expList3.add("Java архитектор");
        expList3.add("Организация процесса разработки системы ERP для разных окружений: релизная политика, версионирование, ведение CI (Jenkins), миграция базы (кастомизация Flyway), конфигурирование системы (pgBoucer, Nginx), AAA via SSO. Архитектура БД и серверной части системы. Разработка интергационных сервисов: CMIS, BPMN2, 1C (WebServices), сервисов общего назначения (почта, экспорт в pdf, doc, html). Интеграция Alfresco JLAN для online редактирование из браузера документов MS Office. Maven + plugin development, Ant, Apache Commons, Spring security, Spring MVC, Tomcat,WSO2, xcmis, OpenCmis, Bonita, Python scripting, Unix shell remote scripting via ssh tunnels, PL/Python");
        RefDateDescription exp3 = new RefDateDescription("RIT Center", "04/2012","10/2014", expList3);

        List<String> expList4 = new ArrayList<>();
        expList4.add("Ведущий программист");
        expList4.add("Участие в проекте Deutsche Bank CRM (WebLogic, Hibernate, Spring, Spring MVC, SmartGWT, GWT, Jasper, Oracle). Реализация клиентской и серверной части CRM. Реализация RIA-приложения для администрирования, мониторинга и анализа результатов в области алгоритмического трейдинга. JPA, Spring, Spring-MVC, GWT, ExtGWT (GXT), Highstock, Commet, HTML5.");
        RefDateDescription exp4 = new RefDateDescription("Luxoft (Deutsche Bank)", "12/2010 ","04/2012", expList4);

        List<String> expList5 = new ArrayList<>();
        expList5.add("Ведущий специалист");
        expList5.add("Дизайн и имплементация Java EE фреймворка для отдела \"Платежные Системы\" (GlassFish v2.1, v3, OC4J, EJB3, JAX-WS RI 2.1, Servlet 2.4, JSP, JMX, JMS, Maven2). Реализация администрирования, статистики и мониторинга фреймворка. Разработка online JMX клиента (Python/ Jython, Django, ExtJS)\n");
        RefDateDescription exp5 = new RefDateDescription("Yota", "06/2008","12/2010", expList5);

        List<String> expList6 = new ArrayList<>();
        expList6.add("Разработчик ПО");
        expList6.add("Реализация клиентской (Eclipse RCP) и серверной (JBoss 4.2, Hibernate 3.0, Tomcat, JMS) частей кластерного J2EE приложения (OLAP, Data mining).\n");
        RefDateDescription exp6 = new RefDateDescription("Enkata", "03/2007","06/2008", expList6);

        List<String> expList7 = new ArrayList<>();
        expList7.add("Разработчик ПО");
        expList7.add("Разработка информационной модели, проектирование интерфейсов, реализация и отладка ПО на мобильной IN платформе Siemens @vantage (Java, Unix).\n");
        final RefDateDescription exp7 = new RefDateDescription("Siemens AG", "01/2005","02/2007", expList7);

        List<String> expList8 = new ArrayList<>();
        expList8.add("Инженер по аппаратному и программному тестированию");
        expList8.add("Тестирование, отладка, внедрение ПО цифровой телефонной станции Alcatel 1000 S12 (CHILL, ASM).");
        final RefDateDescription exp8 = new RefDateDescription("Alcatel", "09/1997","01/2005", expList8);

        List<RefDateDescription> experienceList = new ArrayList<>();
        experienceList.add(exp1);
        experienceList.add(exp2);
        experienceList.add(exp3);
        experienceList.add(exp4);
        experienceList.add(exp5);
        experienceList.add(exp6);
        experienceList.add(exp7);
        experienceList.add(exp8);
        ObjectListSection experience = new ObjectListSection(SectionType.EXPERIENCE,experienceList);

        List<String> eduList1 = new ArrayList<>();
        eduList1.add("\"Functional Programming Principles in Scala\" by Martin Odersky\"");
        RefDateDescription edu1 = new RefDateDescription("Coursera", "03/2013","05/2013", eduList1);

        List<String> eduList2 = new ArrayList<>();
        eduList2.add("Курс \"Объектно-ориентированный анализ ИС. Концептуальное моделирование на UML.\"");
        RefDateDescription edu2 = new RefDateDescription("Luxoft", "03/2011","04/2011", eduList2);

        List<String> eduList3 = new ArrayList<>();
        eduList3.add("3 месяца обучения мобильным IN сетям (Берлин)");
        RefDateDescription edu3 = new RefDateDescription("Siemens AG", "01/2005","04/2005", eduList3);

        List<String> eduList4 = new ArrayList<>();
        eduList4.add("6 месяцев обучения цифровым телефонным сетям (Москва)");
        RefDateDescription edu4 = new RefDateDescription("Alcatel", "09/1997","03/1998", eduList4);

        List<String> eduList5 = new ArrayList<>();
        eduList5.add("Аспирантура (программист С, С++)");
        eduList5.add("Инженер (программист Fortran, C)");
        RefDateDescription edu5 = new RefDateDescription("Санкт-Петербургский национальный исследовательский университет информационных технологий, механики и оптики", "09/1993","07/1996", eduList5);

        List<String> eduList6 = new ArrayList<>();
        eduList6.add("Закончил с отличием");
        RefDateDescription edu6 = new RefDateDescription("Заочная физико-техническая школа при МФТИ", "09/1984","06/1987", eduList6);

        List<RefDateDescription> educationList = new ArrayList<>();
        educationList.add(edu1);
        educationList.add(edu2);
        educationList.add(edu3);
        educationList.add(edu4);
        educationList.add(edu5);
        educationList.add(edu6);
        ObjectListSection education = new ObjectListSection(SectionType.EDUCATION,educationList);

        resume.setContacts(contacts);
        Map<SectionType,Section> sections = new HashMap<>();
        sections.put(SectionType.OBJECTIVE,objective);
        sections.put(SectionType.ACHIEVEMENT, achievment);
        sections.put(SectionType.EDUCATION,education);
        sections.put(SectionType.EXPERIENCE,experience);
        sections.put(SectionType.PERSONAL,personal);
        sections.put(SectionType.QUALIFICATIONS,qualifications);

        Map<String,String> allContacts = resume.getContacts();
        for (Map.Entry<String, String> contact: allContacts.entrySet()) {
            System.out.println(contact.getKey() + " : " + contact.getValue());
        }
        System.out.println("\n\n");

        resume.setSections(sections);
        Map<SectionType,Section> allSections = resume.getSections();

        /*Set<SectionType> st = allSections.keySet();
        for (SectionType sk:st) {
            System.out.println(sk.getTitle());
        }*/
            for (Map.Entry<SectionType,Section> section: allSections.entrySet()){
                SectionType sectionType = section.getKey();
                Section sectionContent = section.getValue();
                System.out.println(sectionType.getTitle());
                switch (sectionType){
                    case OBJECTIVE:
                    case PERSONAL:
                        System.out.println(sectionContent.getContent());
                        break;
                    case ACHIEVEMENT:
                        for (String ach:(List<String>)sectionContent.getContent()) {
                            System.out.println(ach + "\n");
                        }
                }
            }

        /*resume.setContacts(contacts);
        resume.setPersonal(personal);
        resume.setObjective(objective);
        resume.setAchievement(achievment);
        resume.setQualifications(qualifications);
        resume.setExperience(experience);
        resume.setEducation(education);

        System.out.println(resume.getFullName());
        System.out.println(resume.getObjective().getSectionType().getTitle());
        System.out.println(resume.getObjective().getContent());
        System.out.println(resume.getPersonal().getContent());

        System.out.println(resume.getAchievement().getSectionType().getTitle());
        for (String text:resume.getAchievement().getContent()) {
            System.out.println(text + "\n");
        }
*/

    }
}