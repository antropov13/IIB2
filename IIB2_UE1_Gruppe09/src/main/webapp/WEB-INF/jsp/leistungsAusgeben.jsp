<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" isELIgnored = "false"%>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<style>
table, th, td {
	border: 1px solid black;
}
</style>
<title>Insert title here</title>
</head>
<body>
  
		<form action="logout">
		<input type = "submit"  value = "Logout" style=" margin-left:90% ;  margin-top:10px ;"></form>
		
		<p>Guten Tag ${sessionScope.user.getUsername() }</p>
		<p>Ihre Leistungsspektren:</p>
		
		<table style="width: 100%">
			<tr>
				<th>Name</th>
				<th>Beschreibung</th>
				<th>Preis</th>
			</tr>

			<c:forEach items="${leistungen}" var="ln">
				<tr>
					<td>${ln.getName()}</td>
					<td>${ln.getBeschreibung()}</td>
					<td>${ln.getPreis()}</td>
				</tr>
			</c:forEach>
			
		</table>
		



</body>
</html>