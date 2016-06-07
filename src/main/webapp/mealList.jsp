<%--
  Created by IntelliJ IDEA.
  User: vladimir
  Date: 05.06.2016
  Time: 12:03
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Meal List</title>
</head>
<body>
    <h2><a href="index.html">Home</a></h2>
    <h2>Meal list</h2>

    <a href="meals?action=update">Create new</a>
    <table border="2px">

        <tr>
            <td>Date</td>
            <td>Description</td>
            <td>Calories</td>
            <td colspan="2">Action</td>
        </tr>

    <c:forEach items="${mealList}" var="meal">
        <c:choose>
            <c:when test="${meal.exceed}">
                <tr style="color: red">
            </c:when>
            <c:otherwise>
                <tr style="color: green">
            </c:otherwise>
        </c:choose>
            <td>
                <c:out value="${meal.formatDateTime}"></c:out>
            </td>
            <td>
                <c:out value="${meal.description}"></c:out>
            </td>
            <td>
                <c:out value="${meal.calories}"></c:out>
            </td>
            <td>
                <a href="meals?action=update&id=<c:out value="${meal.id}"></c:out>">Update</a>
            </td>
            <td>
                <a href="meals?action=delete&id=<c:out value="${meal.id}"></c:out>">Delete</a>
            </td>
        </tr>

    </c:forEach>

    </table>
</body>
</html>
