<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" isELIgnored = "false"%>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html lang="en">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Gebaeudemenegement - Dezernatmitarbeiter</title>
	<link rel="stylesheet" href="styles/gmCSS.css">	
	
<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/1.10.1/jquery.min.js"></script>

<script>

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
	
}
</script>
</head>

<body onload="start()">

<div style="margin:0 auto; width:1000px;">
	<div class="w3-container w3-blue-grey w3-opacity"">
		<div class="w3-display-container">
			<div style="float: left; width:500px">
				<h2>Guten Tag ${sessionScope.user.getVorname()} ${sessionScope.user.getNachname()}</h2>
			</div > 
			<div id="time"; style="float: right; margin-top: 20px;"></div>
		</div>
	</div>
</div>

<div class="w3-light-grey" style="margin:0 auto; width:1000px; min-height:100%; position:absolute!important; margin-left: auto;margin-right: auto; left: 0; right: 0;">
  <div class="w3-sidebar w3-bar-block w3-light-grey w3-card" style="width:130px">
  <h5 class="w3-bar-item">Menu</h5>
  <a href="<%=request.getContextPath() %>/leistungendma" class="w3-bar-item w3-button tablink w3-red">Leistungen</a>
  <button class="w3-bar-item w3-button tablink" onclick="history.back()">Zurück</button>
  <a href="<%=request.getContextPath() %>/logout" class="w3-bar-item w3-button tablink">Logout</a>
  </div>


    <div style="width:940px; height: 100%; margin-left:100px; margin-top:10px">
	 
			<div class="w3-card-4" style="margin: 0 auto; width:800px">
    			<header class="w3-container w3-grey">
      				<h3>Neuer Auftrag</h3>
    			</header>
    			
    			<div class="w3-container w3-white">
    			
    			<div style="width:50%; float: left;">
    				<div class="w3-panel w3-border-right w3-border-blue" style="">
	    			    <figure style:="text-align: center;">
	  						<img src="img/6.jpg" class="w3-circle" style="width:80px;">
					    	<figcaption>${auftrag.getDienstleister()}</figcaption>
					    </figure>
					</div>
					
					<div  style="margin-top:10px; margin-bottom:10px;" class="w3-border-right w3-border-blue">
						<table style="margin: 0 auto;" >
						<tr>
							<td>Ort:</td>
							<td>${auftrag.getGebaeude().getOrt()}, ${auftrag.getGebaeude().getStrasse()}, ${auftrag.getGebaeude().getHausnummer()}</td>
						</tr>
						<tr>
							<td>Datum:</td>
							<td>${auftrag.getDateTag()}</td>
						</tr>
						
						<c:set var = "status" value = "${auftrag.getStatus()}"/>
						<c:choose>
						<c:when test = "${status == 'Nicht gesendet' }">
							<tr>
							<td>Status:</td>
							<td style="color:#000066">${auftrag.getStatus()}</td>
							</tr>
						</c:when>
						</c:choose>
        					
						</table>
    				</div>
					</div>
					
					<div style="width:50%; float: right; height: 100%" >
    				<a href="<%=request.getContextPath() %>/sendenAuftrag" onclick="return confirm('Möchten Sie die Auftrag senden?')" class="w3-button w3-teal w3-circle w3-xxlarge w3-round" style="margin-top:20%;">Auftrag senden</a>
    				</div>
    				
					<div class="w3-panel" style="margin: 20px;">
					<table class="w3-table w3-bordered">
					<tr>
						<th>Name</th>
						<th>Beschreibung</th>
						<th>Häufigkeit</th>
						<th colspan=2>Preis</th>
					</tr>
					<c:forEach items="${auftrag.getDienstleistungList()}" var="dln">
						<tr>
							<td>${dln.getName()}</td>
	  						<td>${dln.getBeschreibung()}</td>
	  						<td>${dln.getHaeufigkeit()}</td>
	  						<td>${dln.getPreis()} &#8364;</td>
	  						<th>
							<button id="${dln.getDlnId()}" class="w3-button w3-red" style="color: #000!important;" title="Löschen Dienstleistung">
							<a href="<%=request.getContextPath() %>/loeschenDnlausAuftrag?DnlID=${dln.getDlnId()}&LsID=${dln.getLs_id()}" onclick="return confirm('Möchten Sie die Leistung löschen?')">&#10005;</a> 
							</button>
							</th>
	  					</tr>
					</c:forEach>
 					</table>
					</div>
    		</div>
 		</div>	  
	</div>

</body>
</html>