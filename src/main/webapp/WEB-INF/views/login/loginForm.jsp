<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../header.jsp"%>
<!-- 본문시작 -->
<div class="subTit">
	<h2>로그인</h2>
</div>
<div class="container loginWrap">
	<div class="login">
		<form  action="/login/login.do" method="post">
			<input type="text" class="email" name="email" placeholder="이메일" required><br> 
			<input type="password" class="pwd" name="pw" placeholder="비밀번호" required><br> 
			<div class="pwck_input_re_2">아이디 또는 비밀번호가 일치하지 않습니다.</div>
			<p style="text-align:left; margin-top:15px;"><label><input type="checkbox" name="login_state" checked="checked" disabled="disabled"> 로그인 상태 유지</label></p>
			 <input type="submit" value="로그인">
		</form>
	
		<ul class="login_btn">
			<li><a href="#">아이디 찾기</a></li>
			<li><a href="#">비밀번호 재설정</a></li>
			<li><a href="/join/join.do">회원가입</a></li>
		</ul>
	
		<ul class="sns">
			<li><a href="#"><img src="../images/naver.png"></a></li>
			<li><a href="#"><img src="../images/kakao.png"></a></li>
			<li><a href="#"><img src="../images/facebook.png"></a></li>
			<li><a href="#"><img src="../images/google.png"></a></li>
		</ul>
	</div><!-- //login -->
	
</div><!-- //container -->
<!-- 본문끝 -->
<%@ include file="../footer.jsp"%>
