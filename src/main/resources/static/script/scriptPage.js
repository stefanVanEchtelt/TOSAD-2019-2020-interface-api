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
                // if (ruleJson[index].isExecuted === 1) {
                //     } else {
                    let code = fetchCode(ruleJson[index].id);
                // }
            }
            $("#"+3).click(function(){handleRun("ooooi")});

            console.log("then myJson");

        })

}

function fetchCode(ruleId) {
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
            py5.innerHTML += getCardHtml(code, ruleId);

        })
}


function getCardHtml(ruleName, ruleId) {
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
        "              </p>" +
        "               <a onclick=handleRun("+ ruleId+ ") id=" + ruleId + " class=\"btn btn-info mr-3\" style=\"\">Run</a><a class=\"btn btn-info mr-3\" href=\"table.html\">Terug</a><a class=\"btn btn-info\" href=\"#.html\">Edit</a>\n" +
        "            </div>\n" +
        "          </div>\n" +
        "        </div>\n" +
        "      </div>\n" +
        "    </div>";

    return html;
}

function handleRun(ruleId){
    console.log(ruleId);
    fetch('http://localhost:8080/api/tosad/businessRule/rules/businessRules/execute/'+ruleId, {method: 'POST'})
        .then((response) => {
            if(response.ok) {
                return response.json();
            } else {
                console.log('fetch failed');
            }
        })
        .then((codeJson) => {
            console.log(codeJson);

        })
}