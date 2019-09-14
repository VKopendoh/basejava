
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
