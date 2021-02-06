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


<br/>
vasya: <c:out value="${id}"/>

<ul>
    <table>
        <tr class="yellow">
            <td>Date</td>
            <td>Description</td>
            <td>Calories</td>
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
        <tr>
            <td> <% out.println(mealTo.getDateTime().toLocalDate() + " " + mealTo.getDateTime().toLocalTime());  %> </td>
            <td> <% out.println(mealTo.getDescription());%> </td>
            <td> <% out.println(mealTo.getCalories());%> </td>
            <td><a href='update?id=${id}'>Update</a></td>
            <td><a href="delete">Delete</a></td>
            <%
                }
            %>
        </tr>
    </table>
</ul>



</body>
</html>
