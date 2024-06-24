<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../include/header.jsp" %>

<section>
    <%@ include file="sub_image_menu.jsp" %>
    <article style="display:flex; flex-direction: column">
        <h2>회사 소개</h2>
        Shoes Shop 은 어쩌고 저쩌고....

        <h2>오시는 길</h2>
        서울특별시 종로구 인사동길 12 대일빌딩<br>
        전화 : 02-722-XXXX <br>
        팩스 : 02-722-XXXX <br>

        <h3>전철</h3>
        1호선 종각역 또는 1,5호선 종로3가역

        <h3>버스</h3>

        <h3>위치안내</h3>

        <!-- * 카카오맵 - 지도퍼가기 -->
        <!-- 1. 지도 노드 -->
        <div id="daumRoughmapContainer1719190906060" class="root_daum_roughmap root_daum_roughmap_landing"></div>

        <!--
            2. 설치 스크립트
            * 지도 퍼가기 서비스를 2개 이상 넣을 경우, 설치 스크립트는 하나만 삽입합니다.
        -->
        <script charset="UTF-8" class="daum_roughmap_loader_script" src="https://ssl.daumcdn.net/dmaps/map_js_init/roughmapLoader.js"></script>

        <!-- 3. 실행 스크립트 -->
        <script charset="UTF-8">
            new daum.roughmap.Lander({
                "timestamp" : "1719190906060",
                "key" : "2jrxd",
                "mapWidth" : "640",
                "mapHeight" : "360"
            }).render();
        </script>
    </article>
    </div>
</section>
<%@ include file="../include/footer.jsp" %>