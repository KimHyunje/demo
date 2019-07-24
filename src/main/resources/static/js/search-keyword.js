function logout() {
	window.location.href = '/logout';
}

function searchKeyword() {
	$('#searchKeywordTable').bootstrapTable({
		url: '/v1/search-keywords?rank=10',
		columns: [{
			field: 'keyword',
			title: '키워드'
		}, {
			field: 'searchCount',
			title: '검색'
		}], 
		responseHandler: function (res) {
			return res.searchKeywords
		}
	});
}

$(document).ready(function() {
	$('#logout').click(logout);

	searchKeyword();
})