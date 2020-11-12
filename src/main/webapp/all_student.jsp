<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>  
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
 
<!DOCTYPE html>
<html>
 <student>
      <meta charset="utf-8">
      <title>Students</title>
      <link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css"
	integrity="sha384-TX8t27EcRE3e/ihU7zmQxVncDAy5uIKz4rEkgIXeMed4M0jlfIDPvg6uqKI2xXr2"
	crossorigin="anonymous">
<link
	href="https://fonts.googleapis.com/css2?family=Lato:ital@1&display=swap"
	rel="stylesheet">
<link rel="stylesheet" href="/css/main.css" type="text/css">
  </student>
<body>

<div id="main-wrapper">


<jsp:include page="navbar.jsp">
        <jsp:param name="role" value="${role}"/>
</jsp:include>

<div class="container my-5">
	<div class="container my-auto align-self-middle">

		<div class="row">
			<div class="col-8 offset-2 d-flex justify-content-center text-center">
				<h2>Our Students</h2>
			</div>
		</div>
		<div class="row">
			<div
			
				class="col-10 offset-1 text-center">
				<table class="table table-hover table-bordered table-striped">
						
				    <tr>
				    	<th> Student ID </th>
				    	<th> Student Name </th>
				    	<th> View Profile </th>
				    	<c:if test="${curRole eq 'admin'}">
				    		<th> Delete</th>	
				    	</c:if>			    	
				    </tr>	    		
					
					<c:forEach var="student" items="${allStudent}" varStatus="i">
						<tr>
							<td>${student.studentID}</td>
							<td>${student.user.firstName} ${student.user.middleName} ${student.user.lastName}</td>
							<td><a href="/${curRole}/student/${student.user.username}/">View</a></td>	
							<c:if test="${curRole eq 'admin'}">
								<td><a href="/admin/delete/student/${student.studentID}/">Delete</a></td>
							</c:if>
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