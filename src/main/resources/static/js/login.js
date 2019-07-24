function alertModal(text) {
	$('#alertModal .modal-body').html(text);
	$('#alertModal').modal('toggle');
}

function login() {
	$.ajax({
		type: 'POST', 
		url: '/login', 
		contentType: 'application/x-www-form-urlencoded',
		dataType: 'html',
		data: $('#loginForm').serialize()
	}).done(function(data) {
		window.location.href = '/book-search';
	}).fail(function(data) {
		alertModal("아이디/비밀번호가 일치하지 않습니다.");
	});
}

function register() {
	var registerData = {
		id: $('#registerId').val(),
		password: $('#registerPassword').val()
	};

	$.ajax({
		type: 'POST', 
		url: '/v1/users', 
		contentType: 'application/json', 
		data: JSON.stringify(registerData)
	}).done(function(data) {
		$('#userRegisterModal').modal('toggle');
		alertModal("회원가입이 완료되었습니다. 입력하신 아이디/비밀번호로 로그인해 주세요.");
	}).fail(function(data) {
		alertModal("회원가입이 실패하였습니다.");
	});
}

$(document).ready(function() {
	$('#login').click(login);
	$('#register').click(register);
})