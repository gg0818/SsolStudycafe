<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
<!DOCTYPE html>
<html lang="en" xmlns:th="http://www.thymeleaf.org"
	xmlns:sec="http://www.thymeleaf.org/extras/spring-security">
<link rel="stylesheet" href="../css/style.css">
<th:block
	th:replace="~{/layout/basic :: setContent(~{this :: content})}">
	<th:block th:fragment="content">

		<div class="container chatWrap">
			<div class="chatDiv">
				<div class="chatform">
					<div class="col-6 store">
						<a href="/home.do" class="logo">SSOL.</a>
						<p>${roomName}</p>
					</div>
					<div class="chat">
						<div id="msgArea" class="col"></div>
						<div class="col-6 chat_w">
							<div class="input-group mb-3">
								<textarea id="msg" class="form-control"></textarea>
								<!-- <input type="text" id="msg" class="form-control"> -->
								<div class="input-group-append">
									<button class="btn btn-outline-secondary" type="button"
										id="button-send">전송</button>
								</div>
							</div>
						</div><!-- //chat_w -->
					</div><!-- //chat -->
					<div class="col-6"></div>
				</div><!-- //charform -->
			</div><!-- //chatDiv  -->
		</div><!-- //container -->
		<style>
			
		</style>

		<script
			src="https://cdn.jsdelivr.net/npm/sockjs-client@1/dist/sockjs.min.js"></script>
		<script
			src="https://cdnjs.cloudflare.com/ajax/libs/stomp.js/2.3.3/stomp.min.js"></script>

	</th:block>
</th:block>

</html>
<script th:inline="javascript">
            $(document).ready(function(){

                var roomId = "${roomId}";
                var username ="${userName}";

                console.log( roomId + ", " + username);

                var sockJs = new SockJS("/stomp/chat");
                //1. SockJS를 내부에 들고있는 stomp를 내어줌
                var stomp = Stomp.over(sockJs);

                //2. connection이 맺어지면 실행
                stomp.connect({}, function (){
                   console.log("STOMP Connection")

                   //4. subscribe(path, callback)으로 메세지를 받을 수 있음
                   stomp.subscribe("/sub/chat/room/" + roomId, function (chat) {
                       var content = JSON.parse(chat.body);                    
                       var writer = content.writer;
                       var message= content.message;
                     
                       var str = '';

                       if(writer == username){
                           str = "<div class='col-6'>";
                           str += "<div class='alert alert-secondary'>";
                           str += "<p><span class='writer'>" + writer + "</span> <span class='msg'>" + message + "</span></p>";
                           str += "</div></div>";
                           $("#msgArea").append(str);
                       }
                       else{
                           str = "<div class='col-6'>";
                           str += "<div class='alert alert-warning'>";
                           str += "<p><span class='writer'>" + writer + "</span> <span class='msg'>" + message + "</span></p>";
                           str += "</div></div>";
                           $("#msgArea").append(str);
                       }

                   });

                   //3. send(path, header, message)로 메세지를 보낼 수 있음
                   stomp.send('/pub/chat/enter', {}, JSON.stringify({roomId: roomId, writer: username}))
                });

                $("#button-send").on("click", function(e){
                    var msg = document.getElementById("msg");

                    console.log(username + ":" + msg.value);
                    stomp.send('/pub/chat/message', {}, JSON.stringify({roomId: roomId, message: msg.value, writer: username}));
                    msg.value = '';
                });
            });
        </script>