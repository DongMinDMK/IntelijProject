<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../include/header.jsp" %>

<section>
    <%@ include file="sub_image_menu.jsp" %>
    <article>
        <form method="post" action="login" name="loginForm">
            <h2>Login</h2>
            <div class="field">
                <label>User ID</label><input name="userid" type="text" value="${loginUser.userid}"/>
            </div>
            <div class="field">
                <label>Password</label><input name="pwd" type="password" value="${loginUser.pwd}">
            </div>
            <div class="btn">
                <input type="submit" value="LOGIN">
                <input type="button" value="JOIN"  onclick="">
                <input type="button" value="FIND ID"  onclick="">
            </div>

            <div class="btn">
                <input type="button" value="Kakao" onClick="location.href='kakaoStart'" style="background-color: yellow; color:black;">
                <input type="button" value="Naver"  onclick="" style="background-color:green;">
                <input type="button" value="Google"  onclick="" style="background-color:red;">
            </div>
            <div style="font-size:80%; font-weight:bold">${message} </div>
        </form>
    </article>
    </div><!-- nav 와 article 의 부모 끝 -->
</section>

<%@ include file="../include/footer.jsp" %>