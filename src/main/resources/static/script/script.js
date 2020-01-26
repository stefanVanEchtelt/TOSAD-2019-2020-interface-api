getBusinessRules();

function getBusinessRules() {
    const queryString = window.location.search;
    const urlParams = new URLSearchParams(queryString);
    let column_name = urlParams.get('column');
    fetch('http://localhost:8080/api/tosad/businessRule/businessRule/businessRules/column/'  + column_name,  {method: 'GET'})
        .then((response) => {
            if (response.ok) {
                return response.json();
            } else {
                console.log("not ok");
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
                console.log('fetch failed');
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
            console.log(btn);
            html += btn;

        } else if (run === "noRun") {
            html += "<a onclick=handleRun("+ ruleId+ ") id=" + ruleId + " class=\"btn btn-info mr-3\" style=\"\">Run</a>"
        }
        html +=
        "               <a class=\"btn btn-info mr-3\" href=\"#.html\">Edit</a><a class=\"btn btn-info\" href=\"table.html\">Terug</a>\n" +
        "              </p>" +
        "               <a onclick=handleRun("+ ruleId+ ") id=" + ruleId + " class=\"btn btn-info mr-3\" style=\"\">Run</a><a class=\"btn btn-info mr-3\" href=\"#.html\">Edit</a><a class=\"btn btn-info mr-3\" href=\"#.html\">Delete</a><a class=\"btn btn-info\" href=\"table.html\">Terug</a>\n" +
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
                console.log('fetch failed');
            }
        })
        .then((codeJson) => {
            openModal("Business Rule executed succesfully", "table.html")
        })
}

