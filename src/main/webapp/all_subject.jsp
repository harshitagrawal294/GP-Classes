<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>  
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
 
<!DOCTYPE html>
<html>
 <head>
      <meta charset="utf-8">
      <title>Subjects</title>
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
				<h2>All Subjects</h2>
			</div>
		</div>
		<div class="row">
			<div
			
				class="col-10 offset-1 text-center">
				<table class="table table-hover table-bordered table-striped">
						
				    <tr>
				    	<th> Subject ID </th>
				    	<th> Name </th>
				    	<th> Introduced In </th>
				    	<th> Head Faculty</th>	
				    	<th> Update </th>	
				    	<th> Delete </th>			    	
				    </tr>	      				
										
					
					<c:forEach var="subject" items="${allSubject.getFirst()}" varStatus="i">
						<tr>
							<td>${subject.subjectID}</td>
							<td>${subject.subjectName}</td>
							<td>${subject.subjectIntroducedIn}</td>
							<td>${subject.head.facultyID}: ${subject.head.employee.user.firstName} ${subject.head.employee.user.middleName} ${subject.head.employee.user.lastName}</td>
							<td><a href="/admin/update/subject/${subject.subjectID}/">Edit</a></td>							
							<td><a href="/admin/delete/subject/${subject.subjectID}/">Delete</a></td>
						</tr>					
					</c:forEach>
					<c:forEach var="subject" items="${allSubject.getSecond()}" varStatus="i">
						<tr>
							<td>${subject.subjectID}</td>
							<td>${subject.subjectName}</td>
							<td>${subject.subjectIntroducedIn}</td>
							<td>No Head Faculty</td>
							<td><a href="/admin/update/subject/${subject.subjectID}/">Edit</a></td>							
							<td><a href="/admin/delete/subject/${subject.subjectID}/">Delete</a></td>
						</tr>					
					</c:forEach>
		
				</table>
				
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