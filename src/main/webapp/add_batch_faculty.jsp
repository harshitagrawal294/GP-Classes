<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Batch Faculty Form</title>
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

	<c:if test = "${not empty facultyTeachingBatch}">
		<div class="container my-5">
			<div class="container my-auto align-self-middle">
		
				<div class="row">
					<div class="col-8 offset-2 d-flex justify-content-center text-center">
						<h2>Faculty Teaching Batch</h2>
					</div>
				</div>
				<div class="row">
					<div
						class="col-10 offset-1 d-flex justify-content-center text-center">
						<table class="table table-hover table-bordered table-striped">
							<c:forEach var="faculty" items="${facultyTeachingBatch}" varStatus="i">
								<tr>
									<th scope="row">${faculty.facultyID}-${faculty.employee.user.firstName} ${faculty.employee.user.middleName} ${faculty.employee.user.lastName}</th>
									<td> <a href="${faculty.facultyID}/">Remove</a></td>
								</tr>						
							</c:forEach>
						</table>	
					</div>
						
				</div>
			</div>
		</div>
	</c:if>
	
	<c:if test = "${not empty otherFaculty}">
		<div class="container align-self-center col-lg-4 border border-info my-5">
			<div class="row">
				<div class="col-lg-8 offset-lg-2 d-flex justify-content-center text-center border-bottom border-info">
					<h2 class="m-3 ">Add Faculty</h2>
				</div>
			</div>
			<div class="row">
				<div class="col">
					
					<form:form action="" method="post" class="needs-validation m-3"
						modelAttribute="teachesBatch" novalidate="true">
			
						<div class="form-group">
				            <form:select path="faculty.facultyID" class="custom-select" required="true">
				            	<option selected disabled value="">Choose...</option>
								<c:forEach var="faculty" items="${otherFaculty}" varStatus="i">
									<form:option path="faculty.facultyID" value="${faculty.facultyID}" >${faculty.employee.user.username}-${faculty.facultyID}-${faculty.employee.user.firstName} ${faculty.employee.user.middleName} ${faculty.employee.user.lastName}</form:option>
								</c:forEach>
							</form:select>
							<div class="invalid-feedback">Please select a faculty</div>	
						</div>
						
	
						<div class="row">
							<div class="col-md-6 offset-md-3 text-center">
								<form:button class="btn btn-lg btn-info btn-block" type="submit">Add Faculty</form:button>
							</div>
						</div>
					</form:form>
				 
				</div>
			</div>
	
		</div>
	</c:if>
	
	<div class="row">
		<div class="col-sm-2 offset-sm-5 mb-5 text-center">
			<a class="btn btn-lg btn-success btn-block" href="/${role}/allbatch/${teachesBatch.batch.course.courseID}/${teachesBatch.batch.session}/${teachesBatch.batch.batchID}/">Done</a>
		</div>
	</div>
	
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