
var fnBtnEditIcms = function(action) {
	$("#formListagemIcms").attr("method", "GET");
	$("#formListagemIcms").attr("action", action);
	$("#formListagemIcms").submit();
}

var fnBtnDeleteIcms = function(action) {
	$("#formListagemIcms").attr("method", "POST");
	$("#formListagemIcms").attr("action", action);
	$("#formListagemIcms").submit();
}

var fnSelecionaUnidadeFederativaOrigem = function(ufId) {
	$('#ufOrigemId').val(ufId);
	$('#modalListagemUnidadesFederativasOrigem').modal('hide');
}

var fnSelecionaUnidadeFederativaDestino = function(ufId) {
	$('#ufDestinoId').val(ufId);
	$('#modalListagemUnidadesFederativasDestino').modal('hide');
}

var fnSelecionaPais = function(paisId) {
	$('#paisId').val(paisId);
	$('#modalListagemPaises').modal('hide');
}