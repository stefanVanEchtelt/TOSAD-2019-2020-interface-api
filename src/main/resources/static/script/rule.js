const queryString = window.location.search;
const urlParams = new URLSearchParams(queryString);

initForm();

if(urlParams.get('rule') != null){
    fillForm(urlParams.get('rule'));
}

$(document).ready(function() {
    var tableName = urlParams.get('table');
    var columnName = urlParams.get('column');

    $("#form_table").val(tableName);
    $("#form_column").val(columnName);
    $("#head_name").html(columnName);
    $('input[name="rule_name"]').val(tableName + '_' + columnName);

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
        $('input[name="rule_name"]').val(tableName + '_' + columnName  + '_' + $("#form_rule").val());
        if($("#form_rule").val() == "TCMP"){
            getColumnsByTable(tableName, $("#column1"));
            getColumnsByTable(tableName, $("#column2"));    
        }        
    });

    $("select[name='comparison_operator']").change(function(){
        $('input[name="rule_name"]').val(tableName + '_' + columnName  + '_' + $("#form_rule").val() + '_' + $("select[name='comparison_operator']").val());
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

var rule = $("#form_rule");
$("#form_save").click(sendRule);

rule.change(function(){
    initForm();

    switch(rule.val()){
        case "ARNG":
            $("#form_group_value").show();
            $("#form_group_value_extra").show();
            $("#form_group_operator_arng").show();

            $("#form_value").prop('required',true);
            $("#form_value_extra").prop('required',true);
            $("#comparison_operator").prop('required',true);            
            break;
        case "ACMP":
            $("#form_group_operator").show();
            $("#form_group_value").show();
            $("#form_group_operator_acmp").show();
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
    $("#form_group_operator_acmp").hide();
    $("#form_group_table").hide();
    $("#form_group_column").hide();
    $("#form_group_column2").hide();    
    $("#form_group_compare").hide();
    $("#form_group_value").hide();
    $("#form_group_value_extra").hide();    
    $("#form_group_list").hide();

    $("#form_value").prop('required',false);
    $("#form_value_extra").prop('required',false);
    $("#comparison_operator").prop('required',false); 
}

function sendRule(){
    let formData = new FormData(document.querySelector("#generate_rule"));
    let encData = new URLSearchParams(formData.entries());
    var url = "";
    if(urlParams.get('rule') != null){
        // fetch("http://localhost:8080/api/tosad/businessRule/businessRule" + urlParams.get('rule'),  {method: 'DELETE', body: encData})
        // .then((response) => {
        //     if (response.ok) {
        //         console.log(response.json());
        //     }
        //     else {
        //         console.log(response.json());
        //         alert(response.json());
        //     }
        // })
        // .then((myJson) => {
        // });
    }
    // fetch("http://localhost:8080/api/tosad/businessRule/businessRule", {method: 'POST', body: encData})
    //     .then((response) => {
    //         if (response.ok) {
    //             console.log(response.json());
    //             window.location.replace("table.html");
    //         }
    //         else {
    //             console.log(response.json());
    //             alert(response.json());
    //         }
    //     })
    //     .then((myJson) => {
    //     });
}

function fillForm(id){
    console.log("filling...");
    fetch("http://localhost:8080/api/tosad/businessRule/businessRule/businessRules/id/" + id, {method: 'GET'})
        .then((response) => {
            if (response.ok) {
                return response.json();
            }
        }).then((rule) => {
            console.log(rule.rule);
            $("#trigger_insert").val();
            $("#trigger_update").val();
            $("#trigger_delete").val();
            $("#form_value").val(rule.value1);
            $("#form_value_extra").val(rule.value2);
            $("#form_rule").val(rule.rule);
            $("#relational_operator").val(rule.relational_operator);
            $("#comparison_operator").val(rule.comparison_operator);
            $("#list_operator").val();
            $("#column1").val(rule.column1);
            $("#column2").val(rule.column2);            
            $("#table").val(rule.table);
            $("#error").val(rule.message);
            $("#error_code").val(rule.code);
        });
}

