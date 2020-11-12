<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>  
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
 
<!DOCTYPE html>
<html>
 <head>
      <meta charset="utf-8">
      <title>FAQ</title>
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

<div class="container col-md-8 offset-2 mt-4">
		<div class="col-lg-6 offset-lg-3 d-flex justify-content-center text-center border-bottom border-info mb-2 ">
			<h4 class="m-2 ">Frequently Asked Questions</h2>
		</div>
		<div id="accordion">
			<c:forEach var="faq" items="${FAQ}" varStatus="stat">
			<c:if test="${faq.showFAQ}">
			
				
					<div class="card">
					    <div class="card-header p-1" id="heading${stat.count}">
					      <h5 class="mb-0">
					        <button class="btn btn-link collapsed" data-toggle="collapse" data-target="#collapse${stat.count}" aria-expanded="false" aria-controls="collapse${stat.count}">
					          ${faq.query }
					        </button>
					      </h5>
					    </div>
					    <div id="collapse${stat.count}" class="collapse" aria-labelledby="heading${stat.count}" data-parent="#accordion">
					      <div class="card-body">
					      	${faq.replyGiven}
					      </div>
					    </div>
					</div>
						  
			</c:if>
			</c:forEach>
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