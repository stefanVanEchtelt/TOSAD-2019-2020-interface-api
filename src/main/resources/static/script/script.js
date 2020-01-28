const queryString = window.location.search;
const urlParams = new URLSearchParams(queryString);
var column_name = urlParams.get('column');
var table_name =  urlParams.get('table');
getBusinessRules();

function getBusinessRules() {
    fetch('http://localhost:8080/api/tosad/businessRule/businessRule/businessRules/column/'  + column_name,  {method: 'GET'})
        .then((response) => {
            if (response.ok) {
                return response.json();
            } else {
            }
        })
        .then((ruleJson) => {
            let index;
            for (index = 0; index < ruleJson.length; index++){
                if (ruleJson[index].isExecuted === 1) {
                    let code = fetchCode(ruleJson[index].id, 'Run');
                    } else {
                    let code = fetchCode(ruleJson[index].id, 'noRun');
                }
            }
        })

}

function fetchCode(ruleId, runInfo) {
    let py5 = document.getElementById('py-5-id');
    let code;
    fetch('http://localhost:8080/api/tosad/businessRule/rules/businessRules/example/'+ruleId, {method: 'GET'})
        .then((response) => {
            if(response.ok) {
                return response.json();
            } else {
            }
        })
        .then((codeJson) => {
            code = codeJson;
            py5.innerHTML += getCardHtml(code, ruleId, runInfo);

        })
}


function getCardHtml(ruleName, ruleId, run) {
    let html =
        "<div class=\"container\">\n" +
        "      <div class=\"row\">\n" +
        "        <div class=\"col-md-12\">\n" +
        "          <div class=\"card\">\n" +
        "            <div class=\"card-body\">\n" +
        "              <p class=\"card-text\">" +
        "                   <pre>    "+
                            ruleName +
        "                   </pre>" +
        "              </p>";
        if (run === "Run") {
            let message = "businessrule has already been executed";
            let redirect = "close";
            let btn = '';
            btn += '<a ';
            btn += 'onclick="openModal(\'' + message + '\', \'' + redirect + '\') "';
            btn += 'id="' + ruleId + '" class=\"btn btn-info mr-3\" style=\"\">Executed</a>';
            html += btn;

        } else if (run === "noRun") {
            html += "<a onclick=handleRun("+ ruleId+ ") id=" + ruleId + " class=\"btn btn-info mr-3\" style=\"\">Run</a>"
        }
        html +=
        "               <a class=\"btn btn-info mr-3\" href=rule.html?column=" + column_name + "&table=" + table_name + "&rule=" + ruleId + "\>Edit</a><a class=\"btn btn-info mr-3\" onclick=deleteRule(" + ruleId + ")>Delete</a><a class=\"btn btn-info\" href=\"table.html\">Terug</a>\n" +
        "              </p>" +
        "            </div>\n" +
        "          </div>\n" +
        "        </div>\n" +
        "      </div>\n" +
        "    </div>";

    return html;
}

function handleRun(ruleId){
    fetch('http://localhost:8080/api/tosad/businessRule/rules/businessRules/execute/'+ruleId, {method: 'POST'})
        .then((response) => {
            if(response.ok) {
                return response.json();
            } else {
            }
        })
        .then((codeJson) => {
            openModal("Business Rule executed succesfully", "table.html")
        })
}

function deleteRule(ruleId) {
    fetch('/api/tosad/businessRule/businessRule/' + ruleId, {method: 'DELETE'})
        .then((response) => {
            if (response.ok){
                openModal("BusinessRule has been deleted", "reload")
            } else {
                openModal("There has been an error while deleting this businessrule", "reload");
            }
        })
}

