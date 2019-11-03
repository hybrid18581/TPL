<%@ taglib uri="http://www.springframework.org/security/tags"
	prefix="security"%>
  <body>
    <!-- Navbar -->
    <nav class="navbar navbar-top navbar-expand-md navbar-dark" id="navbar-main">
      <div class="container-fluid">
        <!-- Brand -->
        <a class="h4 mb-0 text-white text-uppercase d-none d-lg-inline-block" href="${pageContext.request.contextPath}/Dashboard/showForm">DashBoard</a>
        <!-- Form -->
      <!--   <form class="navbar-search navbar-search-dark form-inline mr-3 d-none d-md-flex ml-lg-auto">
          <div class="form-group mb-0">
            <div class="input-group input-group-alternative">
              <div class="input-group-prepend">
                <span class="input-group-text"><i class="fas fa-search"></i></span>
              </div>
              <input class="form-control" placeholder="Search" type="text">
            </div>
          </div>
        </form> -->
        <!-- User -->
        <ul class="navbar-nav align-items-center d-none d-md-flex">
          <li class="nav-item dropdown">
            <a class="nav-link pr-0" href="#" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
              <div class="media align-items-center">
                <span class="avatar avatar-sm rounded-circle">
                  <img alt="Image placeholder" src="${pageContext.request.contextPath}/assets/img/theme/user.png">
                </span>
                <div class="media-body ml-2 d-none d-lg-block">
                  <span class="mb-0 text-sm  font-weight-bold">User</span>
                </div>
              </div>
            </a>
            <div class="dropdown-menu dropdown-menu-arrow dropdown-menu-right">
              <div class=" dropdown-header noti-title">
                <h6 class="text-overflow m-0">Welcome!</h6>
              </div>
           <!--    <a href="./examples/profile.html" class="dropdown-item">
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
      </div>
    </nav>
      <!-- End Navbar -->
    <!-- Header -->
    
    <div class="header  pb-8 pt-5 pt-md-8" style="background-image: url('${pageContext.request.contextPath}/images/bg-01.jpg');">
      <div class="container-fluid">
        <div class="header-body">
          <!-- Card stats -->
          <security:authorize access="hasRole('player')">
          <div class="row">
            <div class="col-xl-3 col-lg-6">
              <div class="card card-stats mb-4 mb-xl-0">
                <div class="card-body">
                  <div class="row">
                    <div class="col">
                      <h5 class="card-title text-uppercase text-muted mb-0">Leagues</h5>
                      <span class="h2 font-weight-bold mb-0"></span>
                    </div>
                    <div class="col-auto">
                      <div class="icon icon-shape bg-danger text-white rounded-circle shadow">
                      <i class="ni ni-trophy"></i>
                      </div>
                    </div>
                  </div>
                  <p class="mt-3 mb-0 text-muted text-sm">
                    <!--  <span class="text-success mr-2"><i class="fa fa-arrow-up"></i> 3.48%</span> -->
                    <span class="text-nowrap">View Leagues</span> 
                  </p>
                </div>
              </div>
            </div>
            <div class="col-xl-3 col-lg-6">
              <div class="card card-stats mb-4 mb-xl-0">
                <div class="card-body">
                  <div class="row">
                    <div class="col">
                      <h5 class="card-title text-uppercase text-muted mb-0">Join Leagues</h5>
                      <span class="h2 font-weight-bold mb-0"></span>
                    </div>
                    <div class="col-auto">
                      <div class="icon icon-shape bg-warning text-white rounded-circle shadow">
                       <i class="fas fa-users"></i>  
                      </div>
                    </div>
                  </div>
                  
                 
                  
                  <p class="mt-3 mb-0 text-muted text-sm">
                   
                    <!-- <span class="text-danger mr-2"><i class="fas fa-arrow-down"></i> 3.48%</span> -->
                    <span class="text-nowrap"><a 
							href="${pageContext.request.contextPath}/joinLeagues">Join</a></span>
                  </p>
                </div>
              </div>
            </div>
            <div class="col-xl-3 col-lg-6">
              <div class="card card-stats mb-4 mb-xl-0">
                <div class="card-body">
                  <div class="row">
                    <div class="col">
                    
                      <h5 class="card-title text-uppercase text-muted mb-0">Predictions</h5>
                      <span class="h2 font-weight-bold mb-0"></span>
                    </div>
                    <div class="col-auto">
                      <div class="icon icon-shape bg-yellow text-white rounded-circle shadow">
                        <i class="fa fa-angle-right"></i>
                      </div>
                    </div>
                  </div>
                  <p class="mt-3 mb-0 text-muted text-sm">
                   <!--  <span class="text-warning mr-2"><i class="fas fa-arrow-down"></i></span> -->
                    <span class="text-nowrap"><a 
							href="${pageContext.request.contextPath}/SendPrediction">Send </a></span>
                  </p>
                </div>
              </div>
            </div>
            <div class="col-xl-3 col-lg-6">
              <div class="card card-stats mb-4 mb-xl-0">
                <div class="card-body">
                  <div class="row">
                    <div class="col">
                      <h5 class="card-title text-uppercase text-muted mb-0">View Standings</h5>
                      <span class="h2 font-weight-bold mb-0"></span>
                    </div>
                    <div class="col-auto">
                      <div class="icon icon-shape bg-info text-white rounded-circle shadow">
                       <i class="ni ni-tv-2"></i>
                      </div>
                    </div>
                  </div>
                  <p class="mt-3 mb-0 text-muted text-sm">
                <!--     <span class="text-success mr-2"><i class="fas fa-arrow-up"></i></span> -->
                    <span class="text-nowrap">	<a  href= "${pageContext.request.contextPath}/ViewStanding/getStanding" >View</a></span>
                  </p>
                </div>
              </div>
            </div>
          </div>
            </security:authorize>
          
          
        </div>
      </div>
    </div>
  
    </body>