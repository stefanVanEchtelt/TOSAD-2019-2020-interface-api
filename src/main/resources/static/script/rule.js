const queryString = window.location.search;
const urlParams = new URLSearchParams(queryString);

$(document).ready(function() {
    var tableName = urlParams.get('table');
    var columnName = urlParams.get('column');

    $("#form_table").val(tableName);
    $("#form_column").val(columnName);
    $("#head_name").html(columnName);

    fetch('http://localhost:8080/api/tosad/table/' + tableName, {method: 'GET'})
    .then((response) => {
        if (response.ok) {
            return response.json();
        }
    }).then((tables) => {
        let table = $("#table");
        let i;
        for (i = 0; i < tables.length; i++) {
            table.append($("<option value=" + tables[i].name + ">" + tables[i].name + "</option>"));            
        }
    });

    $("#table").change(function(){
        getColumnsByTable($("#table").val(), $("#column2"));
    });

    $("#form_rule").change(function(){
        if($("#form_rule").val() == "TCMP"){
            getColumnsByTable(tableName, $("#column1"));
            getColumnsByTable(tableName, $("#column2"));    
        }        
    });
});

function getColumnsByTable(table, column){
    fetch('http://localhost:8080/api/tosad/column/' + table, {method: 'GET'})
        .then((response) => {
            if (response.ok) {
                return response.json();
            }
        }).then((columns) => {
        column.empty();
        let i;
        for (i = 0; i < columns.length; i++) {
            column.append($("<option value=" + columns[i].name + ">" + columns[i].name + "</option>"));
        }
    });
}

initForm();

var rule = $("#form_rule");
$("#form_save").click(sendRule);

rule.change(function(){
    initForm();

    switch(rule.val()){
        case "ARNG":
            $("#form_group_value").show();
            $("#form_group_value_extra").show();
            $("#form_group_operator_arng").show();
            break;
        case "ACMP":
            $("#form_group_operator").show();
            $("#form_group_value").show();
            break;
        case "ALIS":
            $("#form_group_list").show();
            break;
        case "TCMP":
            $("#form_group_column").show();
            $("#form_group_column2").show();    
            $("#form_group_operator").show();                   
            break;
        case "ICMP":
            $("#form_group_table").show();
            $("#form_group_column2").show();        
            $("#form_group_operator").show();               
    }
});

function initForm(){
    $("#form_group_operator").hide();
    $("#form_group_operator_arng").hide();
    $("#form_group_table").hide();
    $("#form_group_column").hide();
    $("#form_group_column2").hide();    
    $("#form_group_compare").hide();
    $("#form_group_value").hide();
    $("#form_group_value_extra").hide();    
    $("#form_group_list").hide();
}

function sendRule(){
    let formData = new FormData(document.querySelector("#generate_rule"));
    let encData = new URLSearchParams(formData.entries());

    for (var pair of formData.entries()) {
    console.log(pair[0]+ ', ' + pair[1]); 
    }
    
   fetch('http://localhost:8080/api/tosad/businessRule/businessRule', {method: 'POST', body: encData})
    .then((response) => { 
        if (response.ok) {
            console.log('succes 1');
            console.log(response.json());
            console.log('succes 2');
        } 
        else {
            console.log(response.json());
        }
    })
    .then((myJson) => {
    });  
}

