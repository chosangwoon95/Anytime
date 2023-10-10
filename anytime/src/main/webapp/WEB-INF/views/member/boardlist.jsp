<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="../common/header.jsp" />
<title>게시판 관리 - 애니타임</title>
<link type="text/css"
	href="${pageContext.request.contextPath}/resources/css/common/container.modal.css"
	rel="stylesheet">
<link type="text/css"
	href="${pageContext.request.contextPath}/resources/css/common/modal.css"
	rel="stylesheet">
<!-- 
<link type="text/css"
	href="${pageContext.request.contextPath}/resources/css/common/common.css"
	rel="stylesheet">
<link type="text/css"
	href="${pageContext.request.contextPath}/resources/css/common/common.partial.css"
	rel="stylesheet">
<link type="text/css"
	href="${pageContext.request.contextPath}/resources/css/common/container.community.css"
	rel="stylesheet">
 -->
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/resources/css/member/info/total.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/resources/css/member/info/boardlist.css">
<script src="${pageContext.request.contextPath}/resources/js/jquery-3.7.0.js"></script>
<script>
	if ("${result}" == "insertBoardSuccess") {
		alert("게시판이 생성되었습니다. 단체/학과 게시판은, 승인 심사에 통과되면 바로 이용가능합니다.");
	}
</script>
<style>
body {
	background-color: #EEEEF6 !important;
}
nav {
	border-bottom: 0 !important;
}
</style>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/common/modal.js"></script>
<style>
</style>
</head>
<body id="my">
	<div class="boardcontrol">
		<div class="navbar">
			<a class="hamburger">메뉴 열기</a>
			<h1>게시판 관리</h1>
		</div>
		<div class="container">
			<section id="boardSection">
				<h1>관리 중인 게시판</h1>
				<c:choose>
					<c:when test="${boardCount > 0 }">
						<c:forEach var="b" items="${boardlist}">
							<a href="/528552">${b.name } <c:if
									test="${b.approval =='N' }">
									<p>승인 심사 진행 중</p>
								</c:if>
							</a>
							<hr>
						</c:forEach>
					</c:when>
					<c:otherwise>
						<p class="noboard">관리 중인 게시판이 없어요</p>
					</c:otherwise>
				</c:choose>
				<a class="button createboard" href="#createboard"
					data-toggle="modal">게시판 생성하기</a>
			</section>
		</div>
	</div>

	<form id="createBoard" class="modal"
		style="margin-left: -200px; margin-top: -220.5px; display: none;">
		<a title="닫기" class="close"></a> <span class="new">새 게시판 만들기</span>
		<p>
			<label>단체/학과 게시판 생성에는 관리자 승인이 필요합니다.</label> <label id="boarddetail">유형</label>
		<div id="option">
			<label> <input type="radio" name="boardtype" value="2">
				단체
			</label> <label> <input type="radio" name="boardtype" value="3">
				학과
			</label> <label> <input type="radio" name="boardtype" value="4">
				커스텀
			</label>
		</div>
		</p>
		<p></p>
		<label id="boarddetail">게시판 이름</label> <input type="text" class="info"
			name="name" placeholder="게시판 이름" class="text boardinfo"> <label
			id="boarddetail">게시판 소개</label> <input type="text" class="info"
			name="content" placeholder="게시판 소개" class="text boardinfo">
		<div class="purpose" style="display: none;">
			<label id="boarddetail" class="purpose">개설 목적</label>
			<p id="explain" class="purpose">승인 심사시 필요</p>
		</div>
		<input type="text" name="purpose" placeholder="개설 목적"
			class="text info boardinfo purpose" style="display: none;"> <label
			class="custom-checkbox-label"> <input type="checkbox"
			class="custom-checkbox" name="anony"> <span
			class="custom-checkbox-image"></span>
		</label> <input type="submit" value="완료" class="button">
	</form>
	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
	<jsp:include page="../common/footer2.jsp" />
</body>
</html>