<%--
  Created by IntelliJ IDEA.
  User: vladimir
  Date: 07.06.2016
  Time: 16:06
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>UserMeal</title>
</head>
<body>
    <c:choose>
        <c:when test="${meal.id!=null}">
            <h1>Edit UserMeal</h1>
        </c:when>
        <c:otherwise>
            <h1>Add UserMeal</h1>
        </c:otherwise>
    </c:choose>

    <form method="GET" action="meals" name="EditUserMeal">

            <input type="hidden" name="action" value="edit" />

        Id: <input type="text"
                   readonly="readonly"
                   name="id"
                   value="<c:out value="${meal.id}"></c:out>" />

        DateTime: <input type="text"
                   name="dateTime"
                   value="<c:out value="${meal.formatDateTime}"></c:out>" />

        Description: <input type="text"
                         name="description"
                         value="<c:out value="${meal.description}"></c:out>" />

        Calories: <input type="text"
                         name="calories"
                         value="<c:out value="${meal.calories}"></c:out>" />

        <input type="submit" value="Сохранить" />

    </form>


</body>
</html>
