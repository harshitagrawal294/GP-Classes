<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %> 
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>


<!DOCTYPE html>
<html>
 <head>
      <meta charset="utf-8">
      <title></title>
     
     
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">

  </head>
<body>


<c:set var="Role">
	${fn:toUpperCase(fn:substring(curRole, 0, 1))}${fn:toLowerCase(fn:substring(curRole, 1, -1))}   
</c:set>

<sec:authorize var="loggedIn" access="isAuthenticated()" />

<nav class="navbar navbar-expand-md bg-info text-white">
  <span class="navbar-brand"><a class="nav-link text-white" href="/">GP Classes</a></span>
  <c:if test="${loggedIn}">
  	<span class="navbar-brand">Hi ${curUsername}!</span>
  </c:if>
  <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarText" aria-controls="navbarText" aria-expanded="false" aria-label="Toggle navigation">
    <span class="navbar-toggler-icon"></span>
  </button>
  <div class="collapse navbar-collapse" id="navbarText">
  	<c:if test="${loggedIn}">
	    <ul class="navbar-nav mr-auto">
	      <li class="nav-item">
	        <a class="nav-link text-white" href="/${curRole}/details/">${Role} Profile<span class="sr-only">(current)</span></a>
	      </li>
	      <li class="nav-item">
	        <a class="nav-link text-white" href="/${curRole}/">Dashboard</a>
	      </li>
	    </ul>
    </c:if>
    <ul class="navbar-nav ml-auto">
      <li class="nav-item">
      	 <a class="nav-link text-white" href="/"><i class="fa fa-home mr-2" aria-hidden="true"></i>Home</a>
      </li>
      <li class="nav-item">
      	
      	<c:if test="${loggedIn}">
         	<a class="nav-link text-white" href="/logout"><i class="fa fa-sign-in mr-2" aria-hidden="true"></i>Logout</a>
         </c:if>
         <c:if test="${not loggedIn}">
         	<a class="nav-link text-white" href="/login/"><i class="fa fa-sign-in mr-2" aria-hidden="true"></i>Login</a>
         </c:if>
      </li>
    </ul>
  </div>
</nav>


</body>
</html>