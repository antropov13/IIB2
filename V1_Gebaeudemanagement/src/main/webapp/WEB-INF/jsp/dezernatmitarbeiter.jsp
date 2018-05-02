<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="<c:url value="/resources/gebaeudemenegementCSS.css" />"
	rel="stylesheet">
<style>
table, th, td {
	border: 1px solid black;
}
</style>

<script>
function startTime() {
    var today = new Date();
    var options = {  
    	    weekday: "long", year: "numeric", month: "short",  
    	    day: "numeric", hour: "2-digit", minute: "2-digit" , 
    	    	second: "2-digit"
    	};  
    document.getElementById('txt').innerHTML = "Heute ist: " + today.toLocaleDateString("de-DE", options);
    var t = setTimeout(startTime, 500);
}

</script>

<title>Gebaeudemenegement - Dezernatmitarbeiter</title>
</head>
<body onload="startTime()">

	<div id="txt"></div>

	<p>Guten Tag ${sessionScope.user.getVorname()}
		${sessionScope.user.getNachname()}</p>
	<div id="wrap">
		<p>${feedback}</p>
	</div>
	
		<p id="demo"></p>
	<div>
		<form action="logout">
			<input type="submit" value="Logout"; margin-top: 10px;">
		</form>

	</div>
</body>
</html>