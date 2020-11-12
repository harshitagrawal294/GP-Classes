<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html lang="en">
  <head>
      <meta charset="utf-8">
      <title>Password</title>
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
  
  <div class="row-no-margin">
  	
 	<div class="row">
		<div class="col-md-4 offset-md-4 my-5 py-4 border border-info">		
			<div class="row">
				<div class="col-md-8 offset-md-2 d-flex justify-content-center text-center border-bottom border-info">
					<h2 class="mt-3 ">Change Password</h2>
				</div>
			</div>
			<div class="row">
				<div class="col">		
					<form:form action="" method="post" class="needs-validation m-3"  modelAttribute="user" novalidate="true">
						<div class="form-group">
						
							<c:if test="${curRole!='admin'}">
								<form:label path="oldPassword">Old Password:</form:label>
		            			<form:password path="oldPassword"  required="true" class="form-control ${error != null ? 'is-invalid' : ''}"/>
		            			<div class="invalid-feedback">${error}</div>
		            		</c:if>
	            			<c:set var="passErrors"><form:errors path="password" /></c:set>
	            			<form:label path="password">New Password</form:label>
	            			<form:password path="password" required="true" class="form-control ${not empty passErrors ? 'is-invalid' : '' }"/>
	            			<div class="invalid-feedback">${passErrors}</div>
						</div>
						
						<form:button class="btn btn-lg btn-info btn-block col-6 offset-3 bg-info" type="submit">Done</form:button>					
					</form:form>
				</div>
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