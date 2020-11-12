<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %> 
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

 
<!DOCTYPE html>
<html>
 <head>
      <meta charset="utf-8">
      <title>Test Series Details</title>
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
				<h2>Test Series Details</h2>
			</div>
		</div>
		<div class="row">
			<div
				class="col-10 offset-1 d-flex justify-content-center text-center">
				<table class="table table-hover table-bordered table-striped">
					<tr>
						<th scope="row">Test Series ID</th>
						<td>${testSeries.testSeriesID}</td>
					</tr>
					<tr>
						<th scope="row">Test Series Name</th>
						<td>${testSeries.testSeriesName}</td>
					</tr>
					<tr>
						<th scope="row">Level</th>
						<td>${testSeries.level}</td>
					</tr>
					<tr>
						<th scope="row">Mode</th>
						<td>${testSeries.mode}</td>
					</tr>
					<tr>
						<th scope="row">Associated with course</th>
						<td>${testSeries.course.courseName}</td>
					</tr>
					
					<c:set var="num" value="${fn:length(testSeries.testList)}"/>
					<c:forEach var="test" items="${testSeries.testList}" varStatus="stat">
						<tr>
							<c:if test="${ stat.count == 1 }">
								<th scope="row" rowspan="${num}">Tests</th>
	               			</c:if>
							<td><a href="${test.testNumber}/">Test Number:${test.testNumber} on <fmt:formatDate value="${test.testDate}" pattern="dd-MM-yyyy" /> at <fmt:formatDate value="${test.testTime}" pattern="HH:mm" /></a></td>
						</tr>
					</c:forEach>
				</table>	
			</div>
			
			<div class="col-lg-6 offset-lg-3 d-flex justify-content-around">

				<c:if test="${curRole eq 'admin'}">
					<div>
						<a class="btn btn-lg btn-success btn-block mb-3"
							href="/admin/update/testseries/${testSeries.testSeriesID}/">Edit Test Series</a>
					</div>
					<div>
						<a class="btn btn-lg btn-danger btn-block mb-3"
							href="/admin/delete/testseries/${testSeries.testSeriesID}/">Delete Test Series</a>
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