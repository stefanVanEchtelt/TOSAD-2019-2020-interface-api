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
        $('input[name="rule_name"]').val($('input[name="rule_name"]').val().replace("!", "NOT"));
    });

    $("select[name='list_operator']").change(function(){
        $('input[name="rule_name"]').val(tableName + '_' + columnName  + '_' + $("#form_rule").val() + '_' + $("select[name='comparison_operator']").val());
        $('input[name="rule_name"]').val($('input[name="rule_name"]').val().replace("!", "NOT"));    
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
            $("#value1").prop('type', 'number');
            $("#value2").prop('type', 'number');
            $("#value1").prop('required',true);
            $("#value2").prop('required',true);
            $("#comparison_operator").prop('required',true);            
            break;
        case "ACMP":
            $("#form_group_operator").show();
            $("#form_group_value").show();
            $("#value1").prop('type', 'text');                        
            $("#relational_operator").prop('required',true);                                      
            $("#value1").prop('required',true);
            break;
        case "ALIS":
            $("#form_group_list").show();
            $("#form_group_operator_alis").show();            
            $("#list_operator").prop('required',true);      
            $("#list").prop('required',true);            
            break;
        case "TCMP":
            $("#form_group_column").show();
            $("#form_group_column2").show();    
            $("#form_group_operator").show();
            $("#relational_operator").prop('required',true);                             
            break;
        case "ICMP":
            $("#form_group_table").show();
            $("#form_group_column2").show();        
            $("#form_group_operator").show(); 
            $("#relational_operator").prop('required',true);                                      
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
    $("#form_group_operator_alis").hide();            

    $("#value1").removeAttr('required');
    $("#value2").removeAttr('required');
    $("#comparison_operator").removeAttr('required'); 
    $("#list").removeAttr('required');   
    $("#relational_operator").removeAttr('required');            
    $("#list_operator").removeAttr('required');            
         

}

function sendRule(){
    if(validateForm() == false){
        openModal("Fill in the red boxes", "close");
        return;
    }

    let formData = new FormData(document.querySelector("#generate_rule"));
    let encData = new URLSearchParams(formData.entries());
    var url = "";
    if(urlParams.get('rule') != null){
        fetch("http://localhost:8080/api/tosad/businessRule/businessRule" + urlParams.get('rule'),  {method: 'DELETE'})
        .then((response) => {
            if (response.ok) {
            }
            else {
            }
        })
        .then((myJson) => {
        });
    }
    fetch("http://localhost:8080/api/tosad/businessRule/businessRule", {method: 'POST', body: encData})
        .then((response) => {
            if (response.ok) {
                openModal("Business Rule added", "column.html?table=" + urlParams.get('table'));
            }
            else {
                openModal("Error, Business Rule not added", "close");
            }
        })
        .then((myJson) => {
        });
}

function fillForm(id){
    fetch("http://localhost:8080/api/tosad/businessRule/businessRule/businessRules/id/" + id, {method: 'GET'})
        .then((response) => {
            if (response.ok) {
                return response.json();
            }
        }).then((rule) => {
            $("#trigger_insert").val();
            $("#trigger_update").val();
            $("#trigger_delete").val();
            $("#value1").val(rule.value1);
            $("#value2").val(rule.value2);
            $("#list").val(rule.form_list);
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


function validateForm(){
    var filled = true;
    $('#generate_rule').find(':input').each(function() {
        var hasRequired = $(this).attr('required');
        if ($(this).val() == "" && hasRequired !== false && typeof hasRequired !== "undefined") {
            $(this).css('border-color', 'red');
            filled = false;
        }
    });
    return filled;
}

