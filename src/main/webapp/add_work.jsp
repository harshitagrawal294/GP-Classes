<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Work Form</title>
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
	

	<c:if test = "${not empty allWork}">
		<div class="container my-5">
			<div class="container my-auto align-self-middle">
		
				<div class="row">
					<div class="col-8 offset-2 d-flex justify-content-center text-center">
						<h2>Work Added</h2>
					</div>
				</div>
				<div class="row">
					<div
						class="col-10 offset-1 d-flex justify-content-center text-center">
						<table class="table table-hover table-bordered table-striped">
						
		      				<tr>
		      					<th> Company </th>
		      					<th> Joining Date</th>
		      					<th> Delete </th>
		      				</tr>	      				

							<c:forEach var="work" items="${allWork}" varStatus="i">
								<tr>
									<td>${work.company}</td>
									<td><fmt:formatDate value="${work.workJoiningDate}" pattern="dd-MM-yyyy" /></td>
									<td> <a href="${work.workID}/">Remove</a></td>
								</tr>					
							</c:forEach>
						</table>	
					</div>
						
				</div>
			</div>
		</div>
	</c:if>
	
	
		<div class="container align-self-center col-lg-5 border border-info my-5">
			<div class="row">
				<div class="col-lg-8 offset-lg-2 d-flex justify-content-center text-center border-bottom border-info">
					<h2 class="m-3 ">Add Work</h2>
				</div>
			</div>
			<div class="row">
				<div class="col">
					
					<form:form action="" method="post" class="needs-validation m-3"
						modelAttribute="work" novalidate="true">
			
						
						<div class="form-group">
							<form:label path="company">Company</form:label>
							<form:input path="company" class="form-control" required="true"/>
							<div class="invalid-feedback">Please enter the company name</div>
						</div>
						<div class="form-group">
							<form:label path="workJoiningDate">Joining Date</form:label>
							<form:input path="workJoiningDate" class="form-control" type="date" required="true"/>
							<div class="invalid-feedback">Please enter a valid joining date</div>
						</div>
						<div class="form-group">
							<form:label path="workLeavingDate">Leaving Date</form:label>
							<form:input path="workLeavingDate" class="form-control" type="date"/>
							<div class="invalid-feedback">Please enter a valid leaving date</div>
						</div>
						<div class="form-group">
							<form:label path="workDescription">Work Description</form:label>
							<form:input path="workDescription" class="form-control" required="true"/>
							<div class="invalid-feedback">Please enter Work Description</div>
						</div>
						<div class="form-group">
							<form:label path="workRole">Work Role</form:label>
							<form:input path="workRole" class="form-control" required="true"/>
							<div class="invalid-feedback">Please enter Work Role</div>
						</div>
						
						<div class="row">
							<div class="col-md-6 offset-md-3 text-center">
								<form:button class="btn btn-lg btn-info btn-block" type="submit">Add</form:button>
							</div>
						</div>
					</form:form>
				 
				</div>
			</div>
	
		</div>
			
	
	<div class="row">
		<div class="col-sm-2 offset-sm-5 mb-5 text-center">
			<c:if test = "${role eq 'admin'}">
				<a class="btn btn-lg btn-success btn-block" href="/admin/student/${work.student.user.username}/"">Done</a>
			</c:if>
			<c:if test = "${role eq 'student'}">
				<a class="btn btn-lg btn-success btn-block" href="/student/details/">Done</a>
       		 </c:if>
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