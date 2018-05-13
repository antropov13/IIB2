<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Gebaeudemenegement - Dezernatmitarbeiter</title>
<link rel="stylesheet" href="styles/gmCSS.css">
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">

<style>
#gebs {
	margin-top: 40px;
}

#gebs td, #gebs th {
	border: 1px solid #ddd;
	padding: 8px;
	text-align: center;
}

#gebs tr:nth-child(even) {
	background-color: #f2f2f2;
}

#gebs th {
	padding-top: 12px;
	padding-bottom: 12px;
	text-align: center;
	background-color: #4CAF50;
	color: white;
}

.addItem {
	text-align: center;
	text-decoration: none;
	background-color: #f2f2f2;
	color: #42477F;
}
</style>
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
		openFunktion(event, 'Gebaeude', true);

	}

	function myFunction(id) {
		var x = document.getElementById(id);
		if (x.className.indexOf("w3-show") == -1) {
			x.className += " w3-show";
		} else {
			x.className = x.className.replace(" w3-show", "");
		}
	}

	function newwindow() {
		win = window.open("window.htm", "win", "height=300,width=300");
	}
</script>

</head>

<body onload="start()">

	<div style="margin: 0 auto; width: 1000px;">
		<div class="w3-container w3-blue-grey w3-opacity">
			<div class="w3-display-container">
				<div style="float: left; width: 500px">
					<h2>Guten Tag ${sessionScope.user.getVorname()}
						${sessionScope.user.getNachname()}</h2>
				</div>
				<div id="time"
					style="float: right; margin-top: 20px; margin-right: 10px;"></div>
			</div>
		</div>
	</div>

	<div class="w3-light-grey"
		style="margin: 0 auto; width: 1000px; min-height: 100%; position: absolute !important; margin-left: auto; margin-right: auto; left: 0; right: 0;">
		<div class="w3-sidebar w3-bar-block w3-light-grey w3-card"
			style="width: 130px">
			<h5 class="w3-bar-item">Menu</h5>
			<button class="w3-bar-item w3-button tablink w3-red"
				onclick="openFunktion(event, 'Gebaeude')">Gebäude</button>
			<button class="w3-bar-item w3-button tablink"
				onclick="openFunktion(event, 'Dienstleistungen')">Leistungen</button>
			<button class="w3-bar-item w3-button tablink"
				onclick="openFunktion(event, 'Auftraege')">Aufträge</button>
			<form action="logout">
				<input type="submit" value="Logout"
					class="w3-bar-item w3-button tablink">
			</form>
		</div>

		<div id="Gebaeude" class="w3-container city" style="display: none;">
			<div
				style="width: 710px; float: left; height: 100%; margin-left: 148px">

				<table class="w3-table w3-bordered" id="gebs">
					<tr>
						<th>ID</th>
						<th>Strasse</th>
						<th>Hausnr.</th>
						<th>Ort</th>
						<th>PLZ</th>
						<th>Ersteller</th>
						<th>Bearbeiten</th>
						<th>Löschen</th>
					</tr>
					<c:forEach items="${gebaeude}" var="geb">
						<div id="geb${geb.getId()}" class="w3-container w3-hide">

							<tr>
								<td>${geb.getId()}</td>
								<td>${geb.getStrasse()}</td>
								<td>${geb.getHausnummer()}.</td>
								<td>${geb.getOrt()}</td>
								<td>${geb.getPlz()}</td>
								<td>${geb.getDma_id()}</td>
								<td><c:if test="${mGebaeude.contains(geb)}">
										<form method="POST">
											<button type="submit"
												formaction="${pageContext.request.contextPath}/aenderungGebaeude?gebID=${geb.getId()}">
												<i class="fa fa-edit"></i>
											</button>
										</form>
									</c:if></td>
								<td><c:if test="${mGebaeude.contains(geb)}">
										<form method="POST">
											<button type="submit"
												formaction="${pageContext.request.contextPath}/loeschenGebaeude?gebID=${geb.getId()}">
												<i class="fa fa-trash"></i>
											</button>
										</form>
									</c:if></td>
							</tr>
						</div>
					</c:forEach>
				</table>
			</div>
			<div class="w3-sidebar w3-bar-block w3-light-grey w3-card"
				style="width: 130px; float: right; margin-left: 870px;">
				<a class="addItem"
					href="<%=request.getContextPath()%>/hinzufuegenGebaeude" style="margin-top: 25px;"> <i
					class="fa fa-building"></i> Gebäude hinzufügen
				</a>
			</div>
		</div>

		<div id="Dienstleistungen" class="w3-container city"
			style="display: none;">
			<div
				style="width: 810px; float: left; height: 100%; margin-left: 148px;">
				<div  style="float: right; margin-top: 20px; margin-bottom: 15px;">
					<a class="addItem" href="<%=request.getContextPath()%>/hinzufuegenDienstleistung"> <i
						class="fa fa-tasks"></i> Dienstleistung hinzufügen
					</a>
				</div>
				<div class="w3-container">
					<c:forEach items="${dienstleistungen}" var="dln">
						<button style="margin-top: 10px;"
							onclick="myFunction('dln${dln.getName()}')"
							class="w3-btn w3-block w3-green w3-left-align w3-round">Dienstleistung:
							${dln.getName()}</button>
						<div id="dln${dln.getName()}" class="w3-container w3-hide">
							<table class="w3-table w3-bordered">
								<tr>
									<th>Beschreibung</th>
									<th>Häufigkeit.</th>
									<th>Ersteller ID</th>
									<th>Edit</th>
									<th>Los</th>
								</tr>
								<tr>
									<td>${dln.getBeschreibung()}</td>
									<td>${dln.getHaeufigkeit()}</td>
									<td>${dln.getDmaId()}</td>
									<td><c:if test="${dln.getDmaId() == user.getId()}">
											<form method="POST">
												<button type="submit"
													formaction="${pageContext.request.contextPath}/aenderungDienstleistung?dlnID=${dln.getDlnId()}">
													<i class="fa fa-edit"></i>
												</button>
											</form>
										</c:if></td>
									<td><c:if test="${dln.getDmaId() == user.getId()}">
											<form method="POST">
												<button type="submit"
													formaction="${pageContext.request.contextPath}/loeschenDienstleistung?dlnID=${dln.getDlnId()}">
													<i class="fa fa-trash"></i>
												</button>
											</form>
										</c:if></td>
								</tr>
							</table>
						</div>

					</c:forEach>

				</div>
			</div>
		</div>

		<div id="Auftraege" class="w3-container city" style="display: none">
			<h2>Auftraege</h2>
		</div>

	</div>
</body>
</html>