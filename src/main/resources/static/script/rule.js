const queryString = window.location.search;
const urlParams = new URLSearchParams(queryString);

$(document).ready(function() {
    let tableName = urlParams.get('table');
    let columnName = urlParams.get('column');

    //$("#form_table").val(tableName);
    //$("#form_column").val(columName);
    //$("#head_name").innerHTML(columnName);

    fetch('http://localhost:8080/api/tosad/table', {method: 'GET'})
    .then((response) => {
        if (response.ok) {
            return response.json();
        }
    }).then((tables) => {
        let table1 = $("#table1");
        let table2 = $("#table2");
        let i;
        for (i = 0; i < tables.length; i++) {
            table1.append($("<option value=" + tables[i].name + ">" + tables[i].name + "</option>"));
            table2.append($("<option value=" + tables[i].name + ">" + tables[i].name + "</option>"));            
        }
    });

    $("#table1").change(function(){
        getColumnsByTable($("#table1").val());
        console.log("changed");
    });
});

function getColumnsByTable(table){
    fetch('http://localhost:8080/api/tosad/column/' + table, {method: 'GET'})
        .then((response) => {
            if (response.ok) {
                return response.json();
            }
        }).then((columns) => {
        console.log(columns);    
        let column1 = $("#column1");
        let column2 = $("#column2");   
        column1.empty();
        let i;
        for (i = 0; i < columns.length; i++) {
            column1.append($("<option value=" + columns[i].name + ">" + columns[i].name + "</option>"));
            column2.append($("<option value=" + columns[i].name + ">" + columns[i].name + "</option>"));  
        }
    });
}