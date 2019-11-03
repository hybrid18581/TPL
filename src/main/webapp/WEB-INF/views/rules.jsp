<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="false"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>

<head>
    <meta charset='utf-8'>
    <meta http-equiv='X-UA-Compatible' content='IE=edge'>
    <title>Rules</title>
    <meta name='viewport' content='width=device-width, initial-scale=1'>
    <!-- <link rel='stylesheet' type='text/css' media='screen' href='main.css'>
    <script src='main.js'></script> -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
        integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">

<style>


/* Important part */
.modal-dialog{
    overflow-y: initial !important
}
.modal-body{
    height: 250px;
    overflow-y: auto;
}
</style>
</head>

<body>
	<jsp:directive.include file="Header.jsp" />
  <div class="main-content">
<jsp:directive.include file="NavBar.jsp" />
    <div class="container">
    <br>
    	<ol class="breadcrumb">
				<li class="breadcrumb-item" id="SendPredictions"><a href="#">Rules</a></li>
				<li class="breadcrumb-item active">The Constitution</li>
			</ol>
           <h1 style="font-size:medium">Rules of The Predictor League</h1>
        <p class="text-justify" style="font-size:x-small">
            All the teams will be drawn against each other by randomly created fixtures and their match outcomes will be
            decided by the number of goals they score. There are 5 types of predictions involving increasing amount of
            risk and hence riskier the prediction, the more goals you’ll score for being accurate. Wrong prediction of
            any type gives you 0 goals.
        </p>
        <h2 style="font-size:medium">Types of predictions and scoring</h2>
        <ol class="text-justify" style="font-size:x-small">
            <!-- <li> Any goal scorer in a match (1 goal)
                Accurately predicting any one goal scorer in a match of your choice will get you 1 goal.
                Example: Leicester City FC Vs Man City, Vardy goal.</li> -->
            <li>
                Match outcome (2 goals)
                Accurately predicting the outcome of a match of your choice will get you 2 goals.
               <strong> Example: Leicester City FC Vs Man City, Leicester City Win <!-- / Draw -->.</STRONG>
            </li>
            <li>Match outcome and any one goal scorer in a match. (3 goals)
                Accurately predicting the winning team AND any one goal scorer in a match will get you 3 goals.<br>
                <strong>Example: Leicester City FC Vs Man City, Leicester City Win AND Vardy goal.<!--  / Draw, Vardy goal. --></strong></li>
         <!--    <li>
                Correct scoreline of a match. (4 goals)
                Accurately predicting the scoreline of a match of your choice will give you 4 goals.
                Example: Leicester City FC Vs Man City, Leicester City FC 4-0 Man City.
            </li> -->
            <li>
               Winning team, Correct scoreline and a goal scorer in a match (5 goals)
                Accurately predicting the  Winning team, scoreline and  goal scorer in a match  will give you 5
                goals.
            </li>
           <strong>> Example: Leicester City FC Vs Man City, Leicester City FC 4-0 Man City, Vardy goal.</strong>
        </ol>

        <h2 style="font-size:medium">Head to head fixtures:</h2>
        <p class="text-justify" style="font-size:x-small">
            All the teams will be drawn against each other into randomly generated fixtures to be played every GW. The
            outcome of these fixtures will depend on the number of goals scored by the teams in each fixture.
        </p>
        <strong style="font-size:medium">Example</strong>
        <p class="text-justify" style="font-size:x-small"> <u>Fixture - Team A Vs Team B</u><br>
            Team A prediction : LCFC Vs Man City, LCFC Win. [Outcome of a match (2 goals)]<br>
            Team B prediction : Norwich Vs Man Utd, Norwich win, Pukki goal. [Outcome of a match AND any one goal scorer
            (3 goals)]<br>
            The actual premier league match results turn out to be:<br>
            LCFC 1-0 Man City, Vardy goal.<br>
            Norwich 2-1 Man Utd, Pukki, Mata, Cantwell goals.<br>
            So that makes the goals scored in the above TPL fixtures:<br>
            Team A 2 - 3 Team B
        </p>
        <p class="text-justify" style="font-size:x-small">
            Winning team in TPL fixture gets 3 points, losing team gets 0 points and a draw gives 1 point each.
            Total points and goal difference decide the position of teams on the league table.
        </p>
        
       <strong >
           The deadline for sending prediction is one hour before the start of the gameweek.</strong>
        
    </div>
    </div>
</body>

</html>