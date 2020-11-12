<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %> 
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
 
<!DOCTYPE html>
<html>
 <head>
      <meta charset="utf-8">
      <title>Batch Details</title>
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

<div class="container my-5">
	<div class="container my-auto align-self-middle">

		<div class="row">
			<div class="col-8 offset-2 d-flex justify-content-center text-center">
				<h2>Batch Details</h2>
			</div>
		</div>
		<div class="row">
			<div
				class="col-10 offset-1 d-flex justify-content-center text-center">
				<table class="table table-hover table-bordered table-striped">
					<tr>
						<th scope="row">Batch ID</th>
						<td>${batch.batchID}</td>
					</tr>
					<tr>
						<th scope="row">Course Name</th>
						<td>${batch.course.courseName}</td>
					</tr>
					<tr>
						<th scope="row">Course ID</th>
						<td>${batch.course.courseID}</td>
					</tr>
					<tr>
						<th scope="row">Session</th>
						<td>${batch.session}</td>
					</tr>
					<tr>
						<th scope="row">Fees</th>
						<td>Rs. ${batch.fees} /- only</td>
					</tr>
					<tr>
						<th scope="row">capacity</th>
						<td>${batch.capacity} students</td>
					</tr>
									
					<c:set var="num" value="${fn:length(batch.faculties)}"/>
					<c:forEach var="faculty" items="${batch.faculties}" varStatus="stat">
						<tr>
							<c:if test="${ stat.count == 1 }">
								<th scope="row" rowspan="${num}">Faculties</th>
	               			</c:if>							
							<td>${faculty.facultyID}- ${faculty.employee.user.firstName} ${faculty.employee.user.middleName} ${faculty.employee.user.lastName}</td>							
						</tr>
					</c:forEach>
					
					<c:if test="${role ne 'student'}">
						<c:set var="num" value="${fn:length(allStudent)}"/>
						<tr><th colspan="2">Enrolled Students</th></tr>
						<tr>
							<th>Student ID</th>
							<th>Student Name</th>
						</tr>
						<c:forEach var="student" items="${allStudent}" varStatus="stat">
							<tr>
								<th scope="row" >${student.studentID }</th>					
								<td>${student.user.firstName} ${student.user.middleName} ${student.user.lastName}</td>							
							</tr>
						</c:forEach>
					</c:if>
					
				</table>	
			</div>
			
			<div class="col-lg-6 offset-lg-3 d-flex justify-content-around">

				<c:if test="${(role eq 'admin')||(role eq 'staff')}">
					<div>
						<a class="btn btn-lg btn-success btn-block mb-3"
							href="/${role}/update/batch/${batch.batchID}/${batch.course.courseID}/${batch.session}/">Edit Batch</a>
					</div>
					<div>
						<a class="btn btn-lg btn-danger btn-block mb-3"
							href="/${role}/delete/batch/${batch.batchID}/${batch.course.courseID}/${batch.session}/">Delete Batch</a>
					</div>
				</c:if>
				
				<c:if test="${(role eq 'student') && open }">
					<div>
						<a class="btn btn-lg btn-success btn-block mb-3"
							href="/student/enroll/${batch.course.courseID}/${batch.session}/${batch.batchID}/">Enroll now</a>
					</div>
				</c:if>
				
				<c:if test="${(role ne 'student') && open}">
					<div>
						<a class="btn btn-lg btn-success btn-block mb-3"
							href="/${curRole}/allbatch/${batch.course.courseID}/${batch.session}/${batch.batchID}/enroll/">Enroll Student</a>
					</div>
				</c:if>

			</div>
				
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