<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="false"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!--

=========================================================
* Argon Dashboard - v1.1.0
=========================================================

* Product Page: https://www.creative-tim.com/product/argon-dashboard
* Copyright 2019 Creative Tim (https://www.creative-tim.com)
* Licensed under MIT (https://github.com/creativetimofficial/argon-dashboard/blob/master/LICENSE.md)

* Coded by Creative Tim

=========================================================

* The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software. -->
<!DOCTYPE html>
<html lang="en">

<head>
  <meta charset="utf-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
  <title>
    Sign Up
  </title>
  <!-- Favicon -->
  <link href="../assets/img/brand/favicon.png" rel="icon" type="image/png">
  <!-- Fonts -->
  <link href="https://fonts.googleapis.com/css?family=Open+Sans:300,400,600,700" rel="stylesheet">
  <!-- Icons -->
  <link href="../assets/js/plugins/nucleo/css/nucleo.css" rel="stylesheet" />
  <link href="../assets/js/plugins/@fortawesome/fontawesome-free/css/all.min.css" rel="stylesheet" />
  <!-- CSS Files -->
  <link href="../assets/css/argon-dashboard.css?v=1.1.0" rel="stylesheet" />
  
  
  
  <script>
	function modalClose() {

		$('#myModal').hide();
		if ("${acknowldgement}" == "registeredSuccessfully") {
			window.location.href = '/tpl/showLoginPage';
		}
	}

	function onLoad() {
		if ("${acknowldgement}" == "registeredSuccessfully") {

			$('#myModal').show();
			$("#modalBody_").text(
					"Registered Successfully.Please Login to Continue");

		} else if ("${acknowldgement}" == "registrationFailed") {
			$('#myModal').show();
			$("#modalBody_").text("Registration Failed.Please try again");

		}
	}
</script>
</head>

<body onload="onLoad()" style="background-image: url('/images/bg-01.jpg');">
  <div class="main-content" >

    <!-- Header -->
  <br>
      <div class="container">
        <div class="header-body text-center mb-7">
          <div class="row justify-content-center">
            <div class="col-lg-5 col-md-6">
              <h1 class="text-white">The Predictor League</h1>
             
            </div>
          </div>
        </div>
      </div>
     <br>
    
    <!-- Page content -->
    <div class="container mt--8 pb-5">
      <!-- Table -->
      <div class="row justify-content-center">
        <div class="col-lg-6 col-md-8">
          <div class="card bg-secondary shadow border-0">
      <!--       <div class="card-header bg-transparent pb-5">
              <div class="text-muted text-center mt-2 mb-4"><small>Sign up with</small></div>
              <div class="text-center">
                <a href="#" class="btn btn-neutral btn-icon mr-4">
                  <span class="btn-inner--icon"><img src="../assets/img/icons/common/github.svg"></span>
                  <span class="btn-inner--text">Github</span>
                </a>
                <a href="#" class="btn btn-neutral btn-icon">
                  <span class="btn-inner--icon"><img src="../assets/img/icons/common/google.svg"></span>
                  <span class="btn-inner--text">Google</span>
                </a>
              </div>
            </div> -->
            <div class="card-body px-lg-5 py-lg-5">
              <div class="text-center text-muted mb-4">
                <small>Sign Up</small>
              </div>
              <form:form action="processregisterForm" method="POST"
			modelAttribute="login">
                <div class="form-group">
                  <div class="input-group input-group-alternative mb-3">
                    <div class="input-group-prepend">
                      <span class="input-group-text"><i class="ni ni-hat-3"></i></span>
                    </div>
                   
						<form:input path="fullName" name="name" type="text"
							class="form-control" placeholder="Name" />
						<form:errors path="fullName" cssClass="text-warning"></form:errors>
                  </div>
                </div>
                <div class="form-group">
                  <div class="input-group input-group-alternative mb-3">
                    <div class="input-group-prepend">
                      <span class="input-group-text"><i class="ni ni-email-83"></i></span>
                    </div>
                    
                    <form:input path="emailId" name="emailid" type="text"
							class="form-control" placeholder="Email" />
						<form:errors path="emailId" cssClass="text-warning"></form:errors>
                 
                  </div>
                </div>
                
                
                <div class="form-group">
                  <div class="input-group input-group-alternative">
                    <div class="input-group-prepend">
                      <span class="input-group-text"><i class="ni ni-tablet-button"></i></span>
                    </div>
                    
                    <form:input path="mobileNo" name="mobileno" type="text"
							class="form-control" placeholder="Mobile" />
						<form:errors path="mobileNo" cssClass="text-warning"></form:errors>
                    
                  
                  </div>
                </div>
                
                
                   <div class="form-group">
                  <div class="input-group input-group-alternative">
                    <div class="input-group-prepend">
                      <span class="input-group-text"><i class="ni ni-calendar-grid-58"></i></span>
                    </div>
                    
                    <form:input path="dob" name="dob" type="date" class="form-control" />
						<form:errors path="dob" cssClass="text-warning"></form:errors>
                    
                  
                  </div>
                </div>
                
                
                    <div class="form-group">
                  <div class="input-group input-group-alternative">
                    <div class="input-group-prepend">
                      <span class="input-group-text"><i class="ni ni-bold-down"></i></span>
                    </div>
                    
                    <form:select path="clubSupported" class="form-control">

							<form:option value="" label=" Select Club" />
							<form:options items="${clubList}" />
						</form:select>

						<form:errors path="clubSupported" cssClass="text-warning"></form:errors>
                    
                  
                  </div>
                </div>
                
                
                
                
                              <div class="form-group">
                  <div class="input-group input-group-alternative">
                    <div class="input-group-prepend">
                      <span class="input-group-text"><i class="ni ni-bold-down"></i></span>
                    </div>
                    
                  <form:select path="country" class="form-control">

							<form:option value="" label=" Select Country" />
							<form:options items="${countryList}" />
						</form:select>

						<form:errors path="country" cssClass="text-warning"></form:errors>
                    
                  
                  </div>
                </div>
                
                
                               <div class="form-group">
                  <div class="input-group input-group-alternative">
                    <div class="input-group-prepend">
                      <span class="input-group-text"><i class="ni ni-lock-circle-open"></i></span>
                    </div>
                    
                  <form:input path="password" name="pass" type="password"
							class="form-control" placeholder="Password"/>
						<form:errors path="password" cssClass="text-warning"></form:errors>
                    
                  
                  </div>
                </div>
                
                
                
                                     <div class="form-group">
                  <div class="input-group input-group-alternative">
                    <div class="input-group-prepend">
                      <span class="input-group-text"><i class="ni ni-lock-circle-open"></i></span>
                    </div>
                    
       <form:input path="confirmPassword" name="pass" type="password"
							class="form-control" placeholder="Confirm Password" />
						<form:errors path="confirmPassword" cssClass="text-warning"></form:errors>
                    
                  
                  </div>
                </div>
                
              <!--   <div class="row my-4">
                  <div class="col-12">
                    <div class="custom-control custom-control-alternative custom-checkbox">
                      <input class="custom-control-input" id="customCheckRegister" type="checkbox">
                      <label class="custom-control-label" for="customCheckRegister">
                        <span class="text-muted">I agree with the <a href="#!">Privacy Policy</a></span>
                      </label>
                    </div>
                  </div>
                </div> -->
                <div class="text-center">
                  <button type="Submit" class="btn btn-primary mt-4">Create account</button>
                </div>
              </form:form>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
  <!-- Footer -->
  <footer class="py-5">
    <div class="container">
      <div class="row align-items-center justify-content-xl-between">
        <div class="col-xl-6">
          <div class="text-white">
            &copy; 2019 <a href="#"  class="text-white" target="_blank">Rain Inc</a>
          </div>
        </div>
   <!--      <div class="col-xl-6">
          <ul class="nav nav-footer justify-content-center justify-content-xl-end">
            <li class="nav-item">
              <a href="https://www.creative-tim.com" class="nav-link" target="_blank">Creative Tim</a>
            </li>
            <li class="nav-item">
              <a href="https://www.creative-tim.com/presentation" class="nav-link" target="_blank">About Us</a>
            </li>
            <li class="nav-item">
              <a href="http://blog.creative-tim.com" class="nav-link" target="_blank">Blog</a>
            </li>
            <li class="nav-item">
              <a href="https://github.com/creativetimofficial/argon-dashboard/blob/master/LICENSE.md" class="nav-link" target="_blank">MIT License</a>
            </li>
          </ul>
        </div> -->
      </div>
    </div>
  </footer>
 
  <!--   Core   -->
  <script src="../assets/js/plugins/jquery/dist/jquery.min.js"></script>
  <script src="../assets/js/plugins/bootstrap/dist/js/bootstrap.bundle.min.js"></script>
  <!--   Optional JS   -->
  <!--   Argon JS   -->
  <script src="../assets/js/argon-dashboard.min.js?v=1.1.0"></script>
  <script src="https://cdn.trackjs.com/agent/v3/latest/t.js"></script>

</body>

</html>