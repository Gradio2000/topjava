<%@ page import="ru.javawebinar.topjava.model.Role" %>
<%@ page import="java.util.Set" %><%--
  Created by IntelliJ IDEA.
  User: aleksejlaskin
  Date: 20.03.2021
  Time: 17:17
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<meta charset="utf-8">
<html>
<jsp:include page="fragments/headTag.jsp"/>
<head>
    <jsp:include page="fragments/bodyHeader.jsp"/>
</head>
<body>

<section>
    <h3><a href="index.html">Home</a></h3>
    <hr>
    <h2>${param.action == 'create' ? 'Create user' : 'Edit user'}</h2>
    <jsp:useBean id="user" type="ru.javawebinar.topjava.model.User" scope="request"/>
    <form method="post" action="user_edit">

        <input type="hidden" name="id" value="${user.id}">
        <dl>
            <dt>User name:</dt>
            <dd><input type="text" value="${user.name}" size=40 name="name" required></dd>
        </dl>
        <dl>
            <dt>email:</dt>
            <dd><input type="email" value="${user.email}" size=40 name="name" required></dd>
        </dl>
        <dl>
            <dt>Role:</dt>
            <dd><input type="text" value="${user.roles}" name="roles" required></dd>
        </dl>
        <button type="submit">Save</button>
        <button onclick="window.history.back()" type="button">Cancel</button>
    </form>
</section>
</body>
</html>

