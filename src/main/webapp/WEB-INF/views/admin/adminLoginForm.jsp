<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Insert title here</title>
    <link rel="stylesheet" href="/css/admin.css">
    <script src="/script/admin.js"></script>
</head>
<body>
<div id="wrap">
    <article>
        <form method="post" action="adminlogin" name="loginForm">
            <h2>Admin LogIn</h2>
            <div class="field">
                <label>Admin ID</label><input name="adminid" type="text" />
            </div>
            <div class="field">
                <label>Password</label><input name="pwd" type="password" >
            </div>
            <div class="btn">
                <input type="submit" value="LOGIN">
            </div>
            <div style="font-size:80%; font-weight:bold">${message} </div>
        </form>
    </article>
</div>
</body>
</html>