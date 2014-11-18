<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<link href="css/bootstrap.min.css" rel="stylesheet">
	<link href="css/all_pages.css" rel="stylesheet">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Welcome</title>

</head>
<body>


<nav class="navbar navbar-default" role="navigation">
			<div id="credentials_error" class="error">
			<% 
			   String message = (String) session.getAttribute("welcome_msg");
			
			 if (message != null) {  
			%>
			 <%= message  %>
			<% }%>
		
</div>
	
	<div style="float:right">
		  <form align="right" name="form1" method="POST" action="logout">
			  <label class="logoutLblPos">
			  <input name="logout" type="submit"  value="Log out">
			  </label>
		</form>
	</div>
	
</nav>
	
			<div class="user-form">
				<form name="userPage" id="userPage" action="filltable" method="POST">
					<label>Enter custumer ID:</label>
					<input class="form-control" type="text" name="customer"> <br>
					<button id="search" type="submit" class="btn btn-default">Search for transactions</button>
				</form>
			</div>


		<div id="data-table" class="table">
			
			<!-- Table where customer transactions will be displayed -->
			
			
			
		</div>
		
		<div class="user-form">
			<label>Select method of analysis:</label>
			<select name="alanysis-methods" class="form-control" >
		    			<option value="Self Organizing Map">Self Organizing Map</option>
		   				<option value="Naive Bayes">Naive Bayes</option>
			</select>
		
		</div>
		

</body>
</html>