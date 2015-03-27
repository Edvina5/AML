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
<title>Result Page</title>
</head>
<body>

	<div class="first-row2">
		<label>Results:</label>
				<table>
					    <tr>
					        <th>Transaction (format SF,RF,SA,RA,Suspicious?)</th>
					        
					 	 </tr>
					 </table>
		</div>
		<div class="data-row2">
				<table>
				    <c:forEach items="${data}" var="user">
				        <tr>
				            <td><c:out value="${user}"/></td>
				            
				        </tr>
				    </c:forEach>
				  
				</table>		
		</div>

</body>
</html>