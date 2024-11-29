
    // 주기적으로 알림을 가져오는 함수
    function fetchNotifications() {
    fetch('/notifications') // 서버의 알림 API 엔드포인트 호출
        .then(response => response.json())
        .then(data => {
            const notificationDiv = document.getElementById('notifications');
            notificationDiv.innerHTML = ''; // 기존 알림 제거

            if (data && data.length > 0) {
                data.forEach(notification => {
                    const p = document.createElement('p');
                    p.textContent = notification.message; // 알림 메시지 추가
                    notificationDiv.appendChild(p);
                });
                document.getElementById('notificationModal').style.display = 'block';
                document.getElementById('modalBackground').style.display = 'block';
                // 알림 모달 띄우기
                const modal = document.getElementById('notificationModal');
                modal.style.display = 'block';
            }
        })
        .catch(error => console.error('Error fetching notifications:', error));
}

    // 주기적으로 실행 (예: 10초마다 실행)
    setInterval(fetchNotifications, 10000);

    // 알림 모달 닫기
    function closeModal() {
    const modal = document.getElementById('notificationModal');
    const background = document.getElementById('modalBackground');
    modal.style.display = 'none';
    background.style.display = 'none';
}