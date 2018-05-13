<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" isELIgnored = "false"%>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html lang="en">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Gebaeudemenegement - Dienstleister</title>
	<link rel="stylesheet" href="styles/gmCSS.css">		

<script type="text/javascript" src="http://code.jquery.com/jquery-1.7.1.min.js"></script>

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

function myFunction(id) {
    var x = document.getElementById(id);
    if (x.className.indexOf("w3-show") == -1) {
        x.className += " w3-show";
    } else { 
        x.className = x.className.replace(" w3-show", "");
    }
}

function compareNumeric(a, b) {
	  if (a > b) return 1;
	  if (a < b) return -1;
	}
	
function uniq_fast(a) {
    var seen = {};
    var out = [];
    var len = a.length;
    var j = 0;
    for(var i = 0; i < len; i++) {
         var item = a[i];
         if(seen[item] !== 1) {
               seen[item] = 1;
               out[j++] = item;
         }
    }
    return out;
}

function arrays_equal(a, b) {
    return JSON.stringify(a) == JSON.stringify(b);
}

function ShowResults(value) {
	   alert(value);
	}

$(function() {
    $('#dlnselect').change(function(e) {
        var opts = e.target.options;
        var len = opts.length;
        var alle = []
        var selected = []
        var unselected = []
        
        for (var i = 0; i < len; i++) {
        	alle.push(+opts[i].value);
        }
        //alert(alle1)
        
        for (var i = 0; i < alle.length; i++) {
            if (opts[i].selected) {
            }
            else{
            	unselected.push(i+1);
            }
        }
        for (var i = 0; i < alle.length; i++) {
            if (opts[i].selected) {
            	selected.push(+opts[i].value);
            }
        }
        
        var s =  ${dienstleiter.size()};
        var dln_count_select = selected.length;
        var taglist;
        
        for(var j=0; j<alle.length; j++)
        {
        	var input = document.getElementsByName("TR_ID_"+unselected[j]);
        	var inputList = Array.prototype.slice.call(input);
        	for(i = 0; i < input.length; i++)
        	{
        		input[i].style.display="none";
        	}
        }
        
        for(var j=0; j<alle.length; j++)
        {
        	
        	var input = document.getElementsByName("TR_ID_"+selected[j]);
        	var inputList = Array.prototype.slice.call(input);
        	for(i = 0; i < input.length; i++)
        	{
        		input[i].style.display="";
        	}
        }
        	
        
    	var mass1 = [];
        for (var i = 1; i<=s ; i++)
        {
        	var mass1 = [];
        	var div = document.getElementById('DLR_ID_'+i);
        	var elems = div.getElementsByTagName('*');
        	
        	for(var j=0; j<elems.length; j++) 
        	{

        		for(var k = 0; k<dln_count_select; k++)
        			{
	        		if(elems[j].id == "DLN_ID_"+selected[k]){
	        			mass1.push(selected[k]);             	
	        		}
        		}     		
        	}
        	uniquemass = uniq_fast(mass1);
        	uniquemass.sort(compareNumeric);
        	selected.sort(compareNumeric);
        	
        	//alert("final select " + selected + " mass " + uniquemass);
        	//alert("count" + dln_count_select +" : "+ dln_count_flag +"flag")
    		if (!arrays_equal(uniquemass, selected))
			{
    		//alert('DELET DLR_ID_'+i)
    		//alert("none DLR_ID_"+i)
			document.getElementById('DLR_ID_'+i).style.display = "none";
			}
    		else{
    			//alert('Visible DLR_ID_'+i)
    			//alert("vis DLR_ID_"+i)
    			document.getElementById('DLR_ID_'+i).style.display = "";
    		}
        }
    
    }); 
});


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


    <div style="width:870px; height: 100%; margin-left:130px; margin-top:10px">
	 
			<div class="w3-card-4" style="margin: 0 auto; width:800px">
    			<header class="w3-container w3-grey">
      				<h3>Auftrag</h3>
    			</header>
    			<div class="w3-container w3-white">					
					<div class="w3-panel" style="width:100%;">
					<form class="w3-container w3-card-1" method="POST" action="neuerAuftrag">
					<table class="w3-table w3-bordered">
					<tr style="width:50%;">
						<td>Leistungen <i style="font-size: 11pt">(Ctrl + Mausklick für Mehrfachauswahl):</i></td>
					</tr>
					<tr>
							<td rowspan="2">
							<select required name="dlnselect" id="dlnselect" multiple size="${leistungen.size()}" class="w3-btn w3-block w3-light-grey w3-left-align"  style="font-size: 12pt">
							<c:forEach items="${leistungen}" var="dln">
							<option value="${dln.getDlnId()}">${dln.getName()}</option>
							</c:forEach>
							</select>
							</td>
							</script>
						
				    		<td style="height:30px;">Gebäude:
							<td><select name="gebaeude" required class="w3-btn w3-block w3-light-grey" style="font-size: 9pt";>
								<c:forEach items="${gebaeude}" var="geb">
								<option name="gebaeude" value="${geb.getId()}">${geb.getOrt()}, ${geb.getStrasse()}, ${geb.getHausnummer()}</option>
								</c:forEach>
								</select>
							</td>
							</tr>
							
							<tr>
							  </td>
								<td>Datum:</td>
								<td><input name="datum" value="" required style="width:100%" type="date"/></td>
							</tr>
						
						</tr>
					<c:forEach items="${dienstleiter}" var="dlr">
					<tr id="DLR_ID_${dlr.getId()}">
					<td colspan="3" id="qwery">
					<ul class="w3-ul w3-card-4">
						<li class="w3-bar">
						<div style="width:40%; float:left;">
	    			    <figure style:="text-align: left;" style="margin: 10px;">
	  						<img src="img/6.jpg" class="w3-circle" style="width:40px;">
					    	<figcaption>${dlr.getFirmaname()}</figcaption>
					    </figure>
					    </div>	
					    <div style="float:right;">
					    <button name="dlr" value="${dlr.getId()}" class="w3-button w3-teal" type="submit">Auswählen</button>
					    </div>
					    
					    <table class="w3-table w3-bordered">
									<tr>
										<th>Name</th>
										<th>Beschreibung</th>
										<th>Häufigkeit</th>
										<th>Preis</th>
									</tr>
						
					    <c:forEach items="${dlr.getLeistungsspektren()}" var="ls">
									<c:forEach items="${ls.getDienstleistungen()}" var="dln">
											<div name="DIV_ID_${dlr.getId()}">
										      <div class="w3-bar-item" id="DLN_ID_${dln.getDlnId()}">
										        <tr name="TR_ID_${dln.getDlnId()}">
													<td>${dln.getName()}</td>
							  						<td>${dln.getBeschreibung()}</td>
							  						<td>${dln.getHaeufigkeit()}</td>
							  						<td style="width: 80px;">${dln.getPreis()} &#8364;</td>
							  					</tr>
										      </div>
										     </div>
										</c:forEach>	     
									</c:forEach>
								</table>
								</div>
							</li>						
						</ul> 
					</td>
					</tr>
					</c:forEach>
 					</table>
 					</form>
					</div>

    		</div>
 		</div>	  
	</div>

</body>
</html>