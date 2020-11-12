<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>  
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
 
<!DOCTYPE html>
<html>
 <head>
      <meta charset="utf-8">
      <title>Student Details</title>
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
				<h2>Student Details</h2>
			</div>
		</div>
		<div class="row">
			<div
				class="col-10 offset-1 text-center">
				<table class="table table-hover table-bordered table-striped">
					<tr>
						<th scope="row">Student Name</th>
						<td>${student.user.firstName} ${student.user.middleName} ${student.user.lastName}</td>
					</tr>
					<tr>
						<th scope="row">Student ID</th>
						<td>${student.studentID}</td>
					</tr>
					<tr>
						<th scope="row">Username</th>
						<td>${student.user.username}</td>
					</tr>
					<tr>
						<th scope="row">Adhaar Number</th>
						<td>${student.user.adhaarNumber}</td>
					</tr>
					<tr>
						<th scope="row">Gender</th>
						<td>${student.user.gender}</td>
					</tr>
					<tr>
						<th scope="row">Email ID</th>
						<td>${student.user.emailID}</td>
					</tr>
					
					<tr>
						<th scope="row">Street</th>
						<td>${student.user.street}</td>
					</tr>
					<tr>
						<th scope="row">City</th>
						<td>${student.user.city}</td>
					</tr>
					<tr>
						<th scope="row">State</th>
						<td>${student.user.state}</td>
					</tr>
					<tr>
						<th scope="row">Country</th>
						<td>${student.user.country}</td>
					</tr>
					<tr>
						<th scope="row">Phone</th>
						<td> ${student.user.phone}</td>	
				 
					</tr>
					<tr>
						<th scope="row">Birth Date</th>
						<td><fmt:formatDate value="${student.user.birthDate}" pattern="dd-MM-yyyy" /></td>
					</tr>
					<tr>
						<th scope="row">Parent Name</th>
						<td>${student.parentName}</td>
					</tr>
					<tr>
						<th scope="row">Parent Occupation</th>
						<td>${student.parentOccupation}</td>
					</tr>
					<tr>
						<th scope="row">10th Board</th>
						<td>${student.board10th}</td>
					</tr>
					<tr>
						<th scope="row">10th Percentage</th>
						<td>${student.percentage10th}</td>
					</tr>

					<tr>
						<th scope="row">12th Board</th>
						<td>${student.board12th}</td>
					</tr>
					<tr>
						<th scope="row">12th Percentage</th>
						<td>${student.percentage12th}</td>
					</tr>
					
					<tr>
						<th scope="row">Graduation College</th>
						<td>${student.collegeGrad}</td>
					</tr>
					<tr>
						<th scope="row">Graduation Percentage</th>
						<td>${student.percentageGrad}</td>
					</tr>
					
					<tr>
						<th scope="row">Post Graduation College</th>
						<td>${student.collegePGrad}</td>
					</tr>
					<tr>
						<th scope="row">Post-Graduation Percentage</th>
						<td>${student.percentagePGrad}</td>
					</tr>
					
					<tr>
						<th scope="row">Stream</th>
						<td>${student.stream}</td>
					</tr>
					<tr>
						<th scope="row">Achievement</th>
						<td>${student.achievement}</td>
					</tr>
					
					<tr>
						<c:if test="${role ne 'student'}">
							<th scope="row">Report Card</th>
							<td><a href="/${curRole}/student/report/${username}/">View</a></td>
						</c:if>
					</tr>
					
					<c:forEach var="work" items="${allWork}" varStatus="i">
						<tr>
							<th scope="row" colspan="2">Work Experience #${i.count}</th>
						</tr>
						<tr>
							<th scope="row">Company</th>
							<td>${work.company}</td>
						</tr>
						<tr>
							<th scope="row">Work Role</th>
							<td>${work.workRole}</td>
						</tr>
						<tr>
							<th scope="row">Work Description</th>
							<td>${work.workDescription}</td>
						</tr>
						<tr>
							<th scope="row">Work Joining Date</th>
							<td><fmt:formatDate value="${work.workJoiningDate}" pattern="dd-MM-yyyy" /></td>
						</tr>
						<tr>
							<th scope="row">Work Leaving Date</th>
							<td>
								<c:if test="${empty work.workLeavingDate}">
									Currently working here
								</c:if>
								<c:if test="${not empty work.workLeavingDate}">
									<fmt:formatDate value="${work.workLeavingDate}" pattern="dd-MM-yyyy" />
								</c:if>	
							</td>
						</tr>
					
					</c:forEach>
		
				</table>
				
			</div>
			
			<div class="col-lg-8 offset-lg-2 d-flex justify-content-around">

				<c:if test="${role eq 'admin'}">
					<A class="btn btn-lg btn-info btn-inline  bg-info m-3"
						href="/admin/student/update/${student.user.username}/">Edit
						Details</A>
					<A class="btn btn-lg btn-info btn-inline  bg-success m-3"
						href="/admin/student/add/work/${student.user.username}/">Edit
						Work</A>
					<A class="btn btn-lg btn-info btn-inline  bg-warning m-3"
						href="/admin/password/${student.user.username}/">Change
						Password</A>
				</c:if>

				<c:if test="${role eq 'student'}">
					<A class="btn btn-lg btn-info btn-inline  bg-info m-3"
						href="/student/update/">Edit Details</A>
					<A class="btn btn-lg btn-info btn-inline  bg-success m-3"
						href="/student/add/work/">Edit Work</A>
					<A class="btn btn-lg btn-info btn-inline  bg-warning m-3"
						href="/student/password/${student.user.username}/">Change
						Password</A>
				</c:if>
			</div>
				
			</div>
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