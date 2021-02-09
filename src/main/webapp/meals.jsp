<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.util.List" %>
<%@ page import="ru.javawebinar.topjava.model.MealTo" %><%--
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
<br/>
<ul>
    <table border="2">
        <tr>
            <th>Date</th>
            <th>Description</th>
            <th>Calories</th>
            <th></th>
            <th></th>
        </tr>
        <%
            List<MealTo> mealsToList = (List<MealTo>) request.getAttribute("mealsList");
            for (MealTo mealTo : mealsToList){
        %>
        <c:set var="id">
            <%
                int id = mealTo.getId();
                out.println(id);
            %>
        </c:set>
        <c:set var="excess">
            <%
//                boolean ex = mealTo.isExcess();
//                out.printl(ex);
            %>
        </c:set>

        <tr style="color: ${excess.equals("true") ? 'red' : 'green'}">
            <td><% out.println(mealTo.getDateTime().toLocalDate() + " " + mealTo.getDateTime().toLocalTime());  %> </td>
            <td> <% out.println(mealTo.getDescription());%> </td>
            <td> <% out.println(mealTo.getCalories());%> </td>
            <td><a href='update?id=${id}'>Update</a></td>
            <td><a href='delete?id=${id}'>Delete</a></td>
            <%
                }
            %>
        </tr>
    </table>
</ul>


</body>
</html>
