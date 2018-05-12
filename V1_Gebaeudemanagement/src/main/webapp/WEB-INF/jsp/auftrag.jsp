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
			<div style="float: left; width:300px">
				<h2>Guten Tag ${sessionScope.user.getFirmaname()}</h2>
			</div > 
			<div id="time"; style="float: right; margin-top: 20px;"></div>
		</div>
	</div>
</div>

<div class="w3-light-grey" style="margin:0 auto; width:1000px; min-height:100%; position:absolute!important; margin-left: auto;margin-right: auto; left: 0; right: 0;">
  <div class="w3-sidebar w3-bar-block w3-light-grey w3-card" style="width:130px">
  <h5 class="w3-bar-item">Menu</h5>
  <a href="<%=request.getContextPath() %>/leistungen" class="w3-bar-item w3-button tablink w3-red">Leistungen</a>
  <button class="w3-bar-item w3-button tablink" onclick="history.back()">Zurück</button>
  <a href="<%=request.getContextPath() %>/logout" class="w3-bar-item w3-button tablink">Logout</a>
  </div>


    <div style="width:870px; height: 100%; margin-left:130px; margin-top:10px">
	 
			<div class="w3-card-4" style="margin: 0 auto; width:800px">
    			<header class="w3-container w3-grey">
      				<h3>Auftrag ${auftrag.getId()}</h3>
    			</header>
    			
    			<div class="w3-container w3-white">
    				<div class="w3-panel w3-border-right w3-border-blue" style="width:35%; float:left;">
	    			    <figure style:="text-align: center;">
	  						<img src="img/6.jpg" class="w3-circle" style="width:60px;">
					    	<figcaption>${auftrag.getAuftragsersteller()}</figcaption>
					    </figure>
					</div>
					
					<div class="w3-panel" style="width:63%; float:right;">
					<table class="w3-table w3-bordered">
					<tr>
						<th>Name</th>
						<th>Beschreibung</th>
						<th>Häufigkeit</th>
					</tr>
					<c:forEach items="${auftrag.getDienstleistungList()}" var="dln">
						<tr>
							<td>${dln.getName()}</td>
	  						<td>${dln.getBeschreibung()}</td>
	  						<td>${dln.getHaeufigkeit()}</td>
	  					</tr>
					</c:forEach>
 					</table>
					</div>
					
					<div style="width:35%; margin-top:10px; margin-bottom:10px;" class="w3-border-right w3-border-blue">
						<table style="margin: 0 auto;" >
						<tr>
							<td>Ort:</td>
							<td>${auftrag.getGebaeude().getOrt()}</td>
						</tr>
						<tr>
							<td>Datum:</td>
							<td>${auftrag.getDateTag()}</td>
						</tr>
						
						<c:set var = "status" value = "${auftrag.getStatus()}"/>
						<c:choose>
						<c:when test = "${status == 'Ausfuehrung' }">
							<tr>
								<td>Status:</td>
								<td style="color:#0066ff">${auftrag.getStatus()}</td>
							</tr>
							<tr>
        						<td colspan="2"><a href="<%=request.getContextPath() %>/aenderungStatus?status=1" class="w3-block w3-button w3-blue">Erledigt</a></td>
        					</tr>
						</c:when>
						<c:when test = "${status == 'Erledigt' }">
							<tr>
							<td>Status:</td>
							<td style="color:#009933">${auftrag.getStatus()}</td>
							</tr>
							<tr>
        						<td colspan="2"><a href="<%=request.getContextPath() %>/aenderungStatus?status=4" class="w3-block w3-button w3-red">Löschen</button></td>
        					</tr>
						</c:when>
						<c:when test = "${status == 'Warte auf eine Antwort' }">
							<tr>
								<td>Status:</td>
								<td style="color:#ffcc00">${auftrag.getStatus()}</td>
							</tr>
							<tr>
        						<td colspan="2"><a href="<%=request.getContextPath() %>/aenderungStatus?status=2" class="w3-block w3-button w3-green">Annehmen</a></td>
        					</tr>
        					<tr>
        						<td colspan="2"><a href="<%=request.getContextPath() %>/aenderungStatus?status=3" class="w3-block w3-button w3-red">Ablehnen</a></td>
							</tr>
						</c:when>
						<c:when test = "${status == 'Abgelehnt' }">
							<tr>
							<td>Status:</td>
							<td style="color:#ff0000">${auftrag.getStatus()}</td>
							</tr>
							<tr>
        						<td colspan="2"><a href="<%=request.getContextPath() %>/aenderungStatus?status=4" class="w3-block w3-button w3-red">Löschen</button></td>
        					</tr>
						</c:when>
						</c:choose>
						
						</table>
    				</div>
    		</div>
 		</div>	  
	</div>

</body>
</html>