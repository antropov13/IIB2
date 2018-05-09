<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html lang="en">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Gebaeudemenegement - Dienstleister</title>
	<link rel="stylesheet" href="styles/gmCSS.css">	
	
<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/1.10.1/jquery.min.js"></script>

<script>
function openFunktion(evt, funktion, flag) {
  var i, x, tablinks;
  x = document.getElementsByClassName("city");
  for (i = 0; i < x.length; i++) {
     x[i].style.display = "none";
  }
  tablinks = document.getElementsByClassName("tablink");
  if (flag === undefined){
	  for (i = 0; i < x.length; i++) {
	      tablinks[i].className = tablinks[i].className.replace(" w3-red", ""); 
	  }
  }
  evt.currentTarget.className += " w3-red";
  document.getElementById(funktion).style.display = "block";
}

function startTime() {

    var today = new Date();
    var options = {  
    	    weekday: "long", year: "numeric", month: "short",  
    	    day: "numeric", hour: "2-digit", minute: "2-digit" , 
    	    	second: "2-digit"
    	};  
    document.getElementById('time').innerHTML = today.toLocaleDateString("de-DE", options);
    var t = setTimeout(startTime, 500);
}

function start(){
	startTime();
	openFunktion(event, 'Leistungen', true);
}

function myFunction(id) {
    var x = document.getElementById(id);
    if (x.className.indexOf("w3-show") == -1) {
        x.className += " w3-show";
    } else { 
        x.className = x.className.replace(" w3-show", "");
    }
}

function newwindow(){
win = window.open("window.htm","win","height=300,width=300");
}

</script>

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
  <button class="w3-bar-item w3-button tablink w3-red" onclick="openFunktion(event, 'Leistungen')">Leistungen</button>
  <button class="w3-bar-item w3-button tablink" onclick="openFunktion(event, 'Auftraege')">Aufträge</button>
  <form action="logout"><input type="submit" value="Logout" class="w3-bar-item w3-button tablink"></form>
</div>

  <div id="Leistungen" class="w3-container city" style="display:none;">
  	<div style="width:600px; float: left; height: 100%; margin-left:128px">
			<!-- <table class="w3-table w3-bordered">  -->
				<!-- <tr class="w3-green">
						<th>Name</th>
						<th>Beschreibung</th>
						<th>Preis</th>
				</tr>
				 -->
				<div class="w3-container">
				<c:forEach items="${leistungen}" var="ln">
						<!-- <tr class="w3-light-grey">
							<td>
							 -->
							
							<!--  </td>-->
							
							<button  style="margin-top:10px;" onclick="myFunction('${ln.getName()}')" class="w3-btn w3-block w3-green w3-left-align w3-round">${ln.getName()}</button>
							
							
							<div id="${ln.getName()}" class="w3-container w3-hide">
							
							<table class="w3-table w3-bordered">
							<tr>
							<th>Name</th>
							<th>Beschreibung</th>
							<th>Preis</th>
							</tr>
							
							<c:forEach items="${ln.getDienstleistungen()}" var="dln">
							
							
							<!-- <tr>  -->
							
							
							
							<tr>
							  <td>${dln.getName()}</td>
  							  <td>${dln.getBeschreibung()}</td>
  							  <td style="width: 70px;">${dln.getPreis()} &#8364;</td>
  							 </tr>
  							 
  							 </c:forEach>
  							 
  							</table>
  							
  							
  							</div>
							<!-- 
							<td>${dln.getName()}</td>
							<td></td>
							<td>${dln.getPreis()}</td>
							 -->
							
							
							<!-- </tr>  -->
							
						<!-- </tr>  -->
						<!-- 
						<tr>
							<td>
							 <button id="${ln.getId()}" class="w3-button w3-yellow" title="Verändern Dienstleistung">
							<a href="<%=request.getContextPath() %>/aenderungLeistung?LeistungID=${ln.getId()}">&#9998;</a> 
							</button>
							
							<button id="${ln.getId()}" class="w3-button w3-red" style="color: #000!important;" title="Löschen Dienstleistung">
							<a href="<%=request.getContextPath() %>/loeschenLeistung?LeistungID=${ln.getId()}" onclick="return confirm('Möchten Sie die Leistung löschen?')">&#10005;</a> 
							</button>
							</td>
						</tr>
						 -->
				</c:forEach>
				</div>
			<!-- </table>  -->
	</div>
	
	<div class="w3-sidebar w3-bar-block w3-light-grey w3-card" style="width:130px; float: right; margin-left: 743px;">
	<h5 class="w3-bar-item"><p></p></h5>
  	<button class="w3-bar-item w3-button" onclick="openCity(event, 'Loeschen')">
  	<a href="<%=request.getContextPath() %>/loeschenLeistung?LeistungID=-1" onclick="return confirm('Möchten Sie alle Leistung löschen?')">Löschen alle</a>
  	</button>
  	
  	<button class="w3-bar-item w3-button" onclick="openCity(event, 'Hinzufuegen')">
	<a href="<%=request.getContextPath() %>/aenderungLeistung?LeistungID=-1">Hinzufügen</a>
	</button>
	
	</div>
  </div>

  <div id="Auftraege" class="w3-container city" style="display:none">
    <h2>Auftraege</h2>
</div>

</div>
</body>
</html>