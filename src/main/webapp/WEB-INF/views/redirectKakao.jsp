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
    access_token : ${authInfo.access_token}<br>
    token_type : ${authInfo.token_type}<br>
    refresh_token : ${authInfo.refresh_token}<br>
    expires_in : ${authInfo.expires_in}<br>
    scope : ${authInfo.scope}<br>
</body>
</html>