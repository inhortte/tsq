var quote_html = "";

function new_quote() {
    $("#content").html(quote_html);
    $("#content").fadeIn(500);
}

function ajax_hovno() {
    $('a[id^="ca"]').click(function() {
	var author_id = this.id.substr(2);
	$.ajax({
	    url: "/clear-author",
	    cache: false,
	    success: function(html) {
		$("#menu").html(html);
		$.get("/current-quote", function(html) {
		    quote_html = html;
		    $("#content").fadeOut(500, new_quote);
		});
	    }
	});
	return false;
    });
    $('a[id^="au"]').click(function() {
	var author_id = this.id.substr(2);
	$.ajax({
	    url: "/set-author/" + author_id,
	    cache: false,
	    success: function(html) {
		$("#menu").html(html);
		$.get("/current-quote", function(html) {
		    quote_html = html;
		    $("#content").fadeOut(500, new_quote);
		});
	    }
	});
	return false;
    });
    $('a[id="random"]').click(function() {
	$.ajax({
	    url: "/quote/random-quote",
	    cache: false,
	    success: function(html) {
		quote_html = html;
		$("#content").fadeOut(500, new_quote);
		$.get("/refresh-menu", function(html) {
		    $("#menu").html(html);
		});
	    }
	});
    });
    $('a[id="next"]').click(function() {
	$.ajax({
	    url: "/quote/next-quote",
	    cache: false,
	    success: function(html) {
		quote_html = html;
		$("#content").fadeOut(500, new_quote);
		$.get("/refresh-menu", function(html) {
		    $("#menu").html(html);
		});
	    }
	});
    });
    $('a[id="previous"]').click(function() {
	$.ajax({
	    url: "/quote/previous-quote",
	    cache: false,
	    success: function(html) {
		quote_html = html;
		$("#content").fadeOut(500, new_quote);
		$.get("/refresh-menu", function(html) {
		    $("#menu").html(html);
		});
	    }
	});
    });
}

$(document).ready(function() {
    ajax_hovno();
});

