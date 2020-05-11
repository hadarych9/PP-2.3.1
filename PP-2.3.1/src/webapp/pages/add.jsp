<%--
  Created by IntelliJ IDEA.
  User: ale-k
  Date: 11.05.2020
  Time: 14:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    request.setCharacterEncoding("UTF-8");
%>
<html>
<head>
    <title>Добавление пользователя</title>
</head>
<body>
<form method="post">
    <label>Логин:
        <input type="text" name="name"><br/>
    </label>

    <label>Пароль:
        <input type="password" name="password"><br/>
    </label>

    <label>Возраст:
        <input type="number" name="age"><br/>
    </label>

    <button type="submit">Сохранить</button>
</form>
</body>
</html>
