
var fnBtnEditUnidadeFederativa = function(action) {
	$("#formListagemUnidadeFederativa").attr("method", "GET");
	$("#formListagemUnidadeFederativa").attr("action", action);
	$("#formListagemUnidadeFederativa").submit();
}

var fnBtnDeleteUnidadeFederativa = function(action) {
	$("#formListagemUnidadeFederativa").attr("method", "POST");
	$("#formListagemUnidadeFederativa").attr("action", action);
	$("#formListagemUnidadeFederativa").submit();
}

var fnSelecionaPais = function(paisId) {
	$('#paisId').val(paisId);
	$('#modalListagemPaises').modal('hide');
}