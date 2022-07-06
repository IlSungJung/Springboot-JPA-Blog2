<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file="../layout/header.jsp"%>

<div class="container">
	<form action="/auth/loginProc" method="POST">
		<div class="form-group">
			<label for="username">username:</label> <input type="text" name="username" class="form-control" placeholder="Enter username" id="username">
		</div>
		<div class="form-group">
			<label for="password">password:</label> <input type="password" name="password" class="form-control" placeholder="Enter password" id="password">
		</div>
	<button id="btn-login" class="btn btn-primary">로그인</button>
	<a href="https://kauth.kakao.com/oauth/authorize?client_id=461c5bdfe5d43a5d5748fe7436e44ffc&redirect_uri=http://localhost:8000/auth/kakao/callback&response_type=code"><image height="38px" src="/image/kakao_login.png"></a>
	</form>

</div>

<%@ include file="../layout/footer.jsp"%>
