<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>  
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
 
<!DOCTYPE html>
<html>
 <head>
      <meta charset="utf-8">
      <title>Report Card</title>
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
				<h2>Report Card</h2>
			</div>
		</div>
		<div class="row">
		
		<fmt:formatDate var="date_to_compare" value="${test.testDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
		<c:set var="today" value="<%=new java.util.Date()%>"/>
		<fmt:formatDate var="today_formated_date" value="${today}" pattern="yyyy-MM-dd HH:mm:ss"/>  
			
			<div
			
				class="col-10 offset-1 text-center">
				<table class="table table-hover table-bordered table-striped">
					<tr> 
						<th scope="row"> Student ID </th>
						<td>${student.studentID}</td>
					</tr>			
					<tr>
						<th scope="row"> Student Name </th>
						<td>${student.user.firstName} ${student.user.middleName} ${student.user.lastName}</td>
					</tr>					
				</table>
				
				
			</div>
	      					
			<div
			
				class="col-10 offset-1 text-center">
				<table class="table table-hover table-bordered table-striped">
					
					<tr> 
						<th> Test Series ID </th>
						<th> Test Number </th>
						<th> Test Date </th>
						<th> Maximum Marks </th>
						<th> Marks Obtained </th>
					</tr>			
						<c:forEach var="attempt" items="${attemptTest}" varStatus="i">
							<tr>
								<td>${attempt.test.testSeries.testSeriesID}</td>
								<td>${attempt.test.testNumber}</td>
								<td><fmt:formatDate value="${attempt.test.testDate}" pattern="dd-MM-yyyy"/>
								<td>${attempt.test.maximumMarks}</td>
								<td>${attempt.marksObtained}</td>
							</tr>					
						</c:forEach>
				</table>
				
				
			</div>
		</div>
		<div class="row">
			<div class="col-xl-8 col-lg-10 offset-xl-2 offset-lg-1 d-flex justify-content-around">
		
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