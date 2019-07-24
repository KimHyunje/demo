function logout() {
	window.location.href = '/logout';
}

var books;
var prevLink;
var nextLink;

function search() {
	var searchUrl;
	if (this == $('#search')[0]) {
		var keyword = $('#keyword').val();
		if (!keyword) return;

		searchUrl = '/v1/books?keyword=' + keyword + '&page=0&size=10';
	} else if (this == $('#prev')[0]) {
		searchUrl = prevLink;
	} else if (this == $('#next')[0]) {
		searchUrl = nextLink;
	} else {
		return;
	}

	$.ajax({
		type: 'GET', 
		url: searchUrl
	}).done(function(data) {
		books = data.books;

		renderTable();

		var links = data._links;
		if (links.prev) {
			$('#prev').attr('disabled', false);
			prevLink = links.prev.href;
		} else {
			$('#prev').attr('disabled', true);
			prevLink = null;
		}
		if (links.next) {
			$('#next').attr('disabled', false);
			nextLink = links.next.href;
		} else {
			$('#next').attr('disabled', true);
			nextLink = null;
		}
	});
}

function renderTable(data) {
	$('#bookSearchTable').bootstrapTable('destroy');
	$('#bookSearchTable').bootstrapTable({
		data: books,
		columns: [{
			field: 'title',
			header: '제목',
			formatter: linkFormatter
		}, {
			field: 'authors',
			header: '저자'
		}, {
			field: 'price',
			header: '정가'
		}]
	});
}

function linkFormatter(value, row, index) {
	var isbn = "'" + row.isbn + "'";
	return '<a href="javascript:viewDetailModal(' + isbn + ')">' + row.title + '</a>';
}

function viewDetailModal(isbn) {
	$.each(books, function (key, value) {
		if (value.isbn == isbn) {
			$('#bookTitle').html('<p>' + value.title + '</p>');
			$('#bookThumbnail').html('<img src="' + value.thumbnail + '">');
			$('#bookContents').html('<p>' + value.contents + '</p>');
			$('#bookIsbn').html('<p>' + value.isbn + '</p>');
			$('#bookAuthors').html('<p>' + value.authors + '</p>');
			$('#bookPublisher').html('<p>' + value.publisher + '</p>');
			$('#bookDatetime').html('<p>' + value.datetime + '</p>');
			$('#bookPrice').html('<p>' + value.price + '</p>');
			$('#bookSalePrice').html('<p>' + value.salePrice + '</p>');
		}
	});
	$('#bookDetailModal').modal('toggle');
}

$(document).ready(function() {
	$('#logout').click(logout);
	$('#search').click(search);
	$('#prev').click(search);
	$('#next').click(search);
});
