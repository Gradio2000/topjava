
<%@ page import="java.util.List" %>
<%@ page import="ru.javawebinar.topjava.model.MealTo" %><%--
  Created by IntelliJ IDEA.
  User: aleksejlaskin
  Date: 05.02.2021
  Time: 19:28
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<% String c;
    xmlns:c="http://java.sun.com/jsp/jstl/core"; %>

<html>
<head>
    <title>Users list</title>
</head>
<body>
My meals list work already!


<ul>
    <table>
        <tr class="yellow">
            <td>Date</td>
            <td>Description</td>
            <td>Calories</td>
        </tr>
        <%
            String s = "ddd";
            List<MealTo> mealsToList = (List<MealTo>) request.getAttribute("mealsList");
            for (MealTo mealTo : mealsToList){
                String a = "dd";
        %>

        <tr>
            <td> <% out.println(mealTo.getDateTime().toLocalDate() + " " + mealTo.getDateTime().toLocalTime());  %> </td>
            <td> <% out.println(mealTo.getDescription());%> </td>
            <td> <% out.println(mealTo.getCalories());%> </td>
            <td><a href='update/?id='>Update</a></td>
            <td><a href="delete">Delete</a></td>
            <%
                }
            %>
        </tr>
    </table>
</ul>


</body>
</html>
