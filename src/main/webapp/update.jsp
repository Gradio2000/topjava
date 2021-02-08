<%@ page import="ru.javawebinar.topjava.model.MealTo" %>
<%@ page import="ru.javawebinar.topjava.model.Meal" %>
<%@ page import="ru.javawebinar.topjava.util.MealsUtil" %>
<%@ page import="java.util.List" %>
<%@ page import="java.time.LocalDateTime" %>
<%@ page import="java.time.LocalDate" %>
<%@ taglib prefix="s" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: aleksejlaskin
  Date: 05.02.2021
  Time: 23:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
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
    LocalDate dateTime = mealForUpdate.getDateTime().toLocalDate();
    String description = mealForUpdate.getDescription();
    int calories = mealForUpdate.getCalories();
%>
<c:set var="date">
    <%
        out.println(dateTime);
    %>
</c:set>
<c:set var="descr">
    <%
        out.println(description);
    %>
</c:set>
<c:set var="call">
    <%
        out.println(calories);
    %>
</c:set>


<br><br/>
<form action="${pageContext.request.contextPath}/action_update" method="POST">
    Date&Time: <input name="date" type="date" value="${date}"/>
    <br><br>
    Calories: <input name="calories" value="${call}"/>
    <br><br>
    Description: <select name="description">
    <option>Завтрак</option>
    <option>Обед</option>
    <option>Ужин</option>
    <option selected value="t4">${descr}</option>
</select>
    <br><br>
    <input type="button" value="Cancel" />
    <input type="submit" value="Update" />
</form>
</body>
</html>