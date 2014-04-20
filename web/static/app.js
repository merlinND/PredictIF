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
	// Filter client list on name
	$("#filtre-client").on('keyup', function(e) {
		filterSet($(".liste-clients li"), "nom", $(this).val());
	});
	// Select order
	$("#ordre-client").on('change', function(e) {
		var container = $(".liste-clients:first");
		orderSetBy(container.find("li"), $(this).val(), container);
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
	
	// Add/remove a medium from selection
	$(".choisir-medium").on('click', addMedium);
	$(".retirer-medium").hide().on('click', removeMedium);
});

/**
 * Fuzzy match in set on field corresponding to value
 * @param set The set on which to search
 * @param {string} field The class of the element containing the value on which to match
 * @param {string} value The desired value
 * @returns void
 */
function filterSet(set, field, value) {
	if (value.length <= 0) {
		set.show();
		return;
	}
	
	value = value.toLowerCase();
	set.each(function(i, el) {
		var val = $(el).find('.' + field).text().toLowerCase();
		if (val.indexOf(value) > -1)
			$(el).show();
		else
			$(el).hide();
	});
}

function orderSetBy(set, field, container) {
	set.detach();
	set.sort(function(a, b) {
		var val1 = $(a).find('.' + field).text().trim().toLowerCase(),
			val2 = $(b).find('.' + field).text().trim().toLowerCase();
		if (!isNaN(val1) && !isNaN(val2))
			return (parseInt(val1) - parseInt(val2));
		else
			return val1.localeCompare(val2);
	}).each(function(i, el) {
		container.append(el);
	});
}

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

function getOption(e) {
	var clicked = $(e.target),
		attribute = clicked.parents('.details-medium').attr('id'),
		matches = attribute.match(/medium-(\d+)/i);

	if (matches !== null) {
		var id = matches[1];
		return $('#mediums-choisis').find('option[value="' + id + '"]');
	}
	return null;
}
function addMedium(e) {
	getOption(e).prop('selected', true);
	$(e.target).hide().siblings('.retirer-medium').show();
}
function removeMedium(e) {
	getOption(e).prop('selected', false);
	$(e.target).hide().siblings('.choisir-medium').show();
}