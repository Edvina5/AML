<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<html>
<head>
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<link href="css/bootstrap.min.css" rel="stylesheet">
	<link href="css/all_pages.css" rel="stylesheet">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Welcome</title>

</head>
<body>


<nav class="navbar navbar-default" role="navigation">
			<div id="cid-error" >
			<% 
			   String message = (String) session.getAttribute("welcome_msg");
			 if (message != null) {  
			%>
			 <%= message  %>
			<% }%>
</div>
	
	<div style="float:right; padding: 5px; margin-top: -20px">
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
					<input class="form-control" type="text" name="customer"> 
						<div id="cid-error">
							<% 
						  	 String error = (String) session.getAttribute("error");
						 	  session.removeAttribute("error");
							if(error != null) {
							%>
								<p><%= error %></p> 
							<% 	
							}
							%>
						</div>
						
					<button id="search" type="submit" class="btn btn-default">Search for transactions</button>
					
				</form>
			</div>
			
		
<form action="process" method="POST">
		<div class="first-row">
		<label>List of transactions:</label>
				<table>
					    <tr>
					    	<th>Select</th>
					        <th>CID</th>
					        <th>SF</th>
					        <th>RF</th>
					        <th>SA</th>
					        <th>RA</th>
					        <th>Date</th>   
					 	 </tr>
					 </table>
		</div>
		<div class="data-row">
				<table>
				    <c:forEach items="${data}" var="user">
				        <tr>
				        	<td>
				        		<input type="checkbox" name="rowSelector" value="unchecked"/>
				        	</td>
				            <td><c:out value="${user.getCID()}"/></td>
				            <td><c:out value="${user.getSF()}"/></td>
				            <td><c:out value="${user.getRF()}"/></td>
				            <td><c:out value="${user.getSA()}"/></td>
				            <td><c:out value="${user.getRA()}"/></td>
				            <td><c:out value="${user.getDate()}"/></td>
				        </tr>
				    </c:forEach>
				  
				</table>		
		</div>
		
		<br>
		
		<div class="user-form">
			<label>Select method of analysis:</label>
			<select name="analysis-methods" class="form-control" >
		    			<option value="SOM">Self Organizing Map</option>
		   				<option value="Bayes">Naive Bayes</option>
			</select>
			<br>
			<button type="submit" class="btn btn-default">Analyze</button>
		</div>
		
		
</form>		

</body>
</html>