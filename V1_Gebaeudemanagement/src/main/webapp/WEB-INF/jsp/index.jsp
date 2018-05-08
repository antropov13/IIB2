<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html lang="en">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<link rel="stylesheet" href="styles/gmCSS.css">	
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/normalize/5.0.0/normalize.min.css">
	 <link rel="stylesheet" href="styles/style.css">
	 
	<title>Gebaeudemenegement - Login</title>
	
<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/1.10.1/jquery.min.js"></script>
	
</head>


<body>

<div>
	<div class="headerstyle">
	<!-- <h1 style="color: #FF0000">${warning}</h1> -->
	<h1>Herzlich Willkommen beim Gebäudemenegement</h1>
	</div>
</div>


	<div class="divloggin">
		<form class="w3-container w3-card-1 w3-white" method="POST" action="verify">
		
			<div class="wrapper">
  				<input type="checkbox" id="checkbox" name="fachrolle"/>
  				<label class="labelbox" for="checkbox"></label>
			</div>
			
			<div style="margin-top: 25px;">
			<label class="w3-text-blue"><b>Username</b></label>
			<input class="w3-input w3-border" name="username" type="text">
			  <p> 
			  <label class="w3-text-blue"><b>Password</b></label>     
			  <input class="w3-input w3-border" name="password" type="password"></p>
			  <p>      
			  <button class="w3-btn w3-blue" name="login">Einloggen</button></p>
			</div>
		</form>
	</div>
	<p></p>
	<div class="w3-container w3-center w3-animate-zoom">
  		<h2 style="background: #fff;">${warning}</h2>
  	</div>
</body>

</html>