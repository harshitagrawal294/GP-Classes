<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %> 
 
<!DOCTYPE html>
<html>
 <head>
      <meta charset="utf-8">
      <title>Course Details</title>
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
				<h2>Course Details</h2>
			</div>
		</div>
		<div class="row">
			<div
				class="col-10 offset-1 d-flex justify-content-center text-center">
				<table class="table table-hover table-bordered table-striped">
					<tr>
						<th scope="row">Course ID</th>
						<td>${course.courseID}</td>
					</tr>
					<tr>
						<th scope="row">Course Name</th>
						<td>${course.courseName}</td>
					</tr>
					<tr>
						<th scope="row">Session started</th>
						<td>${course.introducedInSession}</td>
					</tr>
					<tr>
						<th scope="row">Description/Feature</th>
						<td>${course.promotionalFeature}</td>
					</tr>
					<tr>
						<th scope="row">Duration</th>
						<td>${course.duration} months</td>
					</tr>
					<tr>
						<th scope="row">Prepares for</th>
						<td>${course.exam.examName}</td>
					</tr>
					
					<c:set var="num" value="${fn:length(course.testSeries)}"/>
					<c:forEach var="testSeries" items="${course.testSeries}" varStatus="stat">
						<tr>
							<c:if test="${ stat.count == 1 }">
								<th scope="row" rowspan="${num}">Test Series</th>
	               			</c:if>
	               			<c:if test="${role ne 'student'}">
								<td><a href="/${role}/alltestseries/${testSeries.testSeriesID}/">${testSeries.testSeriesName}</a></td>
							</c:if>
							<c:if test="${role eq 'student'}">
								<td>${testSeries.testSeriesName}</td>
							</c:if>
						</tr>
					</c:forEach>
					<c:set var="num" value="${fn:length(course.batches)}"/>
					<c:forEach var="batch" items="${course.batches}" varStatus="stat">
               			<tr>
               				<c:if test="${ stat.count == 1 }">
								<th scope="row" rowspan="${num}">Open Batches</th>
	               			</c:if>
							<td><a href="/${role}/allbatch/${course.courseID}/${batch.session}/${batch.batchID}/">Batch #${batch.batchID}, Fees: Rs. ${batch.fees}</a></td>
						</tr>
					</c:forEach>
				</table>	
			</div>
			
			<div class="col-lg-6 offset-lg-3 d-flex justify-content-around">

				<c:if test="${role eq 'admin'}">
					<div>
						<a class="btn btn-lg btn-success btn-block mb-3"
							href="/admin/update/course/${course.courseID}/">Edit Course</a>
					</div>
					<div>
						<a class="btn btn-lg btn-danger btn-block mb-3"
							href="/admin/delete/course/${course.courseID}/">Delete Course</a>
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