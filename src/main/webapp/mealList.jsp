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

    <table border="2px">

        <tr>
            <td>Date</td>
            <td>Description</td>
            <td>Calories</td>
        </tr>

    <c:forEach items="${requestScope.mealList}" var="meal">
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
        </tr>

    </c:forEach>

    </table>

</body>
</html>
