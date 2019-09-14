<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <jsp:useBean id="resume" type="com.vkopendoh.webapp.model.Resume" scope="request"/>
    <title>Резюме ${resume.fullName}</title>
</head>
<body>
<jsp:include page="fragments/header.jsp"/>
<section>
    <h2>${resume.fullName}&nbsp;<a href="resume?uuid=${resume.uuid}&action=edit">Edit</a></h2>
    <p>
        <c:forEach var="contactEntry" items="${resume.contacts}">
            <jsp:useBean id="contactEntry"
                         type="java.util.Map.Entry<com.vkopendoh.webapp.model.ContactType, java.lang.String>"/>
            <%=contactEntry.getKey().toHtml(contactEntry.getValue())%><br/>
        </c:forEach>
    </p>
    <p>
    <c:forEach var="sectionEntry" items="${resume.sections}">
        <jsp:useBean id="sectionEntry"
                     type="java.util.Map.Entry<com.vkopendoh.webapp.model.SectionType, com.vkopendoh.webapp.model.Section>"/>
        <c:set var="sectionType" value="${sectionEntry.key}"/>
        <c:choose>
            <c:when test="${(sectionType.name() == 'PERSONAL' || sectionType.name() =='OBJECTIVE') && sectionEntry.value.content.trim().length() != 0}">
                <h3>${sectionType.title}</h3>
                <p>
                        ${sectionEntry.value.content}
                </p>
            </c:when>
            <c:when test="${(sectionType.name() == 'ACHIEVEMENT' || sectionType.name() =='QUALIFICATIONS') && !sectionEntry.value.content.isEmpty()}">
                <h3>${sectionType.title}</h3>
                <p>
                    <c:forEach var="text" items="${sectionEntry.value.content}">
                        ${text}
                        <br><br>
                    </c:forEach>
                </p>
            </c:when>
            <c:when test="${(sectionType.name() == 'EXPERIENCE' || sectionType.name() == 'EDUCATION')&& !sectionEntry.value.content.isEmpty()}">
                <h3>${sectionType.title}</h3>
                <p>
                <c:forEach var="organization" items="${sectionEntry.value.content}">
                    <i>Наименование организации: </i>
                    <a href="${organization.homePage.url}">${organization.homePage.name}</a>
                    <br><br>
                    <c:forEach var="exp" items="${organization.experiences}">
                        ${exp.startDate} &mdash; ${exp.endDate}
                        <br><br>
                        <i>Позиция/Опыт: </i> ${exp.title}
                        <br><br>
                        <i>Описание: </i><br>${exp.description}
                        <hr>
                    </c:forEach>
                </c:forEach>
                </p>
            </c:when>
        </c:choose>

    </c:forEach>
    </p>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>
