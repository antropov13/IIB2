<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Mängel dokumentieren</title>
<link rel="stylesheet" href="styles/gmCSS.css">
<script type="text/javascript"
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.10.1/jquery.min.js"></script>

<script>
	function openFunktion(evt, funktion, flag) {
		var i, x, tablinks;
		x = document.getElementsByClassName("city");
		for (i = 0; i < x.length; i++) {
			x[i].style.display = "none";
		}
		tablinks = document.getElementsByClassName("tablink");
		if (flag === undefined) {
			for (i = 0; i < x.length; i++) {
				tablinks[i].className = tablinks[i].className.replace(
						" w3-red", "");
			}
		}
		evt.currentTarget.className += " w3-red";
		document.getElementById(funktion).style.display = "block";
	}

	function startTime() {

		var today = new Date();
		var options = {
			weekday : "long",
			year : "numeric",
			month : "short",
			day : "numeric",
			hour : "2-digit",
			minute : "2-digit",
			second : "2-digit"
		};
		document.getElementById('time').innerHTML = today.toLocaleDateString(
				"de-DE", options);
		var t = setTimeout(startTime, 500);
	}

	function start() {
		startTime();
		openFunktion(event, 'MaengelBericht', true);
	}

	function populateInput(s1, s2){
		var s1= document.getElementById(s1);
		var s2 = document.getElementById(s2);
		var j, id;
		var v1; 
		s2.innerHTML = "";
		var l = ${auftraege.size()};
		<c:forEach items="${auftraege}" var="item">
		v1 = ${item.getId()}
	    if (v1==s1.value){
	    	 j = '${item.getDienstleister()}';
	    	 id = ${item.getDlr_id()};
	    	 s2.value = j;
	    }
		</c:forEach> 	
	}  
</script>

</head>
<body onload="start()">

	<div style="margin: 0 auto; width: 1000px;">
		<div class="w3-container w3-blue-grey w3-opacity"">
			<div class="w3-display-container">
				<div style="float: left; width: 300px">
					<h2>Guten Tag ${sessionScope.user.getVorname()}
						${sessionScope.user.getNachname()}</h2>
				</div>
				<div id="time" ; style="float: right; margin-top: 20px;"></div>
			</div>
		</div>
	</div>

	<c:if test="${not empty submitDone}">
		<script>
			alert(" ");
		</script>
	</c:if>


	<!--  <form class="w3-container w3-card-1 w3-white" method="POST" action="aenderungLeistungForm"> -->
	<div class="w3-light-grey"
		style="margin: 0 auto; width: 1000px; min-height: 100%; position: absolute !important; margin-left: auto; margin-right: auto; left: 0; right: 0;">
		<div class="w3-sidebar w3-bar-block w3-light-grey w3-card"
			style="width: 130px">
			<h5 class="w3-bar-item">Menu</h5>
			<a href="<%=request.getContextPath()%>/maengel"
				class="w3-bar-item w3-button tablink w3-red">Mängelberichte</a>
			<!-- <button class="w3-bar-item w3-button tablink" type="submit">Speichern</button>  -->
			<button class="w3-bar-item w3-button tablink"
				onclick="history.back()">Zurück</button>
			<a href="<%=request.getContextPath()%>/logout"
				class="w3-bar-item w3-button tablink">Logout</a>
		</div>


		<div id="MaengelBericht" class="w3-container city" style="display: block;">
		Hola
			<div
				style="width: 710px; float: left; height: 100%; margin-left: 148px">
				<div style="margin-top: 10px; height: 30px; padding: 5px;"
					class="w3-block w3-green w3-left-align w3-round">Neue
					Mängelbericht erstellen</div>
				<form method="POST" id="cetReport"
					action="${pageContext.request.contextPath}/hinzufuegenBerichtForm?mglID=${mgl.getId()}"
					style="display: block; text-align: left; margin-top: 20px">

					<label for="titel"
						style="display: block; width: 70px;  margin-top: 10px">Titel:
					</label> 
					<input id="titel" type="text" name="titel" value="" style="display: block; width: 400px; margin-top: 10px" > 
					<label
						for="bes" style="display: block; width: 70px; margin-top: 10px">
						Beschreibung: </label>
					<textarea id="bes" type="text" name="bes" value="" style="display: block; width: 400px; height: 150px; margin-top: 10px"> </textarea>
					<label for="date" style="display: block;  margin-top: 10px">
						Datum (ausgeführt am): </label> <input id="date" type="date" name="hgk" value="" style="display: block; width: 400px;">
					<button id="id4" class="w3-button w3-yellow"
						title="Änderung speichern" type="submit" style="display: block; margin-top: 10px">Bestätigen</button>

				</form> 
			</div>
			<!-- </form>  -->
		</div>
</body>
</html>