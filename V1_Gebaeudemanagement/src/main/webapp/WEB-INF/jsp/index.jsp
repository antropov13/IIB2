<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
<link href="<c:url value="/resources/gebaeudemenegementCSS.css" />"
	rel="stylesheet">

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Gebaeudemenegement - Login</title>
</head>
<body>
	<h1 style="color: #FF0000">${warning}</h1>
	<h1>Herzlich Willkommen beim Gebaeudemenegement !</h1>
	Bitte geben Sie ihre Nutzerdaten ein:

	<div id="wrap">
		<form method="POST" action="verify">

			<table>
				<tr>
					<td style="color: #000000;">Fachrolle</td>
					<td><select name="fachrolle">
							<option>Dienstleister</option>
							<option>Dezernatmitarbeiter</option>
					</select></td>
				</tr>
				<tr>
					<td style="color: #000000;">Username</td>
					<td><input type="text" name="username"></td>
				</tr>

				<tr>
					<td style="color: #000000;">Password</td>
					<td><input type="password" name="password"></td>
				</tr>

				<tr>
					<td></td>
					<td><input type="submit" name="login" value="Einloggen"></td>
				</tr>
			</table>

		</form>
	</div>
</body>

</html>