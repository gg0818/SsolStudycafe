<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ include file="../header.jsp" %>
<script src="../js/calendar.js"></script>
<script src="../js/res_script.js"></script>
<!-- 본문시작 -->
	<div class="subTit">
		<h2>실시간예약 </h2>
	</div>	
	
	<div class="container reserveCont">
		<div class="inner">
			<div class="reserveDiv">
				<div class="slcD">
					<div class="nameSlc">
						<span>지역</span>
						<select class="location">
							<option value="none">지역을 선택해주세요</option>
						</select>
					</div>	
					<div class="nameSlc">
						<span>지점명</span>
						<select class="store">
							<option value="none">지점을 선택해주세요</option>
						</select>
					</div>	
				</div><!-- //slcD -->
				<div class="accoD">
					<div class="accoBox on">
						<a href="javascript:" class="accoTit">좌석 선택하기</a>
						<div class="accoCont" style="display:block;"><!-- 지역, 지점 선택하기 전에 hide 클래스 붙음 -->
							<div class="seatInfo">
								<span>선택가능</span>
								<span>선택함</span>
								<span>선택불가</span>
							</div>
							<div class="seatDiv">
								<div class="deskD">
									<!-- <a href="javascript:" data-val="desk_1" class="desk seat"><span>좌석1</span></a> -->
									<!-- 클래스 no 붙으면 선택 불가 / max 31 -->
								</div>
								
								<div class="roomD">
									<!-- <a href="javascript:" class="room seat no" data-val="room_1"><span>ROOM1</span></a> -->
								</div>
								<div class="lockerLine">사물함</div>
							</div><!-- //seatDiv -->
						</div><!-- //accoCont -->
					</div>
					<div class="accoBox">
						<a href="javascript:" class="accoTit">사물함 선택하기</a>
						<div class="accoCont">
							<div class="seatInfo">
								<span>선택가능</span>
								<span>선택함</span>
								<span>선택불가 <em>(표시된 날짜까지 예약된 사물함입니다.)</em></span>
							</div>
							<div class="lockerDiv">
								<div class="lockerD"></div>
							</div>
						</div>
					</div>
				</div><!-- //accoD -->
				<div class="slcDeTail">
					<div>
						<p class="location"><span>지역</span><span class="data"></span></p>
						<p class="store"><span>지점</span><span class="data"></span></p>
						<p class="seat"><span>좌석명</span><span class="data"></span></p>
					</div>
					<div>
						<p class="date"><span>날짜</span><span class="data"></span></p>
						<p class="time"><span>시간</span><span class="data"></span></p>
					</div>
					<div>
						<p class="total">총<span class="pay">0</span>원</p>
					</div>
					<button type="button" name="button" class="resBtn" id="resBtn" onclick="javascript:resScript.resAddClick(); return false;">
						<div><img src="../images/cart_img.png" alt=""><span>예약추가</span></div>
					</button>
				</div>
			</div><!-- //reserveDiv -->
			<div class="rightFix">
				<div class="cal">
					<input type="hidden" name="today" value="" id="today">
					<div class="calSel">
						<select id="year" name="year">
						</select>
						<select id="month" name="month">
						</select> 
					</div>						
					<div class="calTable">
						<table>
							<thead>
								<tr>
									<th class="red">일</th>
									<th>월</th>
									<th>화</th>
									<th>수</th>
									<th>목</th>
									<th>금</th>
									<th>토</th>
								</tr>
							</thead>
							<tbody>
								<%
									for(int i=0; i<6; i++){
								%>
								<tr>
								<%
										for(int j=0; j<7; j++){
								%>
									<td><a href="javascript:" name="day" class="day" alt=""></a></td>
								<%	
										}
								%>
								</tr>
								<%	
									}
								%>	
							</tbody>
						</table>
					</div>	
				</div><!-- // cal -->
				<div class="tabD">
					<div class="tab">
						<a href="javascript:" class="on"><span>좌석</span></a>
						<a href="javascript:"><span>사물함</span></a>
					</div>
					<div class="tabCont">
						<div class="resTime on">
							<p class="info">예약시간 <span>(시간은 복수선택 가능합니다)</span></p>
							<div class="none"><span>좌석을 선택해 주세요.</span></div>
							<div class="scrollD"><!-- a태그에 class="no" 붙으면 예약완료 -->
								<%
									for(int i=8; i<21; i++){
										String j= Integer.toString(i);
										String j2 = Integer.toString(i+1);
										if(i<10) j='0'+Integer.toString(i);
										if(i+1 < 10){
											j2='0'+Integer.toString(i+1);
										}
								%>
								<a href="javascript:" data-time="<%=j%>" ><span class="mark">예약가능</span><span><%=j%>:00 ~ <%=j2%>:00</span></a>
								<%			
									}
								%>
							</div>
						</div><!-- //div -->
						<div class="resDay">
							<p class="info">예약날짜</p>
							<div class="none"><span>사물함을 선택해 주세요.</span></div>
							<div class="scrollD">
								<a href="javascript:" data-week="4" ><span class="mark">예약가능</span><span></span></a>
								<a href="javascript:" data-week="8" ><span class="mark">예약가능</span><span></span></a>
								<a href="javascript:" data-week="12" ><span class="mark">예약가능</span><span></span></a>
							</div>
						</div><!-- //resDay -->
					</div><!-- //tabCont -->
				</div><!-- //tabD -->
			</div><!-- //rightFix -->
			
		</div><!-- //inner -->
		
		<div class="resCheck inner">
			<p class="tit">예약확인</p>
			<div class="listD">
				<!-- <div class="list">
					<div>
						<p class="num">1</p>
						<p class="location">서울</p>
						<p class="store">목동직영점</p>
						<p class="seat">좌석12</p>
						<p class="date">2021-12-09</p>
						<p class="time">18:00 ~ 19:00</p>
						<p class="pay">3,000원</p>
					</div>
					<a href="javascript:" class="del">삭제</a>
				</div> -->
			</div>
			<div class="resTotD">
				<p class="tit">결제금액 확인</p>
				<div class="resTot seatT">
					<p>좌석 총 개수 : <span class="num">0</span></p>
					<p>총 금액(기본가 시간당 1,500원) : <span class="pay">0</span>원</p>
					<p>할인 적용된 총 금액 : <span class="disPay">0</span>원</p>
				</div>
				<div class="resTot roomT">
					<p>ROOM 총 개수 : <span class="num">0</span></p>
					<p>총 금액(기본가 시간당 6,000원) : <span class="pay">0</span>원</p>
					<p>할인 적용된 총 금액 : <span class="disPay">0</span>원</p>
				</div>
				<div class="resTot lockerT">
					<p>사물함 총 개수 : <span class="num">0</span></p>
					<p>총 금액 : <span class="pay">0</span>원</p>
				</div>
			</div>
		</div><!-- //resCheck -->
		
		<div class="totalPay">
			<%
				if (session.getAttribute("uid")==null) {
					
			%>
				<a href="javascript:void(0)" onclick="return alert('로그인 후 이용가능합니다.'); false;">
					<p>총 <span class="num">0</span>건</p><p><span class="pay">0</span>원</p>
					결제하기
				</a>	
			<%}else{
				
			%>
				
				<a href="javascript:void(0)" onclick="return resScript.resChk();">
					<p>총 <span class="num">0</span>건</p><p><span class="pay">0</span>원</p>
					결제하기
				</a>
			<%	
			}
			%>
		</div>
	</div><!-- //reserveCont -->
	
	<div class="layerPop resPop">
		<div class="popDiv">
			<p class="tit">예약 내역</p>
			<div class="popCont">
				<div>
					 <p class="tit1"><span>서울</span><span>홍대점</span></p>
					 <div class="listD">
					 	<div class="list">
					 		<div class="date">2021-12-27 (월)</div>
					 		<div class="info"><span>좌석12</span> <p><span>예약시간 : </span><span>09:00~10:00, 10:00~11:00</span></p> </div>
					 		<div class="tot"><span>총 3시간</span><span>8,000원</span></div>
					 	</div>
					 </div>
					 <p class="total">총 예약 건수 <span>1건</span></p>
					 <p class="totalPay">결제 금액 <span>1,500원</span></p>
				</div>
				<div>
					<p class="tit1"><span>이용자 정보</span></p>
					<p><input type="text" readonly value="${uid}"></p>
				</div>
				<div>
					<p class="tit1"><span>결제 수단 선택</span></p>
					<div class="chkD">
						<p><input type="checkbox" name="payType" id="card" disabled value="card" ><label for="card">신용카드</label></p>
						<p><input type="checkbox" name="payType" id="account" checked value="account"><label for="account">무통장입금</label></p>
					</div>
					<p class="infoTxt">현재 무통장입금을 통한 예약만 가능합니다. <br>이용에 불편을 드려 죄송합니다.</p>
				</div>
				<div class="btnD">
					<a href="javascript:void(0)" class="btn" onclick="return resScript.resFinalChk('${uid}');"><span>예약하기</span></a>
				</div>
			</div>
		</div>
		<a href="javascript:" class="xbtn">닫기</a>
	</div>
<!-- 본문끝 -->

<%@ include file="../footer.jsp" %>