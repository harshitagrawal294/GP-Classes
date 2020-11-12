<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@taglib uri="http://java.sun.com/jsp/jstl/core"
prefix="c" %> <%@taglib prefix="spring"
uri="http://www.springframework.org/tags" %> 
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>

${staff}

		<c:forEach items="${allPayment}" var="payment">
			<p>
			${ payment }
			 <A href="/admin/update/payment/staff/${payment.id}/">edit</A>      
			 <A href="/admin/delete/payment/staff/${payment.id}/">delete</A>            
            <br><br>
        </c:forEach>

</body>
</html>