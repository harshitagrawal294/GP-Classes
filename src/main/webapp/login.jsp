<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html lang="en">
  <head>
      <meta charset="utf-8">
      <title>Log in</title>
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
  	
 
	<div class="container mt-5 align-self-center col-md-3 border border-info">
		<c:if test="${ not empty message}">
			<div class="row mx-4 mt-3">
				<div class="alert alert-warning" role="alert">	
					<div>${message}</div>
				</div>
			</div>
		</c:if>
		
		<div class="row">
			<div class="col-md-8 offset-md-2 d-flex justify-content-center text-center border-bottom border-info">
				<h2 class="mt-3 ">Sign in</h2>
			</div>
		</div>
		<div class="row">
			<div class="col">				
				<form action="${contextPath}/login" method="post" class="needs-validation m-3" novalidate="true">
					<div class="form-group">
						<label>Username</label>
						<input required="true" name="username" class="form-control ${error != null ? 'is-invalid' : ''}" type="text" />
						<label>Password</label>
						<input required="true" name="password" class="form-control ${error != null ? 'is-invalid' : ''}" type="password"/>
						<div class="invalid-feedback">${error}</div>
						<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
					</div>
					<button class="btn btn-lg btn-success btn-block" type="submit">Log In</button>
					<div class="text-center">Not registered yet? <a class="text-center" href="/register/student/">Register Now</a></div>
					
				</form>
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