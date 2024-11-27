// WebSocket 연결을 위한 JavaScript 코드
const socket = new SockJS('http://localhost:8082/ws');  // WebSocket 서버에 연결
const stompClient = Stomp.over(socket);

// WebSocket 연결
stompClient.connect({}, function (frame) {
    console.log('Connected: ' + frame);

    // 특정 유저에 대한 알림을 구독
    stompClient.subscribe('/topic/notifications', function (message) {
        var notificationDiv = document.getElementById("notifications");
        notificationDiv.innerHTML += "<p>" + message.body + "</p>";  // 알림 메시지 표시
    });
});
