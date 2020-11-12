<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Test Series Details Form</title>
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css"
	integrity="sha384-TX8t27EcRE3e/ihU7zmQxVncDAy5uIKz4rEkgIXeMed4M0jlfIDPvg6uqKI2xXr2"
	crossorigin="anonymous">
<link
	href="https://fonts.googleapis.com/css2?family=Lato:ital@1&display=swap"
	rel="stylesheet">
<link rel="stylesheet" href="/css/main.css" type="text/css">
</head>
<body>

<div id="main-wrapper">

	<jsp:include page="navbar.jsp">
	        <jsp:param name="role" value="${role}"/>
	</jsp:include>

	<div class="container align-self-center col-lg-4 border border-info my-5">
		<div class="row">
			<div class="col-lg-8 offset-lg-2 d-flex justify-content-center text-center border-bottom border-info">
				<h2 class="m-3 ">Test Series Details</h2>
			</div>
		</div>
		<div class="row">
			<div class="col">
			

				<form:form action="" method="post" class="needs-validation m-3"
					modelAttribute="testSeries" novalidate="true">

					<div class="form-group">
						<form:label path="testSeriesName">Test Series Name</form:label>
						<form:input path="testSeriesName" class="form-control" maxlength="100" required="true"/>
						<div class="invalid-feedback">Please provide the name of the Test Series</div>
					</div>
					
					
					<div class="form-group">
						<form:label path="level"> Level</form:label>
						<form:input path="level" class="form-control" type="number" min="1" max="10" required="true"/>	
						<div class="invalid-feedback">Please provide the level on a scale of 1-10</div>			
					</div>
					
					<div class="form-group">
						<form:label path="mode" >Mode of tests:</form:label>
			            <form:select path="mode" class="custom-select" required="true">
			            	<option selected disabled value="">Choose...</option>
							<form:option path="mode" value="online" >Online</form:option>
							<form:option path="mode" value="offline" >Offline</form:option>
						</form:select>
						<div class="invalid-feedback">Please select the a valid mode</div>	
					</div>
					
		
					<div class="form-group">
						<c:set var="courseErrors"><form:errors path="course.courseID" /></c:set>
						<form:label path="course.courseID" >Associated with course:</form:label>
			            <form:select path="course.courseID" class="custom-select ${not empty courseErrors ? 'is-invalid' : '' }" required="true">
			            	<option selected disabled value="">Choose...</option>
							<c:forEach var="course" items="${allCourse}" varStatus="i">
								<form:option path="course.courseID" value="${course.courseID}" >${course.courseID}: ${course.courseName}</form:option>
							</c:forEach>
						</form:select>
						<div class="invalid-feedback">Please select the a valid course</div>	
					</div>
					

					<div class="row">
						<div class="col-md-6 offset-md-3 text-center">
							<form:button class="btn btn-lg btn-info btn-block bg-success" type="submit">Submit form</form:button>
						</div>
					</div>
				</form:form>
			</div>
		</div>

	</div>
	
	<jsp:include page="footer.jsp">
       <jsp:param name="role" value="${curRole}"/>
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