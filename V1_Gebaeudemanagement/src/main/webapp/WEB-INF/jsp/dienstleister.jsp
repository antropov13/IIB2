<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html lang="en">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Gebaeudemenegement - Dienstleister</title>
	<link rel="stylesheet" href="styles/gmCSS.css">	
	<link rel="stylesheet" href="styles/style.css">	
	
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

<div style="margin:0 auto; width:1000px;">
	<div class="w3-container w3-blue-grey w3-opacity"">
		<div class="w3-display-container">
			<div style="float: left; width:300px">
				<h2>Guten Tag ${sessionScope.user.getFirmaname()}</h2>
			</div > 
			<div id="time"; style="float: right; margin-top: 20px; margin-right: 10px;"></div>
		</div>
	</div>
</div>

<div class="w3-light-grey" style="margin:0 auto; width:1000px; min-height:100%; position:absolute!important; margin-left: auto;margin-right: auto; left: 0; right: 0;">
  <div class="w3-sidebar w3-bar-block w3-light-grey w3-card" style="width:130px">
  <h5 class="w3-bar-item">Menu</h5>
  
  <c:set var = "warte_auftrag" value = "0"/>
  <c:forEach items="${auftraege}" var="at">
  <c:set var = "status" value = "${at.getStatus()}"/>
  <c:choose>
	<c:when test = "${status == 'Warte auf eine Antwort' }">
		 <c:set var = "warte_auftrag" value = "${warte_auftrag + 1}"/>
	</c:when>
  </c:choose>
  </c:forEach>
  <c:if test="${warte_auftrag == 0}"><c:set var = "warte_auftrag" value = ""/></c:if>
  
  
  <button class="w3-bar-item w3-button tablink w3-red" onclick="openFunktion(event, 'Leistungen')">Leistungen</button>
  <button class="w3-bar-item w3-button tablink" onclick="openFunktion(event, 'Auftraege')">Aufträge ${warte_auftrag}</button>
  <form action="logout"><input type="submit" value="Logout" class="w3-bar-item w3-button tablink"></form>
</div>

  <div id="Leistungen" class="w3-container city" style="display:none;">
  	<div style="width:710px; float: left; height: 100%; margin-left:148px">
		<div class="w3-container">
			<c:forEach items="${leistungen}" var="ln">
			<button  style="margin-top:10px;" onclick="myFunction('${ln.getName()}')" class="w3-btn w3-block w3-green w3-left-align w3-round">Leistungsspektrum ${ln.getName()}</button>
			<div id="${ln.getName()}" class="w3-container w3-hide">
				<table class="w3-table w3-bordered">
					<tr>
						<th>Name</th>
						<th>Beschreibung</th>
						<th>Häufigkeit</th>
						<th>Preis</th>
						<th>
							<button id="${ln.getId()}" class="w3-button w3-yellow" title="Verändern Dienstleistung">
								<a href="<%=request.getContextPath() %>/aenderungLeistung?LeistungsspektrumID=${ln.getId()}">&#9998;</a> 
							</button>
						</th>
					</tr>
					<c:forEach items="${ln.getDienstleistungen()}" var="dln">
						<tr>
							<td>${dln.getName()}</td>
	  						<td>${dln.getBeschreibung()}</td>
	  						<td>${dln.getHaeufigkeit()}</td>
	  						<td style="width: 80px;">${dln.getPreis()} &#8364;</td>
	  					</tr>
					</c:forEach>
 				</table>
			</div>
			</c:forEach>
		</div>
	</div>
	
	<div class="w3-sidebar w3-bar-block w3-light-grey w3-card" style="width:130px; float: right; margin-left: 870px;">
	<h5 class="w3-bar-item"><p></p></h5>
  	<button class="w3-bar-item w3-button" onclick="openCity(event, 'Loeschen')">
  	<a href="<%=request.getContextPath() %>/loeschenLeistungsspektrum?LeistungsspektrumID=-1" onclick="return confirm('Möchten Sie alle Leistungsspektren löschen?')">Löschen alle</a>
  	</button>
  	
  	<button class="w3-bar-item w3-button" onclick="openCity(event, 'Hinzufuegen')">
	<a href="<%=request.getContextPath() %>/hinzufuegenLeistungsspektrum">Hinzufügen</a>
	</button>
	</div>
  </div>

  <div id="Auftraege" class="w3-container city" style="display:none">
    <div style="width:870px; height: 100%; margin-left:130px">
		<div class="container">
			<div class="catalog">
			<c:forEach items="${auftraege}" var="at">
			<div class="w3-card-4 item" style="margin-top: 10px;">
    			<header class="w3-container w3-green">
      				<h5>Auftrag ${at.getId()}</h5>
    			</header>
    			    <div class="w3-container w3-white">
	    			    <div style="width:40%; float:left;">
	    			    <figure style:="text-align: center;" style="margin: 10px;">
	  						<img src="img/6.jpg" class="w3-circle" style="width:60px;">
					    	<figcaption>${at.getAuftragsersteller()}</figcaption>
					    </figure>
					    </div>
					  
					<div style="width:60%; float:right; margin-top:20px;">
					<c:set var = "status" value = "${at.getStatus()}"/>
					<c:choose>
					<c:when test = "${status == 'Ausfuehrung' }">
						<div style="float:left;">Status:</div>
						<div style="color:#0066ff; float:right; margin-right:5px;">Ausführung</div>
					</c:when>
					<c:when test = "${status == 'Erledigt' }">
						<div style="float:left;">Status:</div>
						<div style="color:#009933; float:right; margin-right:5px;">Erledigt</div>
					</c:when>
					<c:when test = "${status == 'Warte auf eine Antwort' }">
						<div style="float:left;">Status:</div>
						<div style="color:#ffcc00; float:right; margin-right:5px;">Warte auf eine Antwort</div>
					</c:when>
					<c:when test = "${status == 'Abgelehnt' }">
						<div style="float:left;">Status:</div>
						<div style="color:#ff0000; float:right; margin-right:5px;">Abgelehnt</div>
					</c:when>
					</c:choose>
					<br>
					<div>
					<div style="float:left;">Ort:</div>
					<div style="float:right; margin-right:5px;">${at.getGebaeude().getOrt()}</div>
					</div>
					<br>
					<div>
					<div style="float:left;">Datum:</div>
					<div style="float:right;  margin-right:5px;">${at.getDateTag()}</div>
					</div>
				</div>
					
				    </div>    
				    <a href="<%=request.getContextPath() %>/oeffnenAuftragDLR?AuftragID=${at.getId()}" class="w3-button w3-block w3-dark-grey">Öffnen</a> 

		    </div>
			</c:forEach>
			</div>
		</div>
	</div>
	 
</div>

</div>
</body>
</html>