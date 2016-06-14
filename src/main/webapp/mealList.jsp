<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://topjava.javawebinar.ru/functions" %>
<html>
<head>
    <title>Meal list</title>
    <style>
        .normal {
            color: green;
        }

        .exceeded {
            color: red;
        }
    </style>
</head>
<body>
<section>
    <h2><a href="index.html">Home</a></h2>
    <h3>Meal list of user: ${user}</h3>


    <form method="get" action="meals">
        <input type="hidden" name="action" value="filter">
        <table border="1" cellpadding="8" cellspacing="0">
            <tr align="right">
                <td>
                    From Date: <input type="date" name="fromDate" value="${fromDate}" style="width: 140px;"></dd>
                </td>
                <td>
                    To Date: <input type="date" name="toDate" value="${toDate}" style="width: 140px;"></dd>
                </td>
            </tr>
            <tr align="right">
                <td>
                    From Time: <input type="time" name="fromTime" value="${fromTime}" style="width: 140px;"></dd>
                </td>
                <td>
                    To Time: <input type="time" name="toTime" value="${toTime}" style="width: 140px;"></dd>
                </td>
            </tr>
            <tr align="right">
                <td colspan="2">
                    <button type="submit">Filter</button>
                </td>
            </tr>
        </table>
    </form>


    <a href="meals?action=create">Add Meal</a>
    <hr>
    <table border="1" cellpadding="8" cellspacing="0">
        <thead>
        <tr>
            <th>Date</th>
            <th>Description</th>
            <th>Calories</th>
            <th></th>
            <th></th>
        </tr>
        </thead>
        <c:forEach items="${mealList}" var="meal">
            <jsp:useBean id="meal" scope="page" type="ru.javawebinar.topjava.model.to.UserMealWithExceed"/>
            <tr class="${meal.exceed ? 'exceeded' : 'normal'}">
                <td>
                        <%--${meal.dateTime.toLocalDate()} ${meal.dateTime.toLocalTime()}--%>
                        <%--<%=TimeUtil.toString(meal.getDateTime())%>--%>
                        ${fn:formatDateTime(meal.dateTime)}
                </td>
                <td>${meal.description}</td>
                <td>${meal.calories}</td>
                <td><a href="meals?action=update&id=${meal.id}">Update</a></td>
                <td><a href="meals?action=delete&id=${meal.id}">Delete</a></td>
            </tr>
        </c:forEach>
    </table>
</section>
</body>
</html>