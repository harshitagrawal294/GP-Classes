<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>GP Coaching</title>
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css"
	integrity="sha384-TX8t27EcRE3e/ihU7zmQxVncDAy5uIKz4rEkgIXeMed4M0jlfIDPvg6uqKI2xXr2"
	crossorigin="anonymous">
<link
	href="https://fonts.googleapis.com/css2?family=Lato:ital@1&display=swap"
	rel="stylesheet">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">


<link rel="stylesheet" href="/css/main.css" type="text/css">
</head>
<body>

<div id="main-wrapper">

<jsp:include page="navbar.jsp">
        <jsp:param name="role" value="${role}"/>
</jsp:include>

		<header>
          
            <div class="hero-text-box ml-5 mt-5">
            
                <h3 class="text-white row ml-5 mb-3">
                 	WHY GO FOR THE REST <br> WHEN YOU CAN GET TRAINED BY THE BEST?
                </h3>
                <a href="/allcourse/" class="btn btn-full ml-5">View Courses</a>
                <a href="/allfaculty/" class="btn btn-ghost ml-2">Meet Our Faculty</a>
            </div>
            
        
        </header>
        
        <section id="features" class="section-features">
        
            <div class="row d-block mr-0" class="align-self-center">
                <h2 class="col-md-6 offset-3 mt-5"> Quick Links &mdash; Explore</h2>
            
                <table class="table table-borderless d-flex col-md-6 offset-4 pl-5">
                	<tbody class="pl-5 ml-4">
						<tr>
							<td>
								<i class="fa fa-user-plus fa-3x mr-3" aria-hidden="true"></i>
							</td>
							<td>
								<span><a href="/register/student/" class=""><h4 class="d-inline">Student Registration</h4></a></span>
							</td>
						</tr>
						<sec:authorize var="loggedIn" access="isAuthenticated()" />
						<tr>
							<td>
								<i class="fa fa-weixin fa-3x mr-3" aria-hidden="true"></i>
							</td>
							<td>
								<span><a href="/contactus/" class=""><h4 class="d-inline">Send us message</h4></a></span>
							</td>
						</tr>
						<tr>
							<td>
								<i class="fa fa-pencil fa-3x mr-3" aria-hidden="true"></i>
							</td>
							<td>
								<span><a href="/send/application/" class=""><h4 class="d-inline">Become a faculty</h4></a></span>
							</td>
						</tr>
						<tr>
							<td>
								<i class="fa fa-quote-left fa-3x mr-3" aria-hidden="true"></i>
							</td>
							<td>
								<span><a href="/allalumnus/" class=""><h4 class="d-inline">Testimonials</h4></a></span>
							</td>
						</tr>								
						<tr>
							<td>
								<i class="fa fa-question fa-3x mr-3" aria-hidden="true"></i>
							</td>
							<td>
								<span><a href="/allfaq/" class=""><h4 class="d-inline">Frequently Asked Questions</h4></a></span>
							</td>
						</tr>
					</tbody>
				</table>
                
             </div>        
        </section>
	
	<jsp:include page="footer.jsp">
        <jsp:param name="role" value="${role}"/>
	</jsp:include>
	
</div>
		
			<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"
				integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj"
				crossorigin="anonymous"></script>
			<script
				src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/js/bootstrap.bundle.min.js"
				integrity="sha384-ho+j7jyWK8fNQe+A12Hb8AhRq26LrZ/JpcUGGOn+Y7RsweNrtN/tE3MoK7ZeZDyx"
				crossorigin="anonymous"></script>
			<script src="/js/main.js"></script>
</body>
</html>