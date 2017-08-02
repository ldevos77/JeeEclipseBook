<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<style>
table, th, td {
    border: 1px solid black;
}
</style>
<title>Student</title>
</head>
<body>
	<c:catch var="err">
		<jsp:useBean id="studentBean" class="packt.book.jee.eclipse.ch4.bean.Student"/>
		<c:set var="students" value="${studentBean.getStudents()}"/>
	</c:catch>
	<c:choose>
		<c:when test="${err != null}">
			<c:set var="errMsg" value="${err.message}"/>
		</c:when>
		<c:otherwise></c:otherwise>
	</c:choose>
	<h2>Students:</h2>
	<c:if test="${errMsg != null}">
		<span style="color: red;">
			<c:out value="${errMsg}"></c:out>
		</span>
	</c:if>
	<table>
		<tr>
			<th>Id</th>
			<th>First name</th>
			<th>Enrolled since</th>
		</tr>
		<c:forEach items="${students}" var="student">
			<tr>
				<td>${student.id}</td>
				<td>${student.firstName}</td>
				<td>${student.enrolledSince}</td>
			</tr>
		</c:forEach>
	</table>
</body>
</html>