<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta charset="ISO-8859-1">
		<title>Log in</title>
		<link href="css/bootstrap.min.css" rel="stylesheet">
		<script src="js/jquery-1.11.1.js"></script>
		<script src="http://code.jquery.com/jquery-latest.min.js"></script>
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
			<a class="navbar-brand" href="#">AML Application</a>
		</nav>
		
		<div id="credentials_error" class="error">
			<% String username = (String) session.getAttribute("credentials");
				session.removeAttribute("credentials");
			   String message = (String) session.getAttribute("message");
			   session.removeAttribute("message");
			if(username != null) {
			%>
				The user <strong><%= username %></strong> could not be authenticated!.
			<% 	
			} else if (message != null) {  //Close if bracket
			%>
			 <%= message  %>
			<% }%>
			
		</div>
		
			<div class="form-div">
				<form name="loginform"  id="login-form" action="login" method="POST" >
					<div class="form-group">
						<label>User name:</label>
						<input class="form-control" type="text" name="username">
					</div>
					<div class="form-group">
						<label>Password:</label>
						<input class="form-control" type="password" name="password">
					</div>
					<label>Group:</label>
					<select name="Group" class="form-control">
		    			<option value="UCD">UCD</option>
		   				<option value="AIB">AIB</option>
					</select>
					<br>
						
				<button id="push-button" type="submit" class="btn btn-default">Login</button>
				<a class="btn btn-primary" href="registration.jsp">Register</a>
				</form>
			</div>
	</body>
</html>