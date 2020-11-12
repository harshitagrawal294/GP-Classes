<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>  
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
 
<!DOCTYPE html>
<html>
 <head>
      <meta charset="utf-8">
      <title>Faculty Details</title>
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
				<h2>Faculty Profile</h2>
			</div>
		</div>
		<div class="row">
		
		
			<div
				class="col-10 offset-1 text-center">
				<table class="table table-hover table-bordered table-striped">
					<tr>
						<th scope="row">Faculty Name</th>
						<td>${faculty.employee.user.firstName} ${faculty.employee.user.middleName} ${faculty.employee.user.lastName}</td>
					</tr>
					<tr>
						<th scope="row">Faculty ID</th>
						<td>${faculty.facultyID}</td>
					</tr>
					<tr>
						<th scope="row">Username</th>
						<td>${faculty.employee.user.username}</td>
					</tr>
					<tr>
						<th scope="row">Adhaar Number</th>
						<td>${faculty.employee.user.adhaarNumber}</td>
					</tr>
					<tr>
						<th scope="row">Gender</th>
						<td>${faculty.employee.user.gender}</td>
					</tr>
					<tr>
						<th scope="row">Email ID</th>
						<td>${faculty.employee.user.emailID}</td>
					</tr>
					
					<tr>
						<th scope="row">Street</th>
						<td>${faculty.employee.user.street}</td>
					</tr>
					<tr>
						<th scope="row">City</th>
						<td>${faculty.employee.user.city}</td>
					</tr>
					<tr>
						<th scope="row">State</th>
						<td>${faculty.employee.user.state}</td>
					</tr>
					<tr>
						<th scope="row">Country</th>
						<td>${faculty.employee.user.country}</td>
					</tr>
					<tr>
						<th scope="row">Phone</th>
						<td> ${faculty.employee.user.phone}</td>
					</tr>
					<tr>
						<th scope="row">Birth Date</th>
						<td><fmt:formatDate value="${faculty.employee.user.birthDate}" pattern="dd-MM-yyyy" /></td>
					</tr>
					
					<tr>
						<th scope="row">Teaching Experience</th>
						<td>${faculty.teachingExpirience}</td>
					</tr>
					<tr>
						<th scope="row">College</th>
						<td>${faculty.college}</td>
					</tr>
					<tr>
						<th scope="row">Graduation Year</th>
						<td>${faculty.year}</td>
					</tr>
					<tr>
						<th scope="row">Achievement</th>
						<td>${faculty.achievement}</td>
					</tr>
					<tr>
						<th scope="row">Degree</th>
						<td>${faculty.degree}</td>
					</tr>

					<tr>
						<th scope="row">Subject</th>
						<td>
							<c:if test="${empty faculty.subject}">-</c:if>						
							${faculty.subject.subjectName}
						</td>
					</tr>
								
					<tr>
						<th scope="row">Employee ID</th>
						<td>${faculty.employee.employeeID}</td>
					</tr>
					
					<tr>
						<th scope="row">Salary</th>
						<td>${faculty.employee.salary}</td>
					</tr>
					
					<tr>
						<th scope="row">AccountNumber</th>
						<td>${faculty.employee.accountNumber}</td>
					</tr>
					
					
					<tr>
						<th scope="row">PAN</th>
						<td>${faculty.employee.PAN}</td>
					</tr>
					
					<tr>
						<th scope="row">Joining Date</th>
						<td><fmt:formatDate value="${faculty.employee.joiningDate}" pattern="dd-MM-yyyy" /></td>
					</tr>
					
							
				</table>
				
			</div>
			
			<div class="col-lg-8 offset-lg-2 d-flex justify-content-around">
			

				<c:if test="${role eq 'admin'}">
					<a class="btn btn-lg btn-info btn-inline  bg-info m-3"
						href="/admin/faculty/update/${faculty.employee.user.username}/">Edit
						Details</a>
					<a class="btn btn-lg btn-info btn-inline  bg-success m-3"
						href="/admin/payment/faculty/${faculty.facultyID}/">View Payroll
					</a>
					<A class="btn btn-lg btn-info btn-inline  bg-warning m-3"
						href="/admin/password/${faculty.employee.user.username}/">Change
						Password</A>
				</c:if>

				<c:if test="${role eq 'faculty'}">
					<a class="btn btn-lg btn-info btn-inline  bg-info m-3"
						href="/faculty/update/">Edit Details</a>
					<A class="btn btn-lg btn-info btn-inline  bg-warning m-3"
						href="/faculty/password/">Change
						Password</A>
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