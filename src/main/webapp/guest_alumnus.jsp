<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>  
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %> 
 
<!DOCTYPE html>
<html>
 <head>
      <meta charset="utf-8">
      <title>Pride</title>
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

<div class="container d-flex min-vh-100 my-1">
	<div class="container my-4">

		<div class="row">
			<div class="col-8 offset-2 my-2 text-center border-bottom border-info">
				<h2>Success Stories</h2>
			</div>
		</div>
		
		<c:set var="num" value="${fn:length(allAlumnus)}"/>
		
		
			<c:forEach var="alumnus" items="${allAlumnus}" varStatus="stat">

                <c:if test="${ (stat.count)%3 == 1 }">
                   <div class="row d-flex card-deck justify-content-around">
                </c:if>
                	
                	<div class="card my-5 text-white card-fixed">
					  <div class="card-header bg-secondary">${alumnus.student.user.firstName} ${alumnus.student.user.middleName} ${alumnus.student.user.lastName}</div>
					  <div class="card-body bg-info">
					    <h5 class="card-title"></h5>
					    <p class="card-text">${ alumnus.reviewWritten }</p>
					  </div>
					  <div class="card-footer bg-dark">
					  	<div class="d-flex justify-content-around">
					  		<div>
					  			${ alumnus.alumAchievement }
					  		</div>
					  	</div>
		    		  </div>
					</div>
					
                
                <c:if test="${ stat.count == num || stat.count % 3 == 0 }">
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