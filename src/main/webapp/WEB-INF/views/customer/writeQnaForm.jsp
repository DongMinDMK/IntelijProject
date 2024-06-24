<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../include/header.jsp" %>

<section>

    <%@ include file="sub_image_menu.jsp" %>
    <article>
        <form action="writeQna" method="post" name="fornm">
            <h2> Q & A Write</h2>
            <div class="field" >
                <label>작성자</label>
                <input type="text" name="userid" value="${loginUser.userid}" readonly />
            </div>
            <div class="field">
                <label>Secret Mode</label>
                <div style="display: flex; justify-content: flex-start;">
                    <input name="secret" type="checkbox" value="Y" onchange="enable()"> 비밀글로 하기
                    <input name="pass" type="password" size="15" style="flex:4;" disabled><br>
                </div>
            </div>
            <div class="field" >
                <label>제목</label><input type="text" name="subject">
            </div>
            <div class="field" >
                <label>질문내용</label>
                <textarea name="content" rows="10"  cols="85"></textarea>
            </div>
            <div class="field" >
                ${message}
            </div>
            <div class="btn" >
                <input type="submit" value="작성하기" />
            </div>
        </form>
    </article>

    </div>

</section>

<%@ include file="../include/footer.jsp" %>