<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" isELIgnored = "false"%>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Stockwerk ändern</title>
<link rel="stylesheet" href="styles/gmCSS.css">
<script src="https://code.jquery.com/jquery-3.2.1.slim.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
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
	
	if (params['gebId']!=-1){
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
	openFunktion(event, 'Gebaeude', true);
	getParam();
}

</script>

</head>
<body onload="start()">

<div style="margin:0 auto; width:1000px;">
	<div class="w3-container w3-blue-grey w3-opacity"">
		<div class="w3-display-container">
			<div style="float: left; width:300px">
				<h2>Guten Tag ${sessionScope.user.getVorname()} ${sessionScope.user.getNachname()}</h2>
			</div > 
			<div id="time"; style="float: right; margin-top: 20px;"></div>
		</div>
	</div>
</div>

<c:if test="${not empty submitDone}">
  <script>alert("Alle Gebäude wurden hinzugefügt");
</script></c:if>


<!--  <form class="w3-container w3-card-1 w3-white" method="POST" action="aenderungLeistungForm"> --> 
<div class="w3-light-grey" style="margin:0 auto; width:1000px; min-height:100%; position:absolute!important; margin-left: auto;margin-right: auto; left: 0; right: 0;">
  <div class="w3-sidebar w3-bar-block w3-light-grey w3-card" style="width:130px">
  <h5 class="w3-bar-item">Menu</h5>
  <a href="<%=request.getContextPath() %>/gebaeude" class="w3-bar-item w3-button tablink w3-red">Gebäude</a>
  <!-- <button class="w3-bar-item w3-button tablink" type="submit">Speichern</button>  -->
  <button class="w3-bar-item w3-button tablink" onclick="history.back()">Zurück</button>
  <a href="<%=request.getContextPath() %>/logout" class="w3-bar-item w3-button tablink">Logout</a>
  </div>


  <div id="Gebaeude" class="w3-container city" style="display:block;">
  	<div style="width:710px; float: left; height: 100%; margin-left:148px">
			<div style="text-align:center; margin-top:10px; margin-bottom:10px;">Gebäude ${gebToEdit.getId()}</div>
			<form method="POST" id="changes" action="${pageContext.request.contextPath}/aenderungGebaeudeForm?gebID=${gebToEdit.getId()}">
					<div class="form-group">
					<label for="str">Straße: </label>
					<input required id="str" type="text" name="strasse" value="${gebToEdit.getStrasse()}" class="form-control"> 
					</div>
					<div class="form-group">
					<label for="nr"> Nummer: </label>
					<input required id="nr" type="text" name="nr" value="${gebToEdit.getHausnummer()}" class="form-control">
					</div>
					<div class="form-group">
					<label for="ort"> Ort: </label>
					<input required id="ort" type="text" name="ort" value="${gebToEdit.getOrt()}" class="form-control">
					</div>
					<div class="form-group">
					<label for="pz"> PLZ: </label>
					<input required id="pz" type="number" name="plz" value="${gebToEdit.getPlz()}" class="form-control"> 
					</div>
					<div class="form-group">
					<button id="${geb.getId()}" class="w3-button w3-yellow" title="Änderung speichern" type="submit">&#10003;</button> 		 
	  				</div>
	  		</form>
			
			<c:forEach items="${stwListGeb}" var="stw">
			<div class="w3-button w3-block w3-grey">${stw.getBezeichnung()}</div>
			</c:forEach>
			<br>
			<form action="${pageContext.request.contextPath}/hinzufuegenStockwerk?gebID=${gebToEdit.getId()}" method="POST">
			<div >
				<div><input placeholder="Bezeichnung" style="text-align: center;" class="w3-block w3-wheit" required id="bezeichnungStw" name="bezeichnungStw" type="text" value=""></div>
				<div><button class="w3-button w3-block w3-green">Stockwerk hinzufügen</button></div>
			</div>
			</form>
	</div>
	  
	  	<div class="w3-sidebar w3-bar-block w3-light-grey w3-card" style="width:130px; float: right; margin-left: 870px;">
		<h5 class="w3-bar-item"><p></p></h5>
	  	</div>
	</div>
	<!-- </form>  -->
</div>

</body>
</html>