<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Test Details Form</title>
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
				<h2 class="m-3 ">Test Details</h2>
			</div>
		</div>
		<div class="row">
			<div class="col">

				<form:form action="" method="post" class="needs-validation m-3"
					modelAttribute="test" novalidate="true">
					
					<div class="form-group">
						<form:label path="questionPaperLink"> Question Paper Link</form:label>
						<form:input path="questionPaperLink" class="form-control" type="url" required="true"/>	
						<div class="invalid-feedback">Please provide the link to the question paper</div>			
					</div>
					
					<div class="form-group">
						<form:label path="answerKeyLink"> Answer Key Link</form:label>
						<form:input path="answerKeyLink" class="form-control" type="url" required="true"/>	
						<div class="invalid-feedback">Please provide the link to the answer key</div>			
					</div>

					<div class="form-group">
						<form:label path="difficulty">Difficulty Rating</form:label>
						<form:input path="difficulty" class="form-control" type="number" min="1" max="10" required="true"/>
						<div class="invalid-feedback">Please provide a difficulty rating on scale of 10</div>
					</div>
					
					<div class="form-group">
						<form:label path="testDate">Date</form:label>
						<form:input path="testDate" class="form-control" type="date" required="true"/>
						<div class="invalid-feedback">Please provide the date of test</div>
					</div>
					
					<div class="form-group">
						<form:label path="testTime">Time</form:label>
						<form:input path="testTime" class="form-control" type="time" required="true"/>
						<div class="invalid-feedback">Please provide the time of test</div>
					</div>
					
					<div class="form-group">
						<form:label path="testDuration"> Duration (in hours)</form:label>
						<form:input path="testDuration" class="form-control" type="time" required="true"/>	
						<div class="invalid-feedback">Please provide the duration of the test</div>			
					</div>	
					
					<div class="form-group">
						<form:label path="maximumMarks">Maximum Marks</form:label>
						<form:input path="maximumMarks" class="form-control" type="number" min="1" required="true"/>
						<div class="invalid-feedback">Please provide the maximum marks</div>
					</div>
						
					<div class="form-group">
						<c:set var="testSeriesErrors"><form:errors path="testSeries.testSeriesID" /></c:set>
						<form:label path="testSeries.testSeriesID" >Prepares For:</form:label>
			            <form:select path="testSeries.testSeriesID" class="custom-select ${not empty testSeriesErrors ? 'is-invalid' : '' }" required="true">
			            	<option selected disabled value="">Choose...</option>
							<c:forEach var="testSeries" items="${allTestSeries}" varStatus="i">
								<form:option path="testSeries.testSeriesID" value="${testSeries.testSeriesID}" >${testSeries.testSeriesID}: ${testSeries.testSeriesName}</form:option>
							</c:forEach>
						</form:select>
						<div class="invalid-feedback">Please select the a valid Test Series</div>	
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