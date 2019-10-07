<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Insert title here</title>
</head>
<body>
    nickname : ${user.nickname}<br>
    email : ${user.email}<br>
    ${access_token}
    <br>
    <a href="/logout/${access_token}">로그아웃</a>

</body>
</html>