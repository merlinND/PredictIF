$(document).ready(function(){
	$('a[href^="#client-"').on('click', showClientInfos);
	$('a[href^="#client-"').click();
	
	$('select.choix-prediction').on('change', function(e){
		showDetailsSelect($(e.target), 'prediction');
	});
	// Show initial selection
	$('select.choix-prediction').change();
	
	$('select.choix-medium').on('change', function(e){
		showDetailsSelect($(e.target), 'medium');
	});
	// Show initial selection
	$('select.choix-medium').change();
});

function showClientInfos(e) {
	e.preventDefault();
	$('.details-client').hide();
	
	var attribute = $(e.currentTarget).attr('href'),
		matches = attribute.match(/client-(\d+)/i);
	if (matches !== null) {
		var id = matches[1];
		$('#client-' + id).show();
	}
	
	return false;
}

function showDetailsSelect(select, type) {
	select.siblings('.details-' + type).hide();
	var option = select.find('option:selected').first(),
		id = option.attr('value');
	$('#'+ type + '-' + id).show();
}