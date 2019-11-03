
<%@ taglib uri="http://www.springframework.org/security/tags"
	prefix="security"%>
<head>
  <meta charset="utf-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
  <title>
   
  </title>
  <!-- Favicon -->
  <link href="${pageContext.request.contextPath}/assets/img/brand/favicon.png" rel="icon" type="image/png">
  <!-- Fonts -->
  <link href="https://fonts.googleapis.com/css?family=Open+Sans:300,400,600,700" rel="stylesheet">
  <!-- Icons -->
  <link href="${pageContext.request.contextPath}/assets/js/plugins/nucleo/css/nucleo.css" rel="stylesheet" />
  <link href="${pageContext.request.contextPath}/assets/js/plugins/@fortawesome/fontawesome-free/css/all.min.css" rel="stylesheet" />
  <!-- CSS Files -->
  <link href="${pageContext.request.contextPath}/assets/css/argon-dashboard.css?v=1.1.0" rel="stylesheet" />
  
<script type="text/JavaScript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.10.1/jquery.min.js" ></script>

  </head>


 <nav class="navbar navbar-vertical fixed-left navbar-expand-md navbar-light bg-white" id="sidenav-main">
    <div class="container-fluid">
      <!-- Toggler -->
      <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#sidenav-collapse-main" aria-controls="sidenav-main" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
      </button>
      <!-- Brand -->
      <a class="navbar-brand pt-0" href="">
     
        <img src="${pageContext.request.contextPath}/assets/img/brand/fox.jpg" class="navbar-brand-img" alt="...">
      </a>
      <!-- User -->
      <ul class="nav align-items-center d-md-none">
    <!--     <li class="nav-item dropdown">
          <a class="nav-link nav-link-icon" href="#" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
            <i class="ni ni-bell-55"></i>
          </a>
          <div class="dropdown-menu dropdown-menu-arrow dropdown-menu-right" aria-labelledby="navbar-default_dropdown_1">
            <a class="dropdown-item" href="#">Action</a>
            <a class="dropdown-item" href="#">Another action</a>
            <div class="dropdown-divider"></div>
            <a class="dropdown-item" href="#">Something else here</a>
          </div>
        </li> -->
        <li class="nav-item dropdown">
          <a class="nav-link" href="#" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
            <div class="media align-items-center">
              <span class="avatar avatar-sm rounded-circle">
                <img alt="Image placeholder"  src="${pageContext.request.contextPath}/assets/img/theme/user.png">
              </span>
            </div>
          </a>
          <div class="dropdown-menu dropdown-menu-arrow dropdown-menu-right">
            <div class=" dropdown-header noti-title">
              <h6 class="text-overflow m-0">Welcome!</h6>
            </div>
        <!--     <a href="./examples/profile.html" class="dropdown-item">
              <i class="ni ni-single-02"></i>
              <span>My profile</span>
            </a>
            <a href="./examples/profile.html" class="dropdown-item">
              <i class="ni ni-settings-gear-65"></i>
              <span>Settings</span>
            </a>
            <a href="./examples/profile.html" class="dropdown-item">
              <i class="ni ni-calendar-grid-58"></i>
              <span>Activity</span>
            </a>
            <a href="./examples/profile.html" class="dropdown-item">
              <i class="ni ni-support-16"></i>
              <span>Support</span>
            </a> -->
            <div class="dropdown-divider"></div>
            <a href="${pageContext.request.contextPath}/logout" class="dropdown-item">
              <i class="ni ni-user-run"></i>
              <span>Logout</span>
            </a>
          </div>
        </li>
      </ul>
      <!-- Collapse -->
      <div class="collapse navbar-collapse" id="sidenav-collapse-main">
        <!-- Collapse header -->
        <div class="navbar-collapse-header d-md-none">
          <div class="row">
            <div class="col-6 collapse-brand">
              <a href="">
                <img src="${pageContext.request.contextPath}/assets/img/brand/fox.jpg">
              </a>
            </div>
            <div class="col-6 collapse-close">
              <button type="button" class="navbar-toggler" data-toggle="collapse" data-target="#sidenav-collapse-main" aria-controls="sidenav-main" aria-expanded="false" aria-label="Toggle sidenav">
                <span></span>
                <span></span>
              </button>
            </div>
          </div>
        </div>
        <!-- Form -->
     <!--    <form class="mt-4 mb-3 d-md-none">
          <div class="input-group input-group-rounded input-group-merge">
            <input type="search" class="form-control form-control-rounded form-control-prepended" placeholder="Search" aria-label="Search">
            <div class="input-group-prepend">
              <div class="input-group-text">
                <span class="fa fa-search"></span>
              </div>
            </div>
          </div>
        </form> -->
        <!-- Navigation -->
        
        <security:authorize access="hasRole('player')">
        <ul class="navbar-nav">
          <li class="nav-item"  class=" active ">
          <a class=" nav-link active " href="${pageContext.request.contextPath}/Dashboard/showForm"> <i class="ni ni-world text-primary"></i> Dashboard
            </a>
          </li>
        <!--   <li class="nav-item">
            <a class="nav-link " href="./examples/icons.html">
              <i class="ni ni-planet text-blue"></i> Icons
            </a>
          </li> -->
          <li class="nav-item">
            <a class="nav-link " href="">
              <i class="ni ni-trophy"></i> Leagues
            </a>
          </li>
          <li class="nav-item">
            <a class="nav-link " href="">
              <i class="ni ni-single-02 text-yellow"></i> Profile 
            </a>
          </li>
          <li class="nav-item">
            <a class="nav-link " href="${pageContext.request.contextPath}/SendPrediction">
              <i class="ni ni-send"></i> Send Prediction
            </a>
          </li>
          <li class="nav-item">
            <a class="nav-link" href= "${pageContext.request.contextPath}/ViewStanding/getStanding">
              <i class="ni ni-tv-2"></i> View Standing
            </a>
          </li>
          
             <li class="nav-item">
            <a class="nav-link" href="${pageContext.request.contextPath}/joinLeagues">
              <i class="ni ni-badge"></i> Join Leagues
            </a>
          </li>
          
          
          <li class="nav-item">
            <a class="nav-link" href="${pageContext.request.contextPath}/rules">
              <i class="ni ni-circle-08 text-pink"></i> Rules
            </a> 
          </li>
        </ul>
        
        </security:authorize>
        <!-- Divider -->
        <hr class="my-3">
        <!-- Heading -->
       <!--  <h6 class="navbar-heading text-muted">Documentation</h6> -->
        <!-- Navigation -->
   <!--      <ul class="navbar-nav mb-md-3">
          <li class="nav-item">
            <a class="nav-link" href="https://demos.creative-tim.com/argon-dashboard/docs/getting-started/overview.html">
              <i class="ni ni-spaceship"></i> Getting started
            </a>
          </li>
          <li class="nav-item">
            <a class="nav-link" href="https://demos.creative-tim.com/argon-dashboard/docs/foundation/colors.html">
              <i class="ni ni-palette"></i> Foundation
            </a>
          </li>
          <li class="nav-item">
            <a class="nav-link" href="https://demos.creative-tim.com/argon-dashboard/docs/components/alerts.html">
              <i class="ni ni-ui-04"></i> Components
            </a>
          </li>
        </ul> -->
      </div>
    </div>
  </nav>