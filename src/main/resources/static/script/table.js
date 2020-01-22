$(document).ready(function() {
    fetch('http://localhost:8080/api/tosad/table', {method: 'GET'})
        .then((response) => {
            if (response.ok) {
                return response.json();
            }
        }).then((tables) => {
            let tableHtml = document.getElementById('table_table__body_data');
            let i;
            for (i = 0; i < tables.length; i++) {
                tableHtml.innerHTML += getTrHtml(tables[i].name);
            }
        });
});

function getTrHtml(tableName) {
    let html = '<a href="column.html?table=' + tableName + '" class="list-group-item list-group-item-action">' + tableName + '</a>';

    return html;
}