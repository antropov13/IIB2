<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Mängelbericht Ändern</title> 
<link rel="stylesheet" href="styles/gmCSS.css">
<script src="https://code.jquery.com/jquery-3.2.1.slim.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">

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
		openFunktion(event, 'Maengel', true);
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
			alert("Das Bericht wurde erfolgreich geändert ");
		</script>
	</c:if>

 
	<div class="w3-light-grey" 
		style="margin: 0 auto; width: 1000px; margin-left: auto; margin-right: auto; left: 0; right: 0;">
		<div class="w3-sidebar w3-bar-block w3-light-grey" style="height: inherit;">
			<h5 class="w3-bar-item">Menu</h5>
			<a href="#Maengel"
				class="w3-bar-item w3-button tablink w3-red">Mängelberichte</a>
			<!-- <button class="w3-bar-item w3-button tablink" type="submit">Speichern</button>  -->
			<button class="w3-bar-item w3-button tablink"
				onclick="history.back()">Zurück</button>
			<a href="<%=request.getContextPath()%>/logout"
				class="w3-bar-item w3-button tablink">Logout</a>
		</div>


		<div id="Maengel" style="margin-left:25%;"> 
				<div style="margin-top: 10px; height: 30px; padding: 5px;  text-align: center;" >
				 Mängelbericht ändern</div>
				<div> 
				<form method="POST" id="addReport"
					action="${pageContext.request.contextPath}/aenderungBerichtForm?ldoID=${ldoToEdit.getId()}">
					<div class="form-group row">
					<label for="titel"
						style="display: block; width: 70px;  margin-top: 10px">Titel:
					</label>
					<input id="titel" type="text" name="titel" value="${ldoToEdit.getTitel()}" style="display: block; width: 400px; margin-top: 10px" class="form-control"> 
					</div>
					<div class="form-group row">
						<label for="auftrag" class="col-3 col-form-label "> Für den Auftrag:
						</label> 
						<select class="form-control col-1" name="auftrag" id="auftrag" onchange="populateInput('auftrag', 'dienstleister'); populateD('auftrag', 'dienstleistungen')"> 
						<option value = "${mglToEdit.getAuftrag() }"> ${mglToEdit.getAuftrag()}</option>
						<c:forEach items="${auftraege}" var="aft">
							<c:if test="${mglToEdit.getAuftrag() != aft.getId() }">
										 <option value="${aft.getId()}"> ${aft.getId()}
							</option>
									</c:if>
						</c:forEach>
						</select>
						<label for="dienstleister" class="col-3 col-form-label" style="margin-left: 30px"> Der Dienstleister:
						</label> 
						<input type="text" readonly class="form-control-plaintext col-2" name="dienstleister" id="dienstleister" value="${aft_mgl.getDienstleister()}" >
					</div>  
					<div class="form-group">
					<label for="date" style="display: block;  margin-top: 10px">
						Datum: </label> <input id="date" type="date" name="date" value="${ldoToEdit.getDate()}" style="display: block; width: 400px;"></div>
					<div class="form-group">
						<label for="comment"> Weitere Kommentare:
						</label> 
						<textarea id="bes" name="bes"  style="display: block; width: 400px; height: 150px; margin-top: 10px"> ${ldoToEdit.getBeschreibung()}
						</textarea>
					</div>
					  
					<button id="id4" class="w3-button w3-yellow"
						title="Änderung speichern" type="submit" style="display: block; margin-top: 10px">Speichern und fortfahren</button>

				</form>
				
				</div> 
		</div>
		</div> 
</body>
</html>