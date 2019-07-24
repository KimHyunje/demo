function logout() {
	window.location.href = '/logout';
}

function searchHistory() {
	$('#searchHistoryTable').bootstrapTable({
		url: '/v1/users/' + userId + '/search-histories',
		columns: [{
			field: 'keyword',
			title: '키워드'
		}, {
			field: 'searchDateTime',
			title: '검색 일시'
		}], 
		responseHandler: function (res) {
			return res.searchHistories
		}
	});
}

$(document).ready(function() {
	$('#logout').click(logout);

	searchHistory();
})