
var fnBtnEditFornecedor = function(action) {
	$("#formListFornecedor").attr("method", "GET");
	$("#formListFornecedor").attr("action", action);
	$("#formListFornecedor").submit();
}

var fnBtnDeleteFornecedor = function(action) {
	$("#formListFornecedor").attr("method", "POST");
	$("#formListFornecedor").attr("action", action);
	$("#formListFornecedor").submit();
}


$(function() {
    $("#dataCadastro").datepicker({ dateFormat: "dd/mm/yy", monthNames: [ "Janeiro", "Fevereiro", "Março", "Abril", "Maio", "Junho", "Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro" ], dayNamesMin: [ "Dom", "Seg", "Ter", "Qua", "Qui", "Sex", "Sab" ], dayNames: [ "Domingo", "Segunda", "Terça", "Quarta", "Quinta", "Sexta", "Sábado" ] });
});


var fnSelecionaUf = function(uf) {
	$('#uf').val(uf);
	$('#modalListUf').modal('hide');
}