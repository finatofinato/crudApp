
var fnSelecionaTeste = function(id) {
	$('#campo').val(id);
	$('#myModal').modal('hide');
}

//nao uso
var fnModalListTeste = function(url) {
	
	$('#modalListTeste').load(url).dialog({
        modal: true,
        autoOpen: false,
        width: 400,
        height: 200,
        closeOnEscape: true
        
    });
	$('#modalListTeste').dialog('open');
}

//nao uso
var fnModalBoot = function(url) {
	$('#myModal').modal({
		backdrop: true,
		keyboard: true,
		show: false,
		remote: url
	});
	$('#myModal').modal('show');
}