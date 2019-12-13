
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="false"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>



<!DOCTYPE html>
<html lang="en">

<head>
<meta charset="utf-8" />
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<title>Send Prediction</title>
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.0/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.0/umd/popper.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.0/js/bootstrap.min.js"></script>


<script>
	var type;
	function twoGoals() {
		type = "2G";
		localStorage.setItem("type", type);
		$('#scorer_').hide();
		$('#scoreLine_').hide();
		document.getElementById('SelectclubScorer').value = '';
		document.getElementById('predictionScoreline').value = '';

	}
	function threeGoals() {
		type = "3G";
		localStorage.setItem("type", type);
		$('#scorer_').show();
		$('#scoreLine_').hide();

		document.getElementById('predictionScoreline').value = '';
	}
	function fiveGoals() {
		type = "5G";
		localStorage.setItem("type", type);
		$('#scorer_').show();
		$('#scoreLine_').show();
	}

	function populateOpponent(club) {
		$('#SelectOpponentList').empty();
		$('#SelectclubScorer').empty();
		var data = {}
		data["query"] = 'sad';

		$.ajax({
			type : "GET",
			url : "${pageContext.request.contextPath}/getOpponentList",
			dataType : "json",
			data : {
				"id" : club
			},
			success : function(data) {
				var SelectOpponentList = $('#SelectOpponentList'), option = "";
				SelectOpponentList.empty();
				
				for (i in data) {

					option = option + "<option value='"+[i]+ "'>" + data[i]
							+ "</option>";
				}
				SelectOpponentList.append(option)
				if (document.getElementById("SelectOpponentList").length != 1) {
					document.getElementById('SelectOpponentList').value = '';
				}

				//
				populateScorer(club);
			}
		});

	}

	function populateScorer(club) {
		var data = {}
		data["query"] = 'sad';

		$.ajax({
			type : "GET",
			url : "${pageContext.request.contextPath}/getScorerList",
			dataType : "json",
			data : {
				"id" : club
			},
			success : function(data) {
				var SlctclubScorer = $('#SelectclubScorer'), option = "";
				SlctclubScorer.empty();
				for (i in data) {

					option = option + "<option value='"+[i]+ "'>" + data[i]
							+ "</option>";
				}
				SlctclubScorer.append(option)

				document.getElementById('SelectclubScorer').value = '';
			}
		});
	}
	function sendPrediction(tName, leagueId, teamId) {

		$('#dividerLine_').show();
		document.getElementById('teamTitle_').innerHTML = "Send Prediction for "
				+ tName;

		$('#predictionContainer_').show();
		$('#2G_').focus();
		document.getElementById("predictionTeamId_").value = teamId;
		document.getElementById("predictionLeagueId_").value = leagueId;
		document.getElementById("predictionteamName_").value = tName;

		document.getElementById('teamTitle_').innerHTML = "Send Prediction for "
				+ document.getElementById("predictionteamName_").value;

	}
	function Onload() {
		$('body,html').animate({
			scrollTop : 400
		}, 1300);

		type = localStorage.getItem("type");

		if ("${acknowldgement}" == "predictionSuccess") {

			$('#myModal').show();
			$("#modalBody_").text("Prediction Sent Successfully");

		} else if ("${acknowldgement}" == "predictionFailure") {
			$('#myModal').show();
			$("#modalBody_").text("Prediction Sending Failed");

		} else if ("${acknowldgement}" == "noLeagues") {
			$('#myModal').show();
			$("#modalBody_")
					.text(
							"No Leagues Found, Check if you have joined any leagues or fixtures have been generated for any of your leauges");

		}

		if ("${hasErrors}" == "Y") {
			$('#predictionContainer_').show();
			$('#dividerLine_').show();

			if (type == "3G") {
				$('#scoreLine_').hide();
			}
			if (type == "2G") {
				$('#scoreLine_').hide();
				$('#scorer_').hide();
			}
			document.getElementById("predictionTeamId_").value = teamId;
			twoGoals()

			document.getElementById('teamTitle_').innerHTML = "Send Prediction for "
					+ document.getElementById("predictionteamName_").value;
		} else {
			$('#predictionContainer_').hide();
			$('#dividerLine_').hide();
		}
		//document.getElementById('header').innerHTML = "Send Prediction for Game Week "+${gameweek};	
	}
	function modalClose() {

		$('#myModal').hide();

	}
	function rulesModalOpen() {

		$('#myModal').show();
		//$("#modalBody_").append("<h3>Rules of  The Predictor League</h3>			<p><pre>All the teams will be drawn against each other by randomly		created fixtures and their match outcomes will be decided by the number of goals they score. 		There are 5 types of predictions involving increasing amount of risk and hence riskier the prediction,		the more goals you’ll score for being accurate. Wrong prediction of any type gives you 0 goals<pre></p>				");

	}
</script>


</head>

<body onload="Onload()">

	<jsp:directive.include file="Header.jsp" />
	<div class="main-content">
		<jsp:directive.include file="NavBar.jsp" />


		<br>

		<div class="content-wrapper">
			<div class="container-fluid">
				<ol class="breadcrumb">
					<li class="breadcrumb-item" id="SendPredictions"><a href="#">Send
							Predictions</a></li>
					<li class="breadcrumb-item active">Game Week ${gameweek}</li>
				</ol>
				<div class="table-responsive">
					<!-- Projects table -->
					<table class="table align-items-center table-flush">
						<thead class="thead-dark">
							<tr align="center" class="courseRow">
								<th></th>
								<th>Team Name</th>
								<th>League Name</th>
								<th>PredictedType</th>
								<th>Predicted Team</th>
								<th>Predicted Opponent</th>
								<th>Predicted Scorer</th>
								<th>Predicted Scoreline</th>

							</tr>
						</thead>
						<c:forEach items="${leaguePredictList}" var="leagueList">

							<tr align="center" class="courseRow">
								<c:if test="${predicionLock=='N'}">

									<c:if test="${leagueList.predictionSent=='N'}">
										<td>
											<button type="button" class="btn btn-link"
												onClick="sendPrediction('${leagueList.teamName}','${leagueList.leagueId}','${leagueList.teamId}')">Send
												Prediction</button>
										</td>
									</c:if>


									<c:if test="${leagueList.predictionSent=='Y'}">
										<td>
											<button type="button" class="btn btn-link"
												onClick="sendPrediction('${leagueList.teamName}','${leagueList.leagueId}','${leagueList.teamId}')">Change
												Prediction</button>
										</td>
									</c:if>
								</c:if>
								<c:if test="${predicionLock=='Y'}">
									<c:if test="${leagueList.predictionSent=='N'}">
										<td>Failed to send prediction</td>
									</c:if>
									<c:if test="${leagueList.predictionSent=='Y'}">
										<td>Prediction Sent</td>
									</c:if>

								</c:if>
								<td>${leagueList.teamName}</td>
								<td>${leagueList.leagueName}</td>
								<td>${leagueList.predictionType}</td>
								<td>${leagueList.predictionTeam}</td>
								<td>${leagueList.opponent}</td>
								<td>${leagueList.predictionScorer}</td>
								<td>${leagueList.predictionScoreline}</td>



							</tr>

						</c:forEach>
					</table>
				</div>
				<hr id="dividerLine_" width="50%"
					style="height: 3px; border: none; color: #00FFFF; background-color: #00FFFF;" />
				<br> <b id="teamTitle_"> </b>
				<form:form action="SendPrediction" method="POST"
					modelAttribute="predictObj" id="predictionContainer_">
					<div class="row">
						<div class="col-sm-4">
							<label>Prediction Type</label><br>
							<form:radiobutton path="predictionType" value="2G" label="2 Goal"
								id="2G_" onClick="twoGoals()" />
							<form:radiobutton path="predictionType" value="3G" label="3 Goal"
								onClick="threeGoals()" />
							<form:radiobutton path="predictionType" value="5G" label="5 Goal"
								onClick="fiveGoals()" />
							<form:errors path="predictionType" cssClass="text-warning"></form:errors>

						</div>
						<!-- <div class="col-sm-6">
					 <a href=# onClick="rulesModalOpen()"><i class="fa fa-info-circle"></i></a> 
					</div> -->
					</div>
					<div class="row">
						<div class="col-sm-3">
							<label>Team</label>
							<form:select path="predictionTeam" class="form-control"
								onChange="populateOpponent(this.value)">
								<form:option value="" label=" Select" />
								<form:options items="${clubList}" />
							</form:select>
							<form:errors path="predictionTeam" cssClass="text-warning"></form:errors>
						</div>

						<div class="col-sm-3">
							<label>Opponent</label>
							<form:select path="opponent" class="form-control"
								id="SelectOpponentList">

								<form:options items="${opponentList}" />
							</form:select>
							<form:errors path="predictionScorer" cssClass="text-warning"></form:errors>
						</div>

						<div id="scorer_" class="col-sm-3">
							<label>Scorer</label>
							<form:select path="predictionScorer" class="form-control"
								id="SelectclubScorer">

								<form:options items="${clubScorer}" />
							</form:select>
							<form:errors path="predictionScorer" cssClass="text-warning"></form:errors>
						</div>
						<div id="scoreLine_" class="col-sm-3">
							<label>Score-Line</label>
							<form:input path="predictionScoreline" name="Score Line"
								type="text" class="form-control" />
							<form:errors path="predictionScoreline" cssClass="text-warning"></form:errors>
						</div>
						<form:hidden id="predictionLeagueId_" path="leagueId" />
						<form:hidden id="predictionTeamId_" path="teamId" />
						<form:hidden id="predictionteamName_" path="teamName" />

					</div>
					<br>
					<input style="align: center" type="submit" name="Send" value="Send"
						class="btn btn-primary">
				</form:form>
			</div>
		</div>


		<jsp:directive.include file="Footer.jsp" />
	</div>


	<!-- The Modal -->
	<div class="modal" id="myModal">
		<div class="modal-dialog">
			<div class="modal-content">

				<!-- Modal Header -->
				<div class="p-2 bg-warning">

					<a onclick="modalClose()"><i class="fa fa-window-close"
						aria-hidden="true"></i></a>
				</div>

				<!-- Modal body -->
				<div class="modal-body">
					<p style="align: center" id="modalBody_"></p>

				</div>



			</div>
		</div>
	</div>






</body>

</html>