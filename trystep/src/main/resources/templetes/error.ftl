<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" xmlns:th="http://www.thymeleaf.org"
      xmlns:sec="http://www.thymeleaf.org/thymeleaf-extras-springsecurity3">
<head>
    <title>Hello World!</title>
</head>
<body>
<h1 th:inline="text">Error:</h1>
<p th:text="${error}">${error}</p>
<!-- 页面中获取不到elapse数据,因为是在handler中处理,elapsetime是处理完成之后设置的-->
</body>
</html>