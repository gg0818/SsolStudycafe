<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ include file="../header.jsp" %>
<!-- 본문시작 -->
	<script type="text/javascript" src="https://openapi.map.naver.com/openapi/v3/maps.js?ncpClientId=h7psz5flvj"></script>
	<div class="subTit">
		<h2>지점찾기</h2>
	</div>
	<div class="container">
		<div class="mapDiv">
			<div class="mapList">
				<form name="frm" action="place.do">
					<div class="search">
						<input type="text" name="keyword" id="keyword" placeholder="매장명을 입력해주세요." >
						<input type="submit" class="btn">
					</div>
				</form>
				<p class="tot"><span>${fn:length(list)}개의 매장</span>이 검색되었습니다.</p>
				<div class="listD">
					<c:forEach var="dto" items="${list}">
						<div class="list">
							<a href="javascript:">
								<input type="hidden" value="${dto.latitude}" class="lat" >
								<input type="hidden" value="${dto.longitude}" class="lon" >
								<p class="tit">${dto.store_name}</p>
								<div class="txtD">
									<p class="addr">${dto.store_address} </p>
									<p class="time">운영 시간 : ${dto.operating_hours}</p>
									<p class="tel">전화번호 : ${dto.tel}</p>
									<p class="review">
										<c:choose>
											<c:when test="${dto.score_avg == 5}"><span class="star fiv"></span></c:when>
											<c:when test="${dto.score_avg == 4}"><span class="star for"></span></c:when>
											<c:when test="${dto.score_avg == 3}"><span class="star thr"></span></c:when>
											<c:when test="${dto.score_avg == 2}"><span class="star two"></span></c:when>
											<c:when test="${dto.score_avg == 1}"><span class="star one"></span></c:when>
											<c:otherwise><span class="star"></span></c:otherwise>
										</c:choose>
										<!-- class : one, two, thr, for, fiv -->
										<span class="num">(리뷰 : ${dto.review_tot}건)</span>
									</p>
								</div>
							</a>
						</div>	
					</c:forEach>
				</div>
			</div>
			<div class="mapView">
				<div id="map"></div>	
			</div>
		</div>
	</div>

<!-- 본문끝 -->
<script>
	$(function(){
		initMap();
	});
	
	var infoWindows=[];
	var infoWindow;
	
	function initMap(){
		var areaArr = []; // 지역(위도, 경도)
		var markers = []; // 마커
		var mapList=[];// 맵 목록
		
		var mapOptions = {
		    center: new naver.maps.LatLng(37.557654557627586,126.92444543999783), //처음 노출되는 위치 
		    zoom:12,
		    zoomControl: true,
	        zoomControlOptions: {
	            style: naver.maps.ZoomControlStyle.SMALL,
	            position: naver.maps.Position.TOP_RIGHT
	        }
		}
		var map = new naver.maps.Map('map', mapOptions);
		
		<c:forEach var="dto" items="${list}">
			areaArr.push({store_no:'${dto.store_no}', store_name:'${dto.store_name}', store_address:'${dto.store_address}', 
				operating_hours:'${dto.operating_hours}', tel:'${dto.tel}', latitude:${dto.latitude}, longitude:${dto.longitude},
				score_avg:${dto.score_avg}, review_tot:${dto.review_tot}
			});
		</c:forEach>
		
		
		for(var i=0; i<areaArr.length; i++){
			//마커 
			var marker = new naver.maps.Marker({
				position: new naver.maps.LatLng(areaArr[i].latitude, areaArr[i].longitude),
			    map: map,
			    icon: {
			        url: '../images/marker.png',
			        size: new naver.maps.Size(38, 48),
			        origin: new naver.maps.Point(0, 0),
			        anchor: new naver.maps.Point(19, 48)
			    },
			    animation: naver.maps.Animation.DROP
			});
			
			// 정보창
			var infoWindow = new naver.maps.InfoWindow({
				content:"<div class='mapInfo'><p>"+areaArr[i].store_name+
						"</p><p class='info'><span>매장주소 : </span><span>"+areaArr[i].store_address+
						"</span></p><p class='info'><span>운영시간 : </span><span>"+areaArr[i].operating_hours+
						"</span></p><p class='info'><span>전화번호 : </span><span>"+areaArr[i].tel+
						"</span></p><p><a href='javascript:void(0)' class='revBtn' onclick='javascript:document.placefrm.submit();'>리뷰보기</a></p>"+
						"<form name='placefrm' id='placefrm' action='placelist.do' method='GET' >"+
						"	<input type='hidden' id='store_no' name='store_no' value='"+areaArr[i].store_no+"'>"+
						"	<input type='hidden' id='store_name' name='store_name' value='"+areaArr[i].store_name+"'>"+
						"</form>"+
						"<a href='javascript:void(0)' class='infoXbtn' onclick='infoClose("+i+"); return false;'>닫기</a></div>",
				borderWidth: 0,
			    disableAnchor: true,
			    backgroundColor: 'transparent',
			    pixelOffset: new naver.maps.Point(0, -28),
			    clickable: true,

			});
			
			markers.push(marker);
			infoWindows.push(infoWindow);
			
		}
		
		function getClickHandler(seq){
			return function(e){
				var marker = markers[seq], infoWindow = infoWindows[seq]; 
				if(infoWindow.getMap()){
					infoWindow.close();
				}else{
					infoWindow.open(map, marker); // 표출 
					map.panTo(e.coord); //클릭 시 위치 센터
				}
			}
			
		}// getClickHandler()
		
		for(var i=0,ii=markers.length; i<ii; i++){
			naver.maps.Event.addListener(markers[i], 'click', getClickHandler(i)); // 클릭한 마커 핸들러	
		}
		
		$(".mapDiv .mapList .listD .list a:first-child").each(function(index){
			$(this).on("click", function(){
				var maplat = $(this).find(".lat").val();
				var maplon = $(this).find(".lon").val();
				var mapPos = new naver.maps.LatLng(maplat, maplon); 
				var marker = markers[index], infoWindow = infoWindows[index]; 
				if(infoWindow.getMap()){
					infoWindow.close();
				}else{
					infoWindow.open(map, marker); // 표출 
					map.panTo(mapPos);
				}
			});
		});
		
		
		
	}// initMap()
	
	
	function infoClose(index){
		infoWindow = infoWindows[index];
		infoWindow.close();
	}// infoClose()
	
</script>



<%@ include file="../footer.jsp" %>