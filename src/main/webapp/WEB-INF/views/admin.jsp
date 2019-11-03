<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!-- for spring security tags to work on jsp page add the following link -->
<%@ taglib uri="http://www.springframework.org/security/tags"
	prefix="security"%>
<%@ page session="false"%>
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
<script>
	function saveTeams() {
		var data = {}
		data["query"] = 'sad';

		$
				.ajax({
					type : "GET",
					url : "${pageContext.request.contextPath}/adminpanel/plteamdatasave",
					dataType : "json",
					data : {},
					success : function(data) {
						if (data) {
							modalShow("Teams Saved");
						} else {
							modalShow("Teams could not be  Saved");

						}
					}
				});
	}
	function lockGameweek() {
		var data = {}
		data["query"] = 'sad';

		$.ajax({
			type : "GET",
			url : "${pageContext.request.contextPath}/adminpanel/lockgameweek",
			dataType : "json",
			data : {},
			success : function(data) {
				if (data) {
					modalShow("GameWeek Locked");
				} else {
					modalShow("GameWeek could not be  Locked");

				}

			}
		});
	}
	function modalShow(msg) {

		$('#myModal').show();
		$("#modalBody_").text(msg);

	}

	function generateResults() {
		var data = {}
		data["query"] = 'sad';

		$
				.ajax({
					type : "GET",
					url : "${pageContext.request.contextPath}/adminpanel/generateresults",
					dataType : "json",
					data : {},
					success : function(data) {
						if (data) {
							modalShow("Results Generated ");
						} else {
							modalShow("Error in Generating Results");

						}
					}
				});
	}

	function gameWeekFixtures() {
		
		var data = {}
		data["query"] = 'sad';

		$
				.ajax({
					type : "GET",
					url : "${pageContext.request.contextPath}/adminpanel/gameWeekFixtures",
					dataType : "json",
					data : {},
					success : function(data) {
						if (data) {
							modalShow("Gameweek Fixtures Saved");
						} else {
							modalShow("Gameweek Fixtures could not be Saved");

						}
					}
				});
	}

	function gameWeekScores() {
		var data = {}
		data["query"] = 'sad';

		$
				.ajax({
					type : "GET",
					url : "${pageContext.request.contextPath}/adminpanel/getGameWeeScores",
					dataType : "json",
					data : {},
					success : function(data) {
						if (data) {
							modalShow("Scores Saved");
						} else {
							modalShow("Error in  Saving Scores");

						}
					}
				});
	}
	function modalClose() {

		$('#myModal').hide();

	}
</script>

<title>Admin Panel</title>


<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>



</head>
<body>
	<jsp:directive.include file="Header.jsp" />
	<div class="main-content">
		<jsp:directive.include file="NavBar.jsp" />
		<br>
		<div class="content-wrapper">
			<div class="container-fluid">
				<!-- Breadcrumbs-->
				<ol class="breadcrumb">
					<li class="breadcrumb-item"><a href="#">Dashboard</a></li>
					<li class="breadcrumb-item active">My Dashboard</li>
				</ol>
				<h1>Admin Panel</h1>

				<p id="msg"></p>
				<!-- Display username and role -->
				<%-- <p>User:<security:authentication property="principal.username"/></p>
<p>Role:<security:authentication property="principal.authorities"/></p> --%>

				<a href=# onClick="saveTeams()">fetch premier league
					teams/players </a><br> <br> <a href=#
					onClick="lockGameweek()">Lock the gameweek predictions</a><br>
				<br> <a href=# onClick="gameWeekFixtures()">Fetch premier
					league Gameweek Fixtures</a><br> <br> <a href=#
					onClick="gameWeekScores()">Fetch premier league gameweek
					results</a><br> <br> <a onClick="generateResults()" href=#>calculate
					results</a>

			</div>
		</div>
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
