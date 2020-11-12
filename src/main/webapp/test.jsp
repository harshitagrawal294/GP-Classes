<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>  
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
 
<!DOCTYPE html>
<html>
 <head>
      <meta charset="utf-8">
      <title>Test Details</title>
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
				<h2>Test Details</h2>
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
						<th colspan="2" scope="row">Test Series ID</th>
						<td colspan="2">${test.testSeries.testSeriesID}</td>
					</tr>
					<tr>
						<th colspan="2" scope="row">Test Series Name</th>
						<td colspan="2">${test.testSeries.testSeriesName}</td>
					</tr>
					<tr>
						<th colspan="2" scope="row">Test Number</th>
						<td colspan="2">${test.testNumber}</td>
					</tr>
					<tr>
						<th colspan="2" scope="row">Difficulty</th>
						<td colspan="2">${test.difficulty}</td>
					</tr>					
					<tr>
						<th colspan="2" scope="row">Maximum Marks</th>
						<td colspan="2">${test.maximumMarks}</td>
					</tr>					
					<tr>
						<th colspan="2" scope="row">Test Date</th>
						<td colspan="2"><fmt:formatDate value="${test.testDate}" pattern="dd-MM-yyyy" /></td>
					</tr>
					<tr>
						<th colspan="2" scope="row">Test Time</th>
						<td colspan="2"><fmt:formatDate value="${test.testTime}" pattern="HH:mm" /></td>
					</tr>
					<tr>
						<th colspan="2" scope="row">Duration</th>
						<td colspan="2"><fmt:formatDate value="${test.testDuration}" pattern="HH:mm" /></td>
					</tr>
					
					<c:if test="${(curRole ne 'student')||(date_to_compare lt today_formated_date)}">
						<tr>
							<th colspan="2" scope="row">Question Paper</th>
							<td colspan="2"><a href="${test.questionPaperLink}">Download</a></td>
						</tr>
					</c:if>	
					<c:if test="${(curRole eq 'student')&&(not (date_to_compare lt today_formated_date))}">
						<tr>
							<th colspan="2" scope="row">Question Paper</th>
							<td colspan="2">Question Paper will be available after the test is conducted</td>
						</tr>
					</c:if>	
					
					<c:if test="${(curRole ne 'student')||(date_to_compare lt today_formated_date)}">
						<tr>
							<th colspan="2" scope="row">Answer Key</th>
							<td colspan="2"><a href="${test.answerKeyLink}">Download</a></td>
						</tr>
					</c:if>	
					<c:if test="${(curRole eq 'student')&&(not (date_to_compare lt today_formated_date))}">
						<tr>
							<th colspan="2" scope="row">Answer Key</th>
							<td colspan="2">Answer key will be available after the test is conducted</td>
						</tr>
					</c:if>	
					
					<c:if test="${(not (date_to_compare lt today_formated_date))}">
						<tr>
	      					<th scope="row" colspan="4"> Test has not been conducted yet</th>
	      				</tr>
					</c:if>	
					<c:if test="${((date_to_compare lt today_formated_date))}">
						
						<c:if test = "${empty attemptTest}">
		      				<tr>
		      					<th scope="row" colspan="4"> No one attempted this test </th>
		      				</tr>
		      			</c:if>
		      			<c:if test = "${not empty attemptTest}">
		      				<tr>
		      					<th scope="row" colspan="4"><b>Test Attempts</b></th>
		      				</tr>
		      				<tr>
		      					<th> Student ID </th>
		      					<th> Student Name </th>
		      					<th> Marks Obtained </th>
		      					<th> Rating Given </th>
		      				</tr>	      				
		      			</c:if>						
						<c:forEach var="attempt" items="${attemptTest}" varStatus="i">
							<tr>
								<td>${attempt.student.studentID}</td>
								<td>${attempt.student.user.firstName} ${attempt.student.user.lastName} ${attempt.student.user.middleName}</td>
								<td>${attempt.marksObtained}</td>
								<td>${attempt.ratingGiven}</td>
							</tr>					
						</c:forEach>
					</c:if>
				</table>
				
				
			</div>
		</div>
		<div class="row">
			<div class="col-xl-8 col-lg-10 offset-xl-2 offset-lg-1 d-flex justify-content-around">
		
				<c:if test="${(curRole eq 'admin') || (curRole eq 'staff') }">
					<a class="btn btn-lg btn-info btn-block  m-3"
						href="/${curRole}/update/test/${test.testSeries.testSeriesID}/${test.testNumber}/">Edit
						Test</a>
					<a class="btn btn-lg btn-info btn-block  m-3"
						href="/${curRole}/delete/test/${test.testSeries.testSeriesID}/${test.testNumber}/">Delete
						Test</a>
					<c:if test="${((date_to_compare lt today_formated_date))}">
						<a class="btn btn-lg btn-info btn-block  m-3"
							href="/${curRole}/add/testattempt/${test.testSeries.testSeriesID}/${test.testNumber}/">Edit
							Attempts</a>
					</c:if>
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