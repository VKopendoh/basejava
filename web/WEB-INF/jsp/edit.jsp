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
        function textAreaAdjust(o) {
            o.style.height = "1px";
            o.style.height = (25+o.scrollHeight)+"px";
        }
        function add(stype) {
            var dd = document.createElement("dd");
            var inputName = document.createElement("input");
            var inputTxtPos = document.createElement("input");
            var inputUrl = document.createElement("input");
            var startDate = document.createElement("input");
            var endDate = document.createElement("input");
            var br = document.createElement("BR");
            var description = document.createElement("textarea");
            var nameTxt = document.createTextNode("Название организации: ");
            var urlTxt = document.createTextNode("Ссылка на домашнюю страницу организации: ");
            var dash = document.createTextNode(" - ");
            var posTxt = document.createTextNode("Позиция: ");
            var descTxt = document.createTextNode("Описание: ");
            description.setAttribute("name",stype+"."+"description");
            inputName.setAttribute("type","text");
            inputName.setAttribute("name",stype+"."+"orgName");
            inputUrl.setAttribute("type","url");
            inputUrl.setAttribute("name",stype+"."+"orgUrl");
            inputTxtPos.setAttribute("type","text");
            inputTxtPos.setAttribute("name", stype+"."+"position");
            startDate.setAttribute("type","month");
            startDate.setAttribute("name",stype+"."+"startDate");
            endDate.setAttribute("type","month");
            endDate.setAttribute("name",stype+"."+"endDate");
            description.setAttribute("cols", "150");
            description.setAttribute("onclick", "textAreaAdjust(this)");
            dd.appendChild(nameTxt);
            dd.appendChild(inputName);
            dd.appendChild(br);
            dd.appendChild(br.cloneNode());
            dd.appendChild(urlTxt);
            dd.appendChild(inputUrl);
            dd.appendChild(br.cloneNode());
            dd.appendChild(br.cloneNode());
            dd.appendChild(startDate);
            dd.appendChild(dash);
            dd.appendChild(endDate);
            dd.appendChild(br.cloneNode());
            dd.appendChild(br.cloneNode());
            dd.appendChild(posTxt);
            dd.appendChild(inputTxtPos);
            dd.appendChild(br.cloneNode());
            dd.appendChild(br.cloneNode());
            dd.appendChild(descTxt);
            dd.appendChild(description);
            dd.appendChild(br.cloneNode());
            dd.appendChild(br.cloneNode());
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
                        <dd>
                            <textarea name="${sectionType.name()}" onclick="textAreaAdjust(this)" cols="150"><c:forEach var="content" items="${resume.getSection(sectionType).content}">${content}&#13;&#10;</c:forEach></textarea>
                        </dd>
                        <br>
                    </c:when>
                    <c:when test="${sectionType.name() == 'EXPERIENCE' || sectionType.name() == 'EDUCATION'}">
                      <dd>
                        <c:forEach var="organization" items="${resume.getSection(sectionType).getContent()}">
                            Наименование организации:
                            <input type="text" name="${sectionType}.orgName" size="50" value="${organization.homePage.name}">
                            <br><br>
                            Ссылка на домашнюю страницу организации:
                            <input type="url" name="${sectionType}.orgUrl" size="50" value="${organization.homePage.url}">
                            <br><br>
                            <c:forEach var="exp" items="${organization.experiences}">
                                <input type="month" name="${sectionType}.startDate" value="${exp.startDate}"> &mdash;
                                <input type="month" name="${sectionType}.endDate" value="${exp.endDate}">
                                <br><br>
                                Позиция: <input type="text" name="${sectionType}.position" size="50" value="${exp.title}">
                                <br><br>
                                Описание:<input type="text" name="${sectionType}.description" size="150" value="${exp.description}">
                                <hr>

                            </c:forEach>

                        </c:forEach>
                        </dd>
                        <br><br>
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
