<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.util.List" %>
<%@ page import="ru.javawebinar.topjava.model.MealTo" %>
<%--
  Created by IntelliJ IDEA.
  User: aleksejlaskin
  Date: 05.02.2021
  Time: 19:28
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Users list</title>
</head>
<body>
My meals list work already!
<br><br/>
<a href="add">Create new meal</a>
<br><br/>
<style type="text/css">
    TABLE {
        border-collapse: collapse; /* Убираем двойные линии между ячейками */
        width: 600px; /* Ширина таблицы */
    }
    TD {
        border: 1px solid black; /* Параметры рамки */
        text-align: center; /* Выравнивание по центру */
        padding: 4px; /* Поля вокруг текста */
    }
    TH {
        border: 2px solid black; /* Параметры рамки */
        background: rgba(255, 204, 0, 0); /* Цвет фона ячейки */
        height: 40px; /* Высота ячеек */
        vertical-align: center; /* Выравнивание по нижнему краю */
        padding: 0; /* Убираем поля вокруг текста */
    }
</style>

    <table>
        <tr>
            <th>Date</th>
            <th>Description</th>
            <th>Calories</th>
            <th></th>
            <th></th>
        </tr>
        <c:forEach items="${mealsList}" var="meal">
            <tr style="color: ${meal.excess ? 'red' : 'green'}">
            <td><c:out value="${meal.formattedDateTime}"/></td>
            <td><c:out value="${meal.description}"/></td>
            <td><c:out value="${meal.calories}"/></td>
            <td><a href='update?id=${meal.id}'>Update</a></td>
            <td><a href='action?id=${meal.id}'>Delete</a></td>
        </tr>
        </c:forEach>
    </table>
</body>
</html>
