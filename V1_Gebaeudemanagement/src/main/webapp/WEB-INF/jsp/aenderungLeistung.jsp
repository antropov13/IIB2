<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" isELIgnored = "false"%>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html lang="en">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Gebaeudemenegement - Dienstleister</title>
	<link rel="stylesheet" href="styles/gmCSS.css">	
	
<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/1.10.1/jquery.min.js"></script>

</head>
<body onload="start()">

<div style="margin:0 auto; width:890px;">
	<div class="w3-container w3-blue-grey w3-opacity"">
		<div class="w3-display-container">
			<div style="float: left; width:300px">
				<h2>Guten Tag ${sessionScope.user.getFirmaname()}</h2>
			</div > 
			<div id="time"; style="float: right; margin-top: 20px;"></div>
		</div>
	</div>
</div>

<div class="w3-light-grey" style="margin:0 auto; width:890px; min-height:100%; position:absolute!important; margin-left: auto;margin-right: auto; left: 0; right: 0;">
  <div class="w3-sidebar w3-bar-block w3-light-grey w3-card" style="width:130px">
  <h5 class="w3-bar-item">Menu</h5>
  <button class="w3-bar-item w3-button tablink w3-red" onclick="openFunktion(event, 'Leistungen')">Spechern</button>
  <button class="w3-bar-item w3-button tablink" onclick="history.back()">Zurück</button>
  <form action="logout"><input type="submit" value="Logout" class="w3-bar-item w3-button tablink"></form>
</div>


  <div id="Leistungen" class="w3-container city" style="display:none;">
  	<div style="width:600px; float: left; height: 100%; margin-left:128px">
			<table class="w3-table w3-bordered">
				<tr class="w3-green">
						<th>Name</th>
						<th>Beschreibung</th>
						<th>Preis</th>
				</tr>
		
				<c:forEach items="${leistungen}" var="ln">
						<tr class="w3-light-grey">
							<td>${ln.getName()}</td>
							<td rowspan="2">${ln.getBeschreibung()}</td>
							<td rowspan="2">${ln.getPreis()}</td>
						</tr>
						<tr>
							<td>
							<button id="${ln.getId()}" class="w3-button w3-yellow">
							<a href="<%=request.getContextPath() %>/aenderungLeistung">&#9998;</a>
							</button>
							<button id="${ln.getId()}" class="w3-button w3-red" style="color: #000!important;">&#10005;</button>
							</td>
						</tr>
				</c:forEach>
			</table>
	</div>
	
	<div class="w3-sidebar w3-bar-block w3-light-grey w3-card" style="width:130px; float: right; margin-left: 743px;">
	<h5 class="w3-bar-item"></h5>
  	<button class="w3-bar-item w3-button" onclick="openCity(event, 'Veraendern')">Verändern</button>
  	<button class="w3-bar-item w3-button" onclick="openCity(event, 'Loeschen')">Löschen</button>
  	<button class="w3-bar-item w3-button" onclick="openCity(event, 'Hinzufuegen')">Hinzufügen</button>
	</div>
  </div>

  <div id="Auftraege" class="w3-container city" style="display:none">
    <h2>Auftraege</h2>
</div>

</div>
</body>
</html>