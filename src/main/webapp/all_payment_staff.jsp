<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>  
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %> 
 
 
<!DOCTYPE html>
<html>
 <head>
      <meta charset="utf-8">
      <title>Staff Payments</title>
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
				<h2>Staff Payments</h2>
			</div>
		</div>
		<div class="row">
			<div
			
				class="col-12 text-center">
				<table class="table table-hover table-bordered table-striped">
						
				    <tr>
				    	<th> Staff ID </th>
				    	<th> Staff</th>
				    	<th> Payment ID </th>
				    	<th> Month </th>
				    	<th> Year </th>
				    	<th> Leaves Allowed</th>	
				    	<th> Leaves Taken</th>	
				    	<th> Overtime Worked</th>	
				    	<th> Salary Paid</th>	
				    	<c:if test="${curRole=='admin'}">
					    	<th> Update </th>	
					    	<th> Delete </th>	
					    </c:if>		    	
				    </tr>	      				
							
					<c:set var="num" value="${fn:length(allPayment)}"/>
					<c:forEach var="payment" items="${allPayment}" varStatus="i">
						<tr>
							<c:if test="${ (i.count) == 1 }">
								<td rowspan="${num}">${staff.staffID}</td>
								<td rowspan="${num}">${staff.employee.user.firstName} ${staff.employee.user.middleName} ${staff.employee.user.lastName}</td>
							</c:if>
							<td>${payment.id}</td>
							<td>${payment.month}</td>
							<td>${payment.year}</td>
							<td>${payment.leavesAllowed}</td>
							<td>${payment.leavesTaken}</td>
							<td>${payment.overtimeWorked}</td>
							<td>
								<c:if test="${payment.paid==true}">
									Yes
								</c:if>
								<c:if test="${payment.paid==false}">
									No
								</c:if> 							
							</td>
							<c:if test="${curRole=='admin'}">
								<td><a href="/admin/update/payment/staff/${payment.id}/">Edit</a></td>							
								<td><a href="/admin/delete/payment/staff/${payment.id}/">Delete</a></td>
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