<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Test Attempts Form</title>
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

	<c:if test = "${not empty attemptsAdded}">
		<div class="container my-5">
			<div class="container my-auto align-self-middle">
		
				<div class="row">
					<div class="col-8 offset-2 d-flex justify-content-center text-center">
						<h2>Students Added</h2>
					</div>
				</div>
				<div class="row">
					<div
						class="col-10 offset-1 d-flex justify-content-center text-center">
						<table class="table table-hover table-bordered table-striped">
						
		      				<tr>
		      					<th> Student ID </th>
		      					<th> Student Name </th>
		      					<th> Marks Obtained </th>
		      					<th> Rating Given </th>
		      					<th> Delete </th>
		      				</tr>	      				

							<c:forEach var="attempt" items="${attemptsAdded}" varStatus="i">
								<tr>
									<td>${attempt.student.studentID}</td>
									<td>${attempt.student.user.firstName} ${attempt.student.user.lastName} ${attempt.student.user.middleName}</td>
									<td>${attempt.marksObtained}/${attemptTest.test.maximumMarks}</td>
									<td>${attempt.ratingGiven}</td>
									<td> <a href="${attempt.student.studentID}/">Remove</a></td>
								</tr>					
							</c:forEach>
						</table>	
					</div>
						
				</div>
			</div>
		</div>
	</c:if>
	
	<c:if test = "${not empty allStudent}">
		<div class="container align-self-center col-lg-5 border border-info my-5">
			<div class="row">
				<div class="col-lg-8 offset-lg-2 d-flex justify-content-center text-center border-bottom border-info">
					<h2 class="m-3 ">Add Attempt</h2>
				</div>
			</div>
			<div class="row">
				<div class="col">
					
					<form:form action="" method="post" class="needs-validation m-3"
						modelAttribute="attemptTest" novalidate="true">
			
						<div class="form-group">
							<c:set var="studentErrors"><form:errors path="student.studentID" /></c:set>
				            <form:select path="student.studentID" class="custom-select ${not empty studentErrors ? 'is-invalid' : '' }" required="true">
				            	<option selected disabled value="">Choose...</option>
								<c:forEach var="stud" items="${allStudent}" varStatus="i">
									<form:option path="student.studentID" value="${stud.studentID}" > ${stud.studentID}</form:option>
								</c:forEach>
							</form:select>
							<div class="invalid-feedback">Please select a student</div>	
						</div>
						
						<div class="form-group">
							<form:label path="marksObtained">Marks Obtained</form:label>
							<form:input path="marksObtained" class="form-control" type="number" max="${attemptTest.test.maximumMarks}" required="true"/>
							<div class="invalid-feedback">Please enter the correct marks obtained by the student</div>
						</div>
						<div class="form-group">
							<form:label path="ratingGiven">Rating Given</form:label>
							<form:input path="ratingGiven" class="form-control" type="number" min="1" max="10" required="true"/>
							<div class="invalid-feedback">Please enter a rating on scale of 10</div>
						</div>
						
						
	
						<div class="row">
							<div class="col-md-6 offset-md-3 text-center">
								<form:button class="btn btn-lg btn-info btn-block" type="submit">Add</form:button>
							</div>
						</div>
					</form:form>
				 
				</div>
			</div>
	
		</div>
	</c:if>
	
	<div class="row">
		<div class="col-sm-2 offset-sm-5 mb-5 text-center">
			<a class="btn btn-lg btn-success btn-block" href="/${role}/alltestseries/${attemptTest.test.testSeries.testSeriesID}/${attemptTest.test.testNumber}/">Done</a>
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