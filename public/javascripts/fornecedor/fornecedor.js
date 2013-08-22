
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
    $("#dataCadastro").datepicker({ dateFormat: "dd/mm/yy", monthNames: [ "Janeiro", "Fevereiro", "Mar�o", "Abril", "Maio", "Junho", "Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro" ], dayNamesMin: [ "Dom", "Seg", "Ter", "Qua", "Qui", "Sex", "Sab" ], dayNames: [ "Domingo", "Segunda", "Ter�a", "Quarta", "Quinta", "Sexta", "S�bado" ] });
});


var fnSelecionaUf = function(uf) {
	$('#uf').val(uf);
	$('#modalListUf').modal('hide');
}