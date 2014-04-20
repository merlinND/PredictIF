$(document).ready(function(){
	// Magic trick
	fillClientInfosPopover();
	// Initialize tooltip.js
	$('[data-toggle="popover"]').popover({
		'html': true
	});
	// Only show one popover at a time
	$('[data-toggle="popover"]').on('show.bs.popover', function() {
		$('[data-toggle="popover"]').popover('hide');
	});
	
	$('select.choix-horoscope').on('change', function(e){
		showDetailsSelect($(e.target), 'horoscope');
	});
	// Show initial selection
	$('select.choix-horoscope').change();
	
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
	
	// Submit horoscope creation
	$('form#creer-horoscope').on('submit', creerHoroscope);
});

function fillClientInfosPopover() {
	$('.holder.details-client').hide();
	
	$('.holder.details-client').each(function(i, el) {
		var attribute = $(el).attr('id'),
			matches = attribute.match(/client-(\d+)/i);
		if (matches !== null) {
			var id = matches[1];
			var holder = $('a[href="#client-' + id +'"]'),
				contents = $('<div>' + $(el).html() + '</div>');
			holder.attr("data-original-title", contents.find('h3').remove().html());
			holder.attr("data-content", contents.html());
		}
	});
	
}
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

function creerHoroscope(e) {
	e.preventDefault();
	var form = $(e.target);
	
	$.ajax(form.attr('action'), {
		'method': 'post',
		'data': form.serialize(),
		'success': function(html) {
			form.empty().html('<div class="success">L\'horoscope a bien été créé !</div>');
		},
		'error': function(code) {
			alert(code);
		}
	});
	
	return false;
}