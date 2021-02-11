<%@ page import="ru.javawebinar.topjava.model.Meal" %>
<%@ page import="ru.javawebinar.topjava.util.MealsUtil" %>
<%@ page import="java.util.List" %>
<%@ page import="java.time.LocalDate" %>
<%@ page import="java.time.LocalDateTime" %>
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
<%@page pageEncoding="UTF-8"%>

<html>
<%request.setCharacterEncoding("UTF-8");%>
<head>
    <meta charset="UTF-8">
    <title>User Form</title>
</head>
<body>
ADD MEAL

<br><br/>
<form action="action" method="POST">
    <input name="action" type="hidden" value="add">
    Date&Time: <input name="date" type="datetime-local"/>
    <br><br>
    Calories: <input name="calories"/>
    <br><br>
    Description: <select name="description">
    <option>Завтрак</option>
    <option>Обед</option>
    <option>Ужин</option>
</select>
    <br><br>
    <input type="button" value="Cancel" onclick="window.history.back()" />
    <input type="submit" value="Add meal" />
</form>
</body>
</html>