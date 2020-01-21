$(document).ready(function() {
    console.log(getTrHtml());
    let fetchOptions  = {
        method: 'GET',
    };

    fetch('http://localhost:8080/api/tosad/table', fetchOptions)
        .then((response) => {
            if (response.ok) {
                console.log(response.json());
            }
        });
});

function getTrHtml(tableName) {
    let html = "<tr>";
    html += '<th class="table-light">' + tableName + '</th>';
    html += '<td class="table-light text-center"><a class="btn btn-info" href="script.html?table=' + tableName + '">Show rules</a></td>';
    html += '<td class="table-light text-center"><a class="btn btn-info" href="rule.html?table=' + tableName + '">New rule</a></td>';
    html += '</tr>';

    return html;
}