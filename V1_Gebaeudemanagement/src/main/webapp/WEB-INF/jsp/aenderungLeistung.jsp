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

function getParam(){
	var params = window
    .location
    .search
    .replace('?','')
    .split('&')
    .reduce(
        function(p,e){
            var a = e.split('=');
            p[ decodeURIComponent(a[0])] = decodeURIComponent(a[1]);
            return p;
        },
        {}
    );
	if (params['LeistungID']!=-1){
		x = document.getElementsByName("sel");
		  for (i = 0; i < x.length; i++) {
		     x[i].style.display = "none";
		  }
	}
}

function start(){
	startTime();
	openFunktion(event, 'Leistungen', true);
	getParam();
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

<!-- <form class="w3-container w3-card-1 w3-white" method="POST" action="aenderungLeistungForm"> -->

<div class="w3-light-grey" style="margin:0 auto; width:890px; min-height:100%; position:absolute!important; margin-left: auto;margin-right: auto; left: 0; right: 0;">
  <div class="w3-sidebar w3-bar-block w3-light-grey w3-card" style="width:130px">
  <h5 class="w3-bar-item">Menu</h5>
  <button class="w3-bar-item w3-button tablink w3-red" onclick="openFunktion(event, 'Leistungen')">Leistung</button>
  <button class="w3-bar-item w3-button tablink" type="submit">Speichern</button>
  <button class="w3-bar-item w3-button tablink" onclick="history.back()">Zurück</button>
  <form action="logout"><input type="submit" value="Logout" class="w3-bar-item w3-button tablink"></form>
  </div>


  <div id="Leistungen" class="w3-container city" style="display:none;">
  	<div style="width:600px; float: left; height: 100%; margin-left:128px">
		<div class="w3-container">
			<div style="margin-top:10px; height:30px; padding: 5px;" class="w3-block w3-green w3-left-align w3-round">${spektrum.getName()}</div>
			<table class="w3-table w3-bordered">
					<tr>
						<th>Name</th>
						<th>Beschreibung</th>
						<th>Preis</th>
					</tr>
					<c:forEach items="${spektrum.getDienstleistungen()}" var="dln">
						<tr>
							<td>${dln.getName()}</td>
	  						<td>${dln.getBeschreibung()}</td>
	  						<td style="width: 70px;">${dln.getPreis()} &#8364;</td>
	  						<th>
							<button id="${dln.getId()}" class="w3-button w3-red" style="color: #000!important;" title="Löschen Dienstleistung">
							<a href="<%=request.getContextPath() %>/loeschenLeistung?DnlID=${dln.getId()}" onclick="return confirm('Möchten Sie die Leistung löschen?')">&#10005;</a> 
							</button>
							</button>
							</th>
	  					</tr>
					</c:forEach>
 			</table>
		</div>
	  </div>
	  
	  	<div class="w3-sidebar w3-bar-block w3-light-grey w3-card" style="width:130px; float: right; margin-left: 743px;">
		<h5 class="w3-bar-item"><p></p></h5>
	  	<button class="w3-medium w3-bar-item w3-button">
	  	<a href="<%=request.getContextPath() %>/loeschenLeistungsspektrum?LeistungsspektrumID=${spektrum.getId()}" onclick="return confirm('Möchten Sie den Leistungsspektrum löschen?')" style="text-decoration: none;">Löschen</a>
	  	</button>
	  	
	  	<button class="w3-bar-item w3-button" onclick="openCity(event, 'Hinzufuegen')">
		<a href="<%=request.getContextPath() %>/aenderungLeistung?LeistungID=-1">Hinzufügen</a>
		</button>
	  	</div>
	</div>
</div>
<!-- </form> -->
</body>
</html>