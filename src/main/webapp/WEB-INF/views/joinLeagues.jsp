
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ page session="false"%>
<html>
<head>
<title>Join Leagues</title>
<style>
.highlight { background-color: papayawhip; }</style>
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.0/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.0/umd/popper.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.0/js/bootstrap.min.js"></script>
<script>
function modalClose() {
	
	$('#myModal').hide();
	
	}
	function onloadF() {
		
		$('body,html').animate({scrollTop: 400}, 1300); 
		$("#wmcjoinLeague_").hide();
		$("#wmcjoinLeagueButton_").hide();
		
		if("${acknowldgement}" == "alreadyjoined")
		{ 
		
		$('#myModal').show();
		$("#modalBody_").text("You have already joined this league");
		
		}
	else if ("${acknowldgement}" == "joinedsuccess")
		{
         $('#myModal').show();
         $("#modalBody_").text("You have successfully joined the league");
		
		}
	else if("${acknowldgement}" == "leaguefull")
		{
              $('#myModal').show();
		
              $("#modalBody_").text("League Full");
		}
	else if("${acknowldgement}" == "joinedfailed")
		{
		$('#myModal').show();
		
        $("#modalBody_").text("Error Occured, Could not join the league");
		}

	}
	function joinLeague(leagueID)
	{
		
		document.getElementById("joinLeagueId_").value = leagueID; 
		$("#wmcjoinLeague_").show();
		$("#wmcjoinLeagueButton_").show();
		
	}

</script>
</head>
<body onload=onloadF()>
			<jsp:directive.include file="Header.jsp" />
  <div class="main-content">
<jsp:directive.include file="NavBar.jsp" />
	
	<div class="se-pre-con"></div>
	<br>
		<div class="content-wrapper">
			<div class="container-fluid">
				<!-- Breadcrumbs-->
				<ol class="breadcrumb">
					<li class="breadcrumb-item" id="joinleaguesfocus"><a href="#">Join Leagues</a></li>
					<li class="breadcrumb-item active">Search</li>
				</ol>
				<form:form action="${pageContext.request.contextPath}/searchLeagues"
					method="POST" modelAttribute="leagueObj">
					<div class="row">
						<div class="col-sm-3">
							<form:radiobutton path="leagueType" value="Private"
								label="Private" />

							<form:radiobutton path="leagueType" value="Public" label="Public" />
							<form:errors path="leagueType" cssClass="text-warning"></form:errors>
						</div>
					</div>
					<div class="row">

						<div class="col-sm-3">
							<form:label path="leagueName" id="searchType">League Name/Code</form:label>
							<form:input path="leagueName" name="name" type="text"
								class="form-control" />
							<form:errors path="leagueName" cssClass="text-warning"></form:errors>
						</div>
						<div class="col-sm-3">
							<label> &nbsp;</label> <br> <input type="submit"
								name="searchLeague" value="Search" class="btn btn-primary">
								
						
						</div>
					</div>

					<br>
				</form:form>
				<c:if test="${acknowledgment=='noLeagueFound'}">
				<div class="alert alert-warning">
    <a href="#" class="close" data-dismiss="alert">&times;</a>
    <strong>Oops!!!</strong> Your search did not lead to any results
</div>
</c:if>
				<c:if test="${fn:length(leagueJoinList) gt 0}">
   

				<form:form action="${pageContext.request.contextPath}/searchLeagues"
					method="POST" modelAttribute="leagueObj">
					<div class="row">
						<div class="col-sm-12">
							<table class="table" style="width: 80%; align: 'left'" id="info">
							 <thead class="thead-dark">
								<tr align="left" class="courseRow">
									<th style="width: 100%">League Search</th>

									<th></th>
								</tr>
								</thead>
								<c:forEach items="${leagueJoinList}" var="leagueList">

									<tr align="left" class="courseRow">

										<td>${leagueList.leagueName}</td>



										<td>
											<button type="button" class="btn btn-primary"
												onClick="joinLeague('${leagueList.leagueId}')">Select</button>
										</td>
									</tr>

								</c:forEach>
								<form:hidden id="joinLeagueId_" path="leagueId"
									value="${leagueList.leagueId}" />
									
							</table>
							
						</div></div>
						
						<div class="row">
						
						<div id="wmcjoinLeague_" class="col-sm-6">
						<h5 >Join League</h5>
					
							<form:label path="teamName">Team Name </form:label>
							<form:input path="teamName" name="name" type="text"
								class="form-control" required="required" />
							<form:errors path="teamName" cssClass="text-warning"></form:errors>
							<br>
							<button type="submit" class="btn btn-success" name="joinLeagues" style="padding-left:25px;padding-right:25px"
								value="joinLeagues">Join</button>
						</div>
						<div id="wmcjoinLeagueButton_" class="col-sm-2">
							<br>
							<br>	<br>
							
						</div>
					</div>

				</form:form>
</c:if>
			</div>
		</div>
		<jsp:directive.include file="Footer.jsp" />
	</div>
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


</body>
</html>