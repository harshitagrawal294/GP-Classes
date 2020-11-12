<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>  
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
 
<!DOCTYPE html>
<html>
 <head>
      <meta charset="utf-8">
      <title>Admin Dashboard</title>
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
        <jsp:param name="role" value="${curRole}"/>
</jsp:include>

<div class="container d-flex mt-2 pt-5">
	<div class="container my-auto align-self-middle">
		<div class="row">
			<div class="col-md-6 offset-md-3 d-flex justify-content-center text-center">
				<h2>Welcome Admin!</h2>
			</div>
		</div>
		<div class="row">
			<div
				class="col-md-6 offset-md-3 d-flex justify-content-center text-center">
				<table class="table table-hover table-bordered table-striped mb-5">
					<tr>
						<th scope="row">Your Profile</th>
						<td colspan="2"><A href="/admin/details/">View</A></td>
					</tr>
					<tr>
						<th scope="row">Exam</th>
						<td><A href="/admin/add/exam/">Add</A></td>
						<td><A href="/admin/allexam/">View All</A></td>
					</tr>
					<tr>
						<th scope="row">Course</th>
						<td><A href="/admin/add/course/">Add</A></td>
						<td><A href="/admin/allcourse/">View All</A></td>
					</tr>
					<tr>
						<th scope="row">Batch</th>
						<td><A href="/admin/add/batch/">Add</A></td>
						<td><A href="/admin/allbatch/">View All</A></td>
					</tr>
					<tr>
						<th scope="row">Test Series</th>
						<td><A href="/admin/add/testseries/">Add</A></td>
						<td><A href="/admin/alltestseries/">View All</A></td>
					</tr>
					<tr>
						<th scope="row">Test</th>
						<td><A href="/admin/add/test/">Add</A></td>
						<td><A href="/admin/alltest/">View All</A></td>
					</tr>					
					<tr>
						<th scope="row">Subject</th>
						<td><A href="/admin/add/subject/">Add</A></td>
						<td><A href="/admin/allsubject/">View All</A></td>
					</tr>
					<tr>
						<th scope="row">Alumnus</th>
						<td><A href="/admin/add/alumnus/">Add</A></td>
						<td><A href="/admin/allalumnus/">View All</A></td>
					</tr>
					<tr>
						<th scope="row">Faculty</th>
						<td><A href="/admin/register/faculty/">Add</A></td>
						<td><A href="/admin/allfaculty/">View All</A></td>
					</tr>
					<tr>
						<th scope="row">Staff</th>
						<td><A href="/admin/register/staff/">Add</A></td>
						<td><A href="/admin/allstaff/">View All </A></td>
					</tr>
					<tr>
						<th scope="row">Student</th>
						<td colspan="2"><A href="/admin/allstudent/">View All </A></td>
					</tr>
					<tr>
						<th scope="row">Transaction</th>
						<td colspan="2"><A href="/admin/alltransaction/">View All</A></td>
					</tr>
					<tr>
						<th scope="row">Feedback</th>
						<td colspan="2"><A href="/admin/allfeedback/">View All </A></td>
					</tr>
					<tr>
						<th scope="row">Messages</th>
						<td colspan="2"><A href="/admin/allcontactusform/">View All </A></td>
					</tr>
					<tr>
						<th scope="row">Faculty Applications</th>
						<td colspan="2"><A href="/admin/allfacultyapplicant/">View All </A></td>
					</tr>
					<tr>
						<th scope="row" colspan="3"><A href="/admin/password/">Change Password</th>
					</tr>
					
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