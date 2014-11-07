<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta charset="ISO-8859-1">
		<title>Register Here</title>
		<link href="css/bootstrap.min.css" rel="stylesheet">
		
		<style type="text/css">
				.form-div {
				width: 400px;
				margin: 0 auto;
				}
			body {
			background-image:url('css/background.jpg');
			}

		</style>
	</head>
	<body>
	
	<nav class="navbar navbar-default" role="navigation">
			<a class="navbar-brand" href="#">Register Here</a>
	</nav>
	
	<div id="credentials_error" class="error">
			<% 
			   String message = (String) session.getAttribute("message");
			   session.removeAttribute("message");
			
			 if (message != null) {  //Close if bracket
			%>
			 <%= message  %>
			<% }%>
			
		</div>
	
		<form name="registration" onsubmit="return validateForm()" action="register" method="POST">
		<div class="form-div">
			<div class="form-group" >
				<label>Enter your user name:</label>
				<input class="form-control" name="username" type="text">
				<label>Name:</label>
				<input class="form-control" name="name" type="text">
				<label>Surname:</label>
				<input class="form-control" name="surname" type="text">
				<label>Password:</label>
				<input class="form-control" name="password" type="password">
				<label>Group:</label>
				<input class="form-control" name="tenant" type="text">
				<label>Email:</label>
				<input class="form-control" name="email" type="text">
			</div>
			<button type="submit" class="btn btn-primary">Register</button>
		</div>
				
		</form>
		
		
	
	</body>
</html>
</html>