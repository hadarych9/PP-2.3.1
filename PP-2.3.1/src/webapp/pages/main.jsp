<%--
  Created by IntelliJ IDEA.
  User: ale-k
  Date: 11.05.2020
  Time: 14:28
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    request.setCharacterEncoding("UTF-8");
    String result = request.getParameter("result");
%>
<html>
<head>
    <title>Главная</title>
</head>
<body>
<%
    if (result != null) {
        out.println(result);
    }
%>
<table border="1" cellpadding="5">
    <thead>
    <tr>
        <th>Id</th>
        <th>Логин</th>
        <th>Возраст</th>
        <th>Редактирование</th>
        <th>Удаление</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${userData}" var="user">
        <tr>
            <td> ${user.id} </td>
            <td> ${user.name} </td>
            <td> ${user.age} </td>
            <td>
                <form action="/update" method="get">
                    <input type="hidden" name="id" value=${user.id}>
                    <button type="submit">Изменить</button>
                </form>
            </td>
            <td>
                <form action="/delete" method="get">
                    <input type="hidden" name="id" value=${user.id}>
                    <button type="submit">Удалить</button>
                </form>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<br/>
<form action="/add">
    <button type="submit">Добавить пользователя</button>
</form>
<form action="/drop" method="get">
    <button type="submit">Очистить таблицу</button>
</form>
</body>
</html>
