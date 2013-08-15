
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
