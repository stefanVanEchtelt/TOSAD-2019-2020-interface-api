const queryString = window.location.search;
const urlParams = new URLSearchParams(queryString);


$(document).ready(function() {
    let tableName = urlParams.get('table');
    document.getElementById('table_name').innerHTML = tableName;

    fetch('http://localhost:8080/api/tosad/column/' + tableName, {method: 'GET'})
        .then((response) => {
            if (response.ok) {
                return response.json();
            }
        }).then((tables) => {
        let columnHtml = document.getElementById('column_table__body_data');
        let i;
        for (i = 0; i < tables.length; i++) {
            columnHtml.innerHTML += getTrHtml(tables[i].name);
        }
    });
});

function getTrHtml(columnName) {
    let tableName = urlParams.get('table');
    let html = "<tr>";
    html += '<th class="table-light">' + columnName + '</th>';
    html += '<td class="table-light text-center"><a class="btn btn-info" href="script.html?column=' + columnName + '">Show rules</a></td>';
    html += '<td class="table-light text-center"><a class="btn btn-info" href="rule.html?column=' + columnName + '&table=' + tableName + '">New rule</a></td>';
    html += '</tr>';

    return html;
}