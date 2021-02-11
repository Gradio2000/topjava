<%@ page import="ru.javawebinar.topjava.model.Meal" %>
<%@ page import="ru.javawebinar.topjava.util.MealsUtil" %>
<%@ page import="java.util.List" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@page pageEncoding="UTF-8"%>
<%--
  Created by IntelliJ IDEA.
  User: aleksejlaskin
  Date: 05.02.2021
  Time: 23:21
  To change this template use File | Settings | File Templates.
--%>


<html>
<%request.setCharacterEncoding("UTF-8");%>
<head>
    <meta charset="UTF-8">
    <title>User Form</title>
</head>
<body>
EDIT MEAL
<%
    Meal mealForUpdate = null;
    List<Meal> mealList = MealsUtil.mealList;
    for (Meal meal : mealList){
        if (meal.getId() == Integer.parseInt(request.getParameter("id"))){
            mealForUpdate = meal;
        }
    }
    request.setAttribute("meal", mealForUpdate);
%>


<br><br/>
<form action="action" method="POST">
    <input name="action" value="update" type="hidden">
    <input name="id" type="hidden" value="${meal.getId()}">
    <br><br/>
    Date&Time: <input name="date" type="datetime-local" value="${meal.getDateTime()}"/>
    <br><br>
    Calories: <input name="calories" value="${meal.getCalories()}"/>
    <br><br>
    Description: <select name="description">
    <option>Завтрак</option>
    <option>Обед</option>
    <option>Ужин</option>
    <option selected>${meal.getDescription()}</option>
</select>
    <br><br>
    <input type="button" value="Cancel" onclick="window.history.back()" />
    <input type="submit" value="Update" />
</form>
</body>
</html>