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
<title>Staff Details Form</title>
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

<c:set var="today" value="<%=new java.util.Date()%>"/>
<fmt:formatDate var="today_formated_date" value="${today}" pattern="yyyy-MM-dd"/>  

	<div class="container align-self-center col-lg-8 border border-info my-5">
		<div class="row">
			<div class="col-lg-6 offset-lg-3 d-flex justify-content-center text-center border-bottom border-info">
				<h2 class="m-3 ">Staff Details</h2>
			</div>
		</div>
		<div class="row">
			<div class="col">

				<c:set var="sid" scope="session" value="${staff.staffID}" />

				<form:form action="" method="post" class="needs-validation m-3"
					modelAttribute="staff" novalidate="true">

					<c:if test="${sid==0}">
						<div class="form-row">
							<div class="form-group col-lg-6">
								<c:set var="usernameErrors">
									<form:errors path="employee.user.username" />
								</c:set>
								<form:label path="employee.user.username">Username</form:label>
								<form:input path="employee.user.username" required="true"
									class="form-control ${not empty usernameErrors ? 'is-invalid' : ''}" />
								<div class="invalid-feedback">
									<c:if test="${not empty usernameErrors}">
										<form:errors path="employee.user.username"></form:errors>
									</c:if>
									<c:if test="${empty usernameErrors}">
										Please provide a username
									</c:if>
								</div>
							</div>
							<div class="form-group col-lg-6">
								<c:set var="passwordErrors">
									<form:errors path="employee.user.password" />
								</c:set>
								<form:label path="employee.user.password">Password</form:label>
								<form:password path="employee.user.password" required="true"
									class="form-control ${not empty passwordErrors ? 'is-invalid' : ''}" />
								<div class="invalid-feedback">
									<c:if test="${not empty passwordErrors}">
										<form:errors path="employee.user.password"></form:errors>
									</c:if>
									<c:if test="${empty passwordErrors}">
										Please provide a strong password
									</c:if>
								</div>							</div>
						</div>
						<div class="form-group row">
							<form:label path="employee.user.emailID" class="col-lg-4 col-form-label">Email Address</form:label>
							<div class="col-lg-8">
								<form:input class="form-control" type="email"
									path="employee.user.emailID" maxlength="255" required="true" />
								<div class="invalid-feedback">Please provide a valid Email ID</div>
							</div>
						</div>
					</c:if>

					<c:if test="${sid!=0}">
						<form:hidden path="staffID" value="${staffID}" />
						<form:hidden path="employee.user.active" value="${employee.user.active}" />
						<form:hidden path="employee.user.role" value="${role}" />
						<form:hidden path="employee.user.username" value="Dummy Username" />
						<form:hidden path="employee.user.password" value="Dummy Password" />
						<form:hidden path="employee.user.emailID" value="${employee.user.emailID}" />
					</c:if>




					<c:if test="${role eq 'staff'}">
						<form:hidden path="employee.user.adhaarNumber" />
						<form:hidden path="employee.salary" />
					</c:if>
					
					<div class="form-row">
						<div class="form-group col-lg-4">
							<form:label path="employee.user.firstName">First Name</form:label>
							<form:input path="employee.user.firstName" maxlength="255"
								class="form-control" required="true" />
							<div class="invalid-feedback">Please provide a valid first name </div>
						</div>
						<div class="form-group col-lg-4">
							<form:label path="employee.user.middleName">Middle Name</form:label>
							<form:input path="employee.user.middleName" maxlength="255"
								class="form-control" placeholder="optional" />
							<div class="invalid-feedback">Please provide a valid middle name </div>
						</div>
						<div class="form-group col-lg-4">
							<form:label path="employee.user.lastName">Last Name</form:label>
							<form:input path="employee.user.lastName" maxlength="255"
								class="form-control" required="true" />
							<div class="invalid-feedback">Please provide a valid last name </div>
						</div>
					</div>

					<div class="form-row">
						<div class="form-group col-lg-6">
							<form:label path="employee.user.birthDate">Birth Date</form:label>
							<form:input path="employee.user.birthDate" type="date"  max="${today_formated_date}"
								class="form-control" required="true" />
							<div class="invalid-feedback">Please provide a Birth Date</div>
						</div>
						<div class="form-group col-lg-6">
							<form:label path="employee.user.gender">Gender</form:label>
							<form:select class="custom-select" path="employee.user.gender" required="true">
								<option selected disabled value="">Choose...</option>
								<form:option path="employee.user.gender" value="Male" selected="">Male</form:option>
								<form:option path="employee.user.gender" value="Female">Female</form:option>
								<form:option path="employee.user.gender" value="Other">Other</form:option>
							</form:select>
							<div class="invalid-feedback">Please select a Gender</div>
						</div>
					</div>

					

					<div class="form-row">
						<div class="form-group col-lg-6">
							<form:label path="employee.user.street">Street</form:label>
							<form:input path="employee.user.street" maxlength="255"
								class="form-control" required="true" />
							<div class="invalid-feedback">Please provide a street name </div>
						</div>
						<div class="form-group col-lg-6">
							<form:label path="employee.user.city">City</form:label>
							<form:input path="employee.user.city" maxlength="255" class="form-control"
								required="true" />
							<div class="invalid-feedback">Please provide a city name </div>
						</div>
					</div>

					<div class="form-row">
						<div class="form-group col-lg-6">
							<form:label path="employee.user.state">State</form:label>
							<form:input path="employee.user.state" maxlength="255"
								class="form-control" required="true" />
							<div class="invalid-feedback">Please provide a state name </div>
						</div>
						<div class="form-group col-lg-6">
							<form:label path="employee.user.country">Country</form:label>
							<form:input path="employee.user.country" maxlength="255"
								class="form-control" required="true" />
							<div class="invalid-feedback">Please provide a country name </div>
						</div>
					</div>
					
					
					<div class="form-row">
						<div class="form-group col-lg-6">
							<form:label path="employee.accountNumber">Account Number</form:label>
							<form:input path="employee.accountNumber" class="form-control"
								required="true" type="number" min="100000000"/>
							<div class="invalid-feedback">Please provide a valid Account Number</div>
						</div>
						<div class="form-group col-lg-6">
							<form:label path="employee.user.phone">Phone Number</form:label>
							<form:input path="employee.user.phone" class="form-control"
								required="true" type="number" min="1000000000" max="9999999999" />
							<div class="invalid-feedback">Please provide a valid Phone Number</div>
						</div>
					</div>
					
					<c:if test="${role ne 'staff'}">
						<div class="form-row">
							<div class="form-group col-lg-6">
								<form:label path="employee.user.adhaarNumber">Adhaar Number</form:label>
									<form:input class="form-control" type="number"
										min="100000000000" max="999999999999" path="employee.user.adhaarNumber"
										required="true" />
									<div class="invalid-feedback">
										Please provide a valid Adhaar Number
										<form:errors path="employee.user.adhaarNumber"></form:errors>
									</div>
							</div>				
							<div class="form-group col-lg-6">
								<form:label path="employee.salary">Salary</form:label>
								<form:input path="employee.salary" class="form-control"
									required="true" type="number" min="0" step="0.01"/>
								<div class="invalid-feedback">Please provide a salary in step of 0.01</div>
							</div>
						</div>					
					</c:if>
				
					
					
					
					<div class="form-row">
						
						<div class="form-group col-lg-6">
							<form:label path="employee.joiningDate">Joining Date</form:label>
							<form:input path="employee.joiningDate" type="date" class="form-control"  max="${today_formated_date}"
								required="true" />
							<div class="invalid-feedback">Please provide Joining Date </div>
						</div>
						<div class="form-group col-lg-6">
							<form:label path="employee.PAN">PAN</form:label>
							<form:input path="employee.PAN" pattern="[A-Z]{5,5}[0-9]{4,4}[A-Z]" class="form-control"
								required="true"/>
							<div class="invalid-feedback">Please provide a valid PAN</div>
						</div>
					</div>
					
					<div class="form-group">
						<form:label path="work">Work</form:label>
						<form:textarea path="work" type="textarea"
							class="form-control" required="true" maxlength="255" />
						<div class="invalid-feedback">Please provide work details </div>
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