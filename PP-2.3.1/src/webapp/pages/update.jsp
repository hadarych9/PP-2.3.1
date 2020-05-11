<%--
  Created by IntelliJ IDEA.
  User: ale-k
  Date: 11.05.2020
  Time: 16:04
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%
    request.setCharacterEncoding("UTF-8");
%>
<jsp:useBean id="user" scope="request" type="CRUD.model.User"/>
<html>
<head>
    <title>Добавление пользователя</title>
</head>
<body>
<form method="post">
    <label>Логин:
        <input type="text" name="name" value="${user.name}"><br/>
    </label>

    <label>Пароль:
        <input type="password" name="password" value="${user.password}"><br/>
    </label>

    <label>Возраст:
        <input type="number" name="age" value="${user.age}"><br/>
    </label>

    <button type="submit">Сохранить</button>
</form>
</body>
</html>
