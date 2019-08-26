<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
	integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T"
	crossorigin="anonymous">

<title>Login Page</title>
</head>
<body>
	<div class="container">
		<h1>Login</h1>
		<p class="text-danger">
			<c:out value="${error}" />
		</p>
		<form method="post" action="/login">
			<p>
				<label for="email">Email</label> 
				<input class="form-control col-4" type="text" id="email" name="email" />
			</p>
			<p>
				<label for="password">Password</label> 
				<input class="form-control col-4" type="password" id="password" name="password" />
			</p>
			<input type="submit" value="Login!" class="btn btn-primary"/>
		</form>
	</div>
</body>
</html>