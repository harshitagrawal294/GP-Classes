<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Student Details Form</title>
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

	<div class="container align-self-center col-lg-8 border border-info my-5">
		<div class="row">
			<div class="col-lg-6 offset-lg-3 d-flex justify-content-center text-center border-bottom border-info">
				<h2 class="m-3 ">Student Details</h2>
			</div>
		</div>
		<div class="row">
			<div class="col">

				<c:set var="sid" scope="session" value="${student.studentID}" />

				<form:form action="" method="post" class="needs-validation m-3"
					modelAttribute="student" novalidate="true">

					<c:if test="${sid==0}">
						<div class="form-row">
							<div class="form-group col-lg-6">
								<c:set var="usernameErrors">
									<form:errors path="user.username" />
								</c:set>
								<form:label path="user.username">Username</form:label>
								<form:input path="user.username" required="true"
									class="form-control ${not empty usernameErrors ? 'is-invalid' : ''}" />
								<div class="invalid-feedback">
									<c:if test="${not empty usernameErrors}">
										<form:errors path="user.username"></form:errors>
									</c:if>
									<c:if test="${empty usernameErrors}">
										Please provide a username
									</c:if>
								</div>
							</div>
							<div class="form-group col-lg-6">
								<c:set var="passwordErrors">
									<form:errors path="user.password" />
								</c:set>
								<form:label path="user.password">Password</form:label>
								<form:password path="user.password" required="true"
									class="form-control ${not empty passwordErrors ? 'is-invalid' : ''}" />
								<div class="invalid-feedback">
									<c:if test="${not empty passwordErrors}">
										<form:errors path="user.password"></form:errors>
									</c:if>
									<c:if test="${empty passwordErrors}">
										Please provide a strong password
									</c:if>
								</div>							</div>
						</div>
						<div class="form-group row">
							<form:label path="user.emailID" class="col-lg-4 col-form-label">Email Address</form:label>
							<div class="col-lg-8">
								<form:input class="form-control" type="email"
									path="user.emailID" maxlength="255" required="true" />
								<div class="invalid-feedback">Please provide a valid Email ID</div>
							</div>
						</div>
					</c:if>

					<c:if test="${sid!=0}">
						<form:hidden path="studentID" value="${studentID}" />
						<form:hidden path="user.active" value="${user.active}" />
						<form:hidden path="user.role" value="${role}" />
						<form:hidden path="user.username" value="Dummy Username" />
						<form:hidden path="user.password" value="Dummy Password" />
						<form:hidden path="user.emailID" value="${user.emailID}" />
					</c:if>





					<c:if test="${role ne 'student'}">
						<div class="form-group row">
							<form:label path="user.adhaarNumber"
								class="col-lg-4 col-form-label">Adhaar Number</form:label>
							<div class="col-lg-8">
								<form:input class="form-control" type="number"
									min="100000000000" max="999999999999" path="user.adhaarNumber"
									required="true" />
								<div class="invalid-feedback">
									Please provide a valid Adhaar Number
									<form:errors path="user.adhaarNumber"></form:errors>
								</div>
							</div>
						</div>
					</c:if>

					<c:if test="${role eq 'student'}">
						<form:hidden path="user.adhaarNumber" />
					</c:if>

					<div class="form-row">
						<div class="form-group col-lg-6">
							<form:label path="user.birthDate">Birth Date</form:label>
							<c:set var="today" value="<%=new java.util.Date()%>"/>
		<fmt:formatDate var="today_formated_date" value="${today}" pattern="yyyy-MM-dd"/>  
							<form:input path="user.birthDate" type="date" max="${today_formated_date}"
								class="form-control" required="true" />
							<div class="invalid-feedback">Please provide a Birth Date</div>
						</div>
						<div class="form-group col-lg-6">
							<form:label path="user.gender">Gender</form:label>
							<form:select class="custom-select" path="user.gender" required="true">
								<option selected disabled value="">Choose...</option>
								<form:option path="user.gender" value="Male" selected="">Male</form:option>
								<form:option path="user.gender" value="Female">Female</form:option>
								<form:option path="user.gender" value="Other">Other</form:option>
							</form:select>
							<div class="invalid-feedback">Please select a Gender</div>
						</div>
					</div>

					<div class="form-row">
						<div class="form-group col-lg-4">
							<form:label path="user.firstName">First Name</form:label>
							<form:input path="user.firstName" maxlength="255"
								class="form-control" required="true" />
							<div class="invalid-feedback">Please provide a valid first name </div>
						</div>
						<div class="form-group col-lg-4">
							<form:label path="user.middleName">Middle Name</form:label>
							<form:input path="user.middleName" maxlength="255"
								class="form-control" placeholder="optional" />
							<div class="invalid-feedback">Please provide a valid middle name </div>
						</div>
						<div class="form-group col-lg-4">
							<form:label path="user.lastName">Last Name</form:label>
							<form:input path="user.lastName" maxlength="255"
								class="form-control" required="true" />
							<div class="invalid-feedback">Please provide a valid last name </div>
						</div>
					</div>

					<div class="form-row">
						<div class="form-group col-lg-6">
							<form:label path="user.street">Street</form:label>
							<form:input path="user.street" maxlength="255"
								class="form-control" required="true" />
							<div class="invalid-feedback">Please provide a street name </div>
						</div>
						<div class="form-group col-lg-6">
							<form:label path="user.city">City</form:label>
							<form:input path="user.city" maxlength="255" class="form-control"
								required="true" />
							<div class="invalid-feedback">Please provide a city name </div>
						</div>
					</div>

					<div class="form-row">
						<div class="form-group col-lg-6">
							<form:label path="user.state">State</form:label>
							<form:input path="user.state" maxlength="255"
								class="form-control" required="true" />
							<div class="invalid-feedback">Please provide a state name </div>
						</div>
						<div class="form-group col-lg-6">
							<form:label path="user.country">Country</form:label>
							<form:input path="user.country" maxlength="255"
								class="form-control" required="true" />
							<div class="invalid-feedback">Please provide a country name </div>
						</div>
					</div>
					
					
					<div class="form-group row">
			    		<form:label path="user.phone"
			    			class="col-lg-4 col-form-label pr-0">Phone Number:</form:label>
			    		<div class="col-lg-8">
			    			<form:input class="form-control" type="number"
			    				min="1000000000" max="9999999999" path="user.phone"
			    				required="true" />
			    			<div class="invalid-feedback">
			    				Please provide a valid Phone Number
			    				<form:errors path="user.phone"></form:errors>
			    			</div>
			    		</div>
			    	</div>

					

					<div class="form-row">
						<div class="form-group col-lg-6">
							<form:label path="parentName">Parent Name</form:label>
							<form:input path="parentName" maxlength="255"
								class="form-control" required="true" />
							<div class="invalid-feedback">Please provide the name of student's parent </div>
						</div>
						<div class="form-group col-lg-6">
							<form:label path="parentOccupation">Parent Occupation</form:label>
							<form:input path="parentOccupation" maxlength="255"
								class="form-control" required="true" />
							<div class="invalid-feedback">Please provide the occupation of student's parent </div>
						</div>
					</div>

					<div class="form-row">
						<div class="form-group col-lg-6">
							<form:label path="board10th">10th Board</form:label>
							<form:input path="board10th" maxlength="255" class="form-control"
								required="true" />
							<div class="invalid-feedback">Please provide the name of your 10th board </div>
						</div>
						<div class="form-group col-lg-6">
							<form:label path="percentage10th">Precentage (converted out of 100)</form:label>
							<form:input path="percentage10th" class="form-control"
								required="true" type="number" max="100" min="0" step="1" />
							<div class="invalid-feedback">Please provide your 10th board percentage</div>
						</div>
					</div>

					<div class="form-row">
						<div class="form-group col-lg-6">
							<form:label path="board12th">12th Board</form:label>
							<form:input path="board12th" maxlength="255" class="form-control"
								required="true" />
							<div class="invalid-feedback">Please provide the name of your 12th board </div>
						</div>
						<div class="form-group col-lg-6">
							<form:label path="percentage12th">Precentage (converted out of 100)</form:label>
							<form:input path="percentage12th" class="form-control"
								required="true" type="number" max="100" min="0" step="1" />
							<div class="invalid-feedback">Please provide your 12th board percentage</div>
						</div>
					</div>


					<div class="form-row">
						<div class="form-group col-lg-6">
							<form:label path="collegeGrad">Graduate School</form:label>
							<form:input path="collegeGrad" maxlength="255" 
								class="form-control" required="true" />
							<div class="invalid-feedback">Please provide the name of your graduation school </div>
						</div>
						<div class="form-group col-lg-6">
							<form:label path="percentageGrad">Percentage (converted out of 100)</form:label>
							<form:input path="percentageGrad" class="form-control"
								required="true" type="number" max="100" min="0" step="1" />
							<div class="invalid-feedback">Please provide your graduation percentage</div>
						</div>
					</div>

					<div class="form-row">
						<div class="form-group col-lg-6">
							<form:label path="collegePGrad">Post Graduate School</form:label>
							<form:input path="collegePGrad" maxlength="255"
								class="form-control" placeholder="optional" />
							<div class="invalid-feedback">Please provide the name of your post-graduation school </div>
						</div>
						<div class="form-group col-lg-6">
							<form:label path="percentagePGrad">Precentage (converted out of 100)</form:label>
							<form:input path="percentagePGrad" class="form-control"
								type="number" max="100" min="0" step="1" />
							<div class="invalid-feedback">Please provide your post-graduation percentage</div>
						</div>
					</div>

					<div class="form-group">
						<form:label path="stream">Stream</form:label>
						<form:input path="stream" class="form-control" maxlength="255"
							placeholder="optional" />
						<div class="invalid-feedback">Please provide the stream of your studies</div>
					</div>

					<div class="form-group">
						<form:label path="achievement">Achievement</form:label>
						<form:textarea path="achievement" type="textarea"
							class="form-control" placeholder="optional" maxlength="1000" />
						<div class="invalid-feedback">Please provide your achievement </div>
					</div>
					<div class="row">
						<div class="col-md-4 offset-md-4 text-center">
							<form:button class="btn btn-lg btn-info btn-block bg-info" type="submit">Submit form</form:button>
						</div>
					</div>
				</form:form>
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