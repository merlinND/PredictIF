$(document).ready(function(){
	$('.details-client').hide();
	$('a[href^="#client-"').on('click', showClientInfos);
});

function showClientInfos(e) {
	$('.details-client').hide();
	
	var attribute = $(e.currentTarget).attr('href'),
		matches = attribute.match(/client-(\d+)/i);
	if (matches !== null) {
		var id = matches[1];
		$('#client-' + id).show();
	}
}