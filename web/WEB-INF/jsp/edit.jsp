<%@ page import="com.vkopendoh.webapp.model.ContactType" %>
<%@ page import="com.vkopendoh.webapp.model.SectionType" %>
<%@ page import="com.vkopendoh.webapp.model.Resume" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <jsp:useBean id="resume" type="com.vkopendoh.webapp.model.Resume" scope="request"/>
    <title>Резюме ${resume.fullName}</title>
    <script type="text/javascript">
        function add(stype) {
            var dd = document.createElement("dd");
            var element = document.createElement("textarea");
            element.setAttribute("rows", "3");
            element.setAttribute("cols", "150");
            element.setAttribute("name", stype);
            dd.appendChild(element);
            var targetTag = document.getElementById(stype);
            targetTag.appendChild(dd);
        }
    </script>
</head>
<body>
<jsp:include page="fragments/header.jsp"/>
<section>
    <form method="post" action="resume" enctype="application/x-www-form-urlencoded">
        <input type="hidden" name="uuid" value="${resume.uuid}">
        <dl>
            <dt>Имя:</dt>
            <dd><input type="text" name="fullName" size=50 value="${resume.fullName}"></dd>
        </dl>
        <h3>Контакты:</h3>
        <c:forEach var="type" items="<%=ContactType.values()%>">
            <dl>
                <dt>${type.title}</dt>
                <dd><input type="text" name="${type.name()}" size=30 value="${resume.getContact(type)}"></dd>
            </dl>
        </c:forEach>
        <h3>Секции:</h3>

        <c:forEach var="sectionType" items="<%=SectionType.values()%>">
            <dl>
                <dt id="${sectionType.name()}">${sectionType.title}</dt>
                <br>
                <c:choose>
                    <c:when test="${sectionType.name() == 'PERSONAL' || sectionType.name() =='OBJECTIVE'}">
                        <dd><input type="text" name="${sectionType.name()}" size=150
                                   value="${resume.getSection(sectionType).content}"></dd>
                    </c:when>
                    <c:when test="${sectionType.name() == 'ACHIEVEMENT' || sectionType.name() =='QUALIFICATIONS'}">
                        <c:forEach var="content" items="${resume.getSection(sectionType).content}">
                        <dd><textarea name="${sectionType.name()}" cols="150" rows="3">${content}
                                </textarea>
                        </dd>
                        </c:forEach>
                        <br>
                        <input type="button" name="addField"
                               value="Добавить" onclick="add('${sectionType.name()}');">
                    </c:when>
                </c:choose>
            </dl>
        </c:forEach>

        <hr>
        <button type="submit">Сохранить</button>
    </form>
    <button onclick="window.history.back()">Отменить</button>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>
