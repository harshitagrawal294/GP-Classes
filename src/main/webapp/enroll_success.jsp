<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %> 
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
 
<!DOCTYPE html>
<html>
 <head>
      <meta charset="utf-8">
      <title>Transaction Successful</title>
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
        <jsp:param name="role" value="student"/>
</jsp:include>

<c:set var="enroll" value="${sucessEnroll.getFirst()}"/>
<c:set var="transaction" value="${sucessEnroll.getSecond()}"/>

<div class="container my-5">
	<div class="container my-auto align-self-middle">

		<div class="row">
			<div class="col-8 offset-2 d-flex justify-content-center text-center">
				<h2>Transaction Successful</h2>
			</div>
		</div>
		<div class="row">
			<div
				class="col-10 offset-1 d-flex justify-content-center text-center">
				<table class="table table-hover table-bordered table-striped">
					<tr>
						<th scope="row">Enrollment Number</th>
						<td>${enroll.enrollmentNumber}</td>
					</tr>
					<tr>
						<th scope="row">Transaction ID</th>
						<td>${enroll.transactionID}</td>
					</tr>
					<tr>
						<th scope="row">Student ID</th>
						<td>${enroll.studentID}</td>
					</tr>
					<tr>
						<th scope="row">Amount</th>
						<td>Rs. ${transaction.amount} /- only</td>
					</tr>
					<tr>
						<th scope="row">Mode</th>
						<td>${transaction.mode}</td>
					</tr>
					<tr>
						<th scope="row">Date</th>
						<td><fmt:formatDate value="${transaction.transactionDate}" pattern="dd-MM-yyyy" /></td>
					</tr>
					<tr>
						<th scope="row">Time</th>
						<td><fmt:formatDate value="${transaction.transactionTime}" pattern="HH:mm:ss" /></td>
					</tr>
					
				</table>	
			</div>
			
			<div class="col-lg-6 offset-lg-3 d-flex justify-content-around">
				<a class="btn btn-lg btn-success btn-block mb-3" href="/student/enrolled/course/">View All Enrolled Batches</a>
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