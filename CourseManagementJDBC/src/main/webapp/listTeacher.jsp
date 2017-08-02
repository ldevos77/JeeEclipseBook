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
<title>Teacher</title>
</head>
<body>
	<c:catch var="err">
		<jsp:useBean id="teacherBean" class="packt.book.jee.eclipse.ch4.bean.Teacher"/>
		<c:set var="teachers" value="${teacherBean.getTeachers()}"/>
	</c:catch>
	<c:choose>
		<c:when test="${err != null}">
			<c:set var="errMsg" value="${err.message}"/>
		</c:when>
		<c:otherwise></c:otherwise>
	</c:choose>
	<h2>Teachers:</h2>
	<c:if test="${errMsg != null}">
		<span style="color: red;">
			<c:out value="${errMsg}"></c:out>
		</span>
	</c:if>
	<table>
		<tr>
			<th>Id</th>
			<th>First name</th>
			<th>Designation</th>
		</tr>
		<c:forEach items="${teachers}" var="teacher">
			<tr>
				<td>${teacher.id}</td>
				<td>${teacher.firstName}</td>
				<td>${teacher.designation}</td>
			</tr>
		</c:forEach>
	</table>
</body>
</html>