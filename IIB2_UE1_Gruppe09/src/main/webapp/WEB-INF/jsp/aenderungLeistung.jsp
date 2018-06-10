<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" isELIgnored = "false"%>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html lang="en">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Gebaeudemenegement - Dienstleister</title>
<link rel="stylesheet" href="styles/gmCSS.css">	
<link rel="stylesheet" href="styles/style.css">	
<script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
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
	
	if (params['warning']==true){
		alert("qwe")
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

<c:if test="${not empty submitDone}">
  <script>alert("Alle Leistungen wurden schon in Leistungsspektrum hinzugefügt");
</script></c:if>


<form class="w3-container w3-card-1 w3-white" method="POST" action="aenderungLeistungForm">
<div class="w3-light-grey" style="margin:0 auto; width:1000px; min-height:100%; position:absolute!important; margin-left: auto;margin-right: auto; left: 0; right: 0;">
  <div class="w3-sidebar w3-bar-block w3-light-grey w3-card" style="width:130px">
  <h5 class="w3-bar-item">Menu</h5>
  <a href="<%=request.getContextPath() %>/leistungen" class="w3-bar-item w3-button tablink w3-red">Leistungen</a>
  <button class="w3-bar-item w3-button tablink" type="submit">Speichern</button>
  <button class="w3-bar-item w3-button tablink" onclick="history.back()">Zurück</button>
  <a href="<%=request.getContextPath() %>/logout" class="w3-bar-item w3-button tablink">Logout</a>
  </div>


  <div id="Leistungen" class="w3-container city" style="display:block;">
  	<div style="width: 800px; float: right; height: 100%; padding:15px; display: block;">
		<div  style="float: right; margin-top: 20px; margin-bottom: 15px;">
			<a href="<%=request.getContextPath() %>/loeschenLeistungsspektrum?LeistungsspektrumID=${spektrum.getId()}" onclick="return confirm('Möchten Sie den Leistungsspektrum löschen?')" style="text-decoration: none;">
			<i class="fa fa-trash"></i>Löschen</a>
	  		<a href="<%=request.getContextPath() %>/hinzufuegenLeistung?LeistungID=-1" style="text-decoration: none; margin-left:15px;">Leistung hinzufügen</a>
		</div>
		<div class="w3-container" style="margin-right: 30px;">
		<div style="height:30px; padding: 5px;" class=" w3-green w3-left-align w3-round">Leistungsspektrum ${spektrum.getName()}</div>
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
	  						<td style="width: 100px;">
	  						<input id="${dln.getDlnId()}" name="Preis ${dln.getDlnId()}" type="number" min="0" value="${dln.getPreis()}" style="width: 60px"> &#8364;</input >
	  						</td>
	  						<th>
	  						<a href="<%=request.getContextPath() %>/loeschenLeistung?DnlID=${dln.getDlnId()}" onclick="return confirm('Möchten Sie die Leistung löschen?')">&#10005;</a> 
 
							</th>
	  					</tr>
					</c:forEach>
 			</table>
	  </div> 
	</div> 	
</div>
</div> 
	</form>

</body>
</html>