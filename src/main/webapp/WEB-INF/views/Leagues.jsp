<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="false"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<html>
<head>

<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.0/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.0/umd/popper.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.0/js/bootstrap.min.js"></script>


<title>DashBoard</title>

<script type="text/javascript">
function onLoadFunction() {
	
	
	if("${acknowldgement}" == "leagueCreated")
		{ 
		
		$('#myModal').show();
		 $("#modalBody_").text("League Created Succesfully");
		
		}
	else if ("${acknowldgement}" == "leagueCreationFailed")
		{
         $('#myModal').show();
         $("#modalBody_").text("League Creation failed");
		
		}
	else if ("${acknowldgement}" == "fixturesGeneratedS")
	{
     $('#myModal').show();
     $("#modalBody_").text("Fixtures generated succesfully");
	
	}
	else if ("${acknowldgement}" == "fixturesGeneratedF")
	{
     $('#myModal').show();
     $("#modalBody_").text("Fixtures generation Failed!, You do not have enough players in your league");
	
	}

	if (("${createdLeagueYN}" == "N")) {
		$('#createLeague_').show();
		$('#listLeagues_').hide();
		$("#dashCreateLink_").text("Create League");
		$('#createLeagueLink_').hide();
			}
	else if("${createdLeagueYN}" == "E")
		{
		
		$('#createLeague_').show();
		$('#listLeagues_').hide();
		$("#dashCreateLink_").text(document.getElementById('dashCreateLink_').innerHTML);
		$('#createLeagueLink_').hide();
		}
	else
		{
		$('#createLeague_').hide();
		$('#listLeagues_').show();
		$('#createLeagueLink_').show();
		$("#dashCreateLink_").text("View Leagues");
		
		}
		}
		
function createNewLeague() {
	
		$('#createLeague_').show();
		$('#listLeagues_').hide();
		$('#createLeagueLink_').hide();
		
		}
function viewLeagues() {
	
	$('#createLeague_').hide();
	$('#listLeagues_').show();
	$('#createLeagueLink_').show();
	
	}
	
	function generateFixture(leagurId,noPlayers)
			{
		
		
		document.getElementById("fixtureleagueId_").value = leagurId; 
		document.getElementById("fixtureplayerNo_").value = noPlayers; 
		document.getElementById("generateFixtures_").click();
		
		
			}
	
	function modalClose() {
		
		$('#myModal').hide();
		
		}
</script>


	<style>


</style>

</head>

<body onLoad=onLoadFunction()>


		<jsp:directive.include file="Header.jsp" />
  <div class="main-content">
<jsp:directive.include file="NavBar.jsp" />
	
	<div class="se-pre-con"></div>
	<div class="content-wrapper">
		<div class="container-fluid">
		
	
		


			<!-- create league -->
			<div class="card mb-3" id="createLeague_" width="100%" height="30">
				<div class="card-header">
					<i class="fa fa-play-circle-o"></i> Create League
				</div>
				<div class="card-body">
					<form:form action="processForm" method="POST"
						modelAttribute="leagueObj">
						<div class="row">
							<div class="col-sm-3">
								<form:label path="leagueName">League Name</form:label>
								<form:input path="leagueName" name="name" type="text"
									class="form-control" />
								<form:errors path="leagueName" cssClass="text-warning"></form:errors>

							</div>
							<div class="col-sm-3">
								<form:label path="leagueType">League Type</form:label>
								<form:select path="leagueType" name="club" class="form-control">

									<form:option value="" label=" Select" />
									<form:options items="${leagueTypeList}" />
								</form:select>
								<form:errors path="leagueType" cssClass="text-warning"></form:errors>

							</div>
							<div class="col-sm-3">
								<form:label path="playerNo">No of Players </form:label>
								<form:select path="playerNo" name="club" class="form-control">

									<form:option value="" label=" Select" />
									<form:options items="${playerNoList}" />
								</form:select>
								<form:errors path="playerNo" cssClass="text-warning"></form:errors>

							</div>
							<div class="col-sm-3">
								<form:label path="teamName">Team Name</form:label>
								<form:input path="teamName" name="name" type="text"
									class="form-control" />
								<form:errors path="teamName" cssClass="text-warning"></form:errors>
							</div>
						</div>
						<br>
						<div class="row">
							<div class="col-sm-5"></div>
							<div class="col-sm-4">
								<input type="submit" name="CreateLeague" value="Create"
									class="btn btn-primary">
							</div>
						</div>
					</form:form>
				</div>
			</div>
			<!-- existing created league -->
			<div class="card mb-3" id="listLeagues_">
				<div class="card-header">
					<i class="fa fa-list-ul"></i> My Leagues
				</div>
				<div class="card-body">
					<table style="width: 100%">
						<tr align="center" class="courseRow" id="leagueListTable_">
							<th>League Name</th>
							<th>League Type</th>
							<th>League Code</th>
							<th></th>
							<th></th>
						</tr>
						<c:forEach items="${leagueList}" var="leagueList">

							<tr align="center" class="courseRow">

								<td>${leagueList.leagueName}</td>
								<td>${leagueList.leagueType}</td>

								<td>${leagueList.leagueCode}</td>
								<form:form action="generateFixtures" method="POST"
									modelAttribute="leagueObj">
									<c:if test="${leagueList.fixturesGeneratedYN=='N'}">



										<td>
											<button type="button" class="btn btn-link"
												onClick="generateFixture('${leagueList.leagueId}','${leagueList.playerNo}')">Generate
												Fixtures</button>
											<button type="submit" class="btn btn-link"
												id="generateFixtures_" name="generateFixtures"
												value="generateFixtures"></button>
										</td>
									</c:if>
									<c:if test="${leagueList.fixturesGeneratedYN=='Y'}">
										<td  class="text-success"><font size="4">Fixtures Generated</font></td>
									</c:if>
									<!-- 	<td>
										<button type="submit" class="btn btn-link" name="deleteLeague"
											value="deleteLeague">Delete</button>

									</td> -->
									<form:hidden id="fixtureleagueId_" path="leagueId" />
									<form:hidden id="fixtureplayerNo_" path="playerNo" />
								</form:form>
							</tr>
						</c:forEach>
					</table>
					<br>
					<c:if test="${empty leagueList}">
						<div class="row">
							<div class="col-sm-5"></div>
							<div class="col-sm-4">

								<div class="alert alert-warning">
									<a href="#" class="close" data-dismiss="alert">&times;</a> <strong>Heyy!!!</strong>
									You gotta create leagues first
								</div>

							</div>
						</div>
					</c:if>
				</div>

			</div>
			<button id="createLeagueLink_" type="button" class="btn btn-info"
				name="createLeagueLink" Onclick="createNewLeague()">Create
				New League</button>
		<%-- 	<div class="row">
				<div class="col-sm-8">
					<!-- Example Bar Chart Card-->
					<div class="card mb-3">
						<div class="card-header">
							<i class="fa fa-bar-chart"></i> Bar Chart Example
						</div>
						<div class="card-body">
							<div class="row">
								<div class="col-sm-8 my-auto">
									<canvas id="myBarChart" width="100" height="50"></canvas>
								</div>
								<div class="col-sm-4 text-center my-auto">
									<div class="h4 mb-0 text-primary">$34,693</div>
									<div class="small text-muted">YTD Revenue</div>
									<hr>
									<div class="h4 mb-0 text-warning">$18,474</div>
									<div class="small text-muted">YTD Expenses</div>
									<hr>
									<div class="h4 mb-0 text-success">$16,219</div>
									<div class="small text-muted">YTD Margin</div>
								</div>
							</div>
						</div>
						<div class="card-footer small text-muted">Updated yesterday
							at 11:59 PM</div>
					</div>

					<!-- /Card Columns-->
				</div>
				<div class="col-sm-4">
					<!-- Example Pie Chart Card-->
					<div class="card mb-3">
						<div class="card-header">
							<i class="fa fa-pie-chart"></i> Premier League Table
						</div>
						<div class="card-body">
							<canvas id="myPieChart" width="100%" height="100"></canvas>
						</div>
						<div class="card-footer small text-muted">Updated yesterday
							at 11:59 PM</div>
					</div>

				</div>
			</div> --%>

		</div>
	</div>
	<div>

		<!-- The Modal -->
		<div class="modal" id="myModal">
			<div class="modal-dialog">
				<div class="modal-content">

					<!-- Modal Header -->
					<div class="modal-header">
						<h4 class="modal-title">Message</h4>
						<button type="button" onclick="modalClose()" class="close"
							data-dismiss="modal">&times;</button>
					</div>

					<!-- Modal body -->
					<div class="modal-body">
						<span id="modalBody_"></span>
					</div>

					<!-- Modal footer -->
					<div class="modal-footer">
						<button type="button" onclick="modalClose()"
							class="btn btn-primary" data-dismiss="modal">Close</button>
					</div>

				</div>
			</div>
		</div>

	</div>




	<jsp:directive.include file="Footer.jsp" />
	</div>
</body>
</html>
