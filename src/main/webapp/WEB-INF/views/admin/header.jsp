<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Insert title here</title>
    <link rel="stylesheet" href="/css/admin.css" />
    <script src="/script/admin.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
    <script type="text/javascript">
        $(function(){
            $("#myButton").click(function(){
                var formselect = $("#fileupForm")[0]; // 지목된 폼을 변수에 저장
                var formdata = new FormData(formselect); // 전송된 폼객체에 폼과 안의 데이터(이미지)를 저장

                $.ajax({
                    url:"<%=request.getContextPath()%>/fileup",
                    type:"POST",
                    enctype:"multipart/form-data",
                    async: false,
                    data: formdata,
                    timeout: 10000,
                    contentType: false,
                    processData: false,
                    success: function(data){ //controller 에서 리턴된 해시맵이 성공했을때의 함수 파라미터로 data 로 전달됩니다.
                        if(data.STATUS == 1){ //성공
                            $("#filename").append("<div>"+data.SAVEFILENAME+"</div>");
                            $("#image").val(data.IMAGE);
                            $("#savefilename").val(data.SAVEFILENAME);
                            $("#filename").append("<img src='product_images/" + data.SAVEFILENAME + "' height='150'/>");
                        }
                    },
                    error: function(){
                        window.alert("실패");
                    }
                });
            });
        });
    </script>
</head>
<body>

<div id="wrap">
    <header>
        <div id="logo">
            <img style="width:800px" src="/images/bar_01.gif">
            <img src="/images/text.gif">
            <input class="btn" type="button" value="logout"
                   onClick="location.href='adminLogout'">
        </div>

    </header>