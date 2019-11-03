<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="false"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<html>
<head>

<style>

.modal-body {
    max-height: calc(100vh - 210px);
    overflow-y: auto;
}
</style>

<title>View Standings</title>

<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.0/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.0/umd/popper.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.0/js/bootstrap.min.js"></script>


<script>
	function getStandingDetails(leagueid) {
		var data = {}
		data["query"] = 'sad';

		$.ajax({
			type : "GET",
			url : "${pageContext.request.contextPath}/ViewStanding/getStandingDetails",
			dataType : "json",
			data : {
				"id" : leagueid
			},
			success : function(data) {
				var txt="";
				txt += "<div class='table-responsive'>"
				txt += "<h4 class='modal-title' id='header_'>Standings</h4><table class='table' style='width: 100%; align: 'left''>"
				txt+="<tr><th>#</th><th>Team</th><th>GP</th><th>W</th><th>D<th>L</th></th><th>GF</th><th>GA</th><th>Points</th></tr>"
				for (x in data) {
					
				        txt += "<tr><td>" + data[x].rank + "</td><td>" + data[x].teamname + "<td>" + data[x].GP + "</td><td> "+data[x].w+"</td><td>"+data[x].d+" </td><td>"+data[x].l+" </td><td>" + data[x].GF + "</td><td>" + data[x].GA + "</td><td>" + data[x].points + "</td></tr>";
				}
				      txt += "</table></div>"        
				      document.getElementById("demo").innerHTML = txt;
				
				$('#myModal').show();
				$("#modalBody_").text(data.responseText);
			},
			error : function(ts) {
				//alert(ts.responseText)

			}

		});
	}
	function getResultsFixtures(leagueid) {
		
		
		var data = {}
		data["query"] = 'sad';

		$.ajax({
			type : "GET",
			url : "${pageContext.request.contextPath}/ViewStanding/getResultsFixtures",
			dataType : "json",
			data : {
				"id" : leagueid
			},
			success : function(data) {
				
				var ss=JSON
				
				
				var txt="";
				txt += "<div class='table-responsive'>"
					txt+="<table class='table' align: 'left'>"
				for (x in data) {
					txt+="<tr style='background-color:cyan'><td>Gameweek "+data[x].gw+"</td></tr>";
				      // txt += "<tr><td>" + data[x].data + "</td></tr>";
				       var arrays=data[x].data.split('#');
				       
				       
				       for (i = 1; i < arrays.length; i++) { 
				    	   var innerArray= arrays[i].split(':');
				    	 // alert(innerArray[0]);
				    	  txt +=" <tr><th></th><th></th><th></th></tr>"
				    	 
				    	   txt += "<tr><td style='font-weight:bold'>" + innerArray[0] + "</td><td>vs</td><td style='font-weight:bold'>" + innerArray[1] + "</td></tr>";
				    	   
				    	   txt += "<tr><td>" + innerArray[2].replace('', ' ') + "</td></td><td></td><td>" + innerArray[3].replace('', ' ') + "</td></tr>";
				    	   txt += "<tr><td>" + innerArray[4].replace('--', ' ') + "</td></td><td></td><td>" + innerArray[5].replace('-', ' ') + "</td></tr>"; 
				       }
				        /* txt += "<tr><td>" + data[x].hg + "</td><td>" + data[x].ag + "</td></tr>";
				        txt += "<tr><td>" + data[x].hp + "</td><td>" + data[x].ap + "</td></tr>";   */
				}
				      txt += "</table></div>"        
				      document.getElementById("demo").innerHTML = txt;
				
				$('#myModal').show();
				$("#modalBody_").text(data.responseText);
			},
			error : function(ts) {
				//alert(ts.responseText)

			}

		});
	}
	function modalClose() {

		$('#myModal').hide();

	}
	function onload() {
		$('body,html').animate({scrollTop: 400}, 1300); 
		// $("#Standings").focus();

	}
</script>
</head>
<body onload="onload()">
		<jsp:directive.include file="Header.jsp" />
  <div class="main-content">
<jsp:directive.include file="NavBar.jsp" />
	
	<div class="se-pre-con"></div>
	<br>
		<div class="content-wrapper">
			<div class="container-fluid">
				<ol class="breadcrumb">
					<li class="breadcrumb-item"><a href="#" id="Standings">Standings</a></li>
					<li class="breadcrumb-item active">Game Week ${gameweek}</li>
				</ol>
				<div class="row">
					<div class="col-sm-12">
<div class="table-responsive">
						<table class="table" style="width: 100%; align: 'left'" id="info">
							<thead class="thead-dark">
								<tr align="center" class="courseRow">

									<th>League</th>
									<th>Team</th>
									<th>Points</th>
									<th>Position</th>
									<th>Status</th>
									<th></th>
									<th></th>
								</tr>
							</thead>
							<c:forEach items="${leagueStanding}" var="leagueStandingObj">

								<tr align="center" class="courseRow">
									<td>${leagueStandingObj.leagueName}</td>
									<td>${leagueStandingObj.teamName}</td>
									<td>${leagueStandingObj.playerNo}</td>
									<td>${leagueStandingObj.leagueType}</td>

									<c:if test="${leagueStandingObj.fixturesGeneratedYN=='N'}">

										<td>Inactive</td>

									</c:if>
									<c:if test="${leagueStandingObj.fixturesGeneratedYN=='Y'}">
										<td>Active</td>
									</c:if>
									<td>
									
											
								<a data-toggle="tooltip" title="Points Table" href="#" class="link" onClick="getStandingDetails('${leagueStandingObj.leagueId}')"><i class="fas fa-hashtag"></i></a>	
									
									</td>
									<td>
										
											<a href="#" data-toggle="tooltip" title="Fixtures & Results" class="link" onClick="getResultsFixtures('${leagueStandingObj.leagueId}')"><i class="fas fa-futbol"></i></a>	
									</td>
								</tr>

							</c:forEach>
							<%-- <form:hidden id ="joinLeagueId_" path="leagueId" value="${leagueList.leagueId}" /> --%>
						</table>
						</div>
					</div>
				</div>
				
<jsp:directive.include file="Footer.jsp" />

			</div>
		</div>
	
	</div>
	<!-- The Modal -->
	<div class="modal" id="myModal">
		<div class="modal-dialog">
			<div class="modal-content">

				<!-- Modal Header -->
				<div class="modal-header">
					Fixtures and Result
					<button type="button" onclick="modalClose()" class="close"
						data-dismiss="modal">&times;</button>
				</div>

				<!-- Modal body -->
				<div class="modal-body">
		<p id="demo"></p>
			
				</div>

				<!-- Modal footer -->
				<div class="modal-footer">
					<button type="button" onclick="modalClose()"
						class="btn btn-primary" data-dismiss="modal">Close</button>
				</div>

			</div>
		</div>
	</div>
	<div></div>
	
</body>
 
</html>
