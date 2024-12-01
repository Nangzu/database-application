async function loadPriceHistory(productId) {
    try {
        const response = await fetch(`/products/${productId}/price-history`);
        const priceHistory = await response.json();

        const messageArea = document.getElementById('priceHistoryMessage');
        const canvas = document.getElementById('priceHistoryChart');

        if (priceHistory.length === 0) {
            messageArea.textContent = "최저가 변화가 없는 제품입니다.";
            canvas.style.display = 'none'; // 차트 숨기기
        } else {
            messageArea.textContent ="";
            canvas.style.display = 'block'; // 차트만 보이게 하기

            const ctx = document.getElementById('priceHistoryChart').getContext('2d');
            const labels = priceHistory.map(record => new Date(record.priceMinTime).toLocaleDateString()); // X축 (날짜)
            const prices = priceHistory.map(record => record.priceMin); // Y축 (가격)

            // 가격 변화를 선 그래프 형태로 그리기
            new Chart(ctx, {
                type: 'line', // 선 그래프
                data: {
                    labels: labels,
                    datasets: [{
                        label: '최저가 변화',
                        data: prices,
                        borderColor: 'rgba(75, 192, 192, 1)', // 선 색상
                        backgroundColor: 'rgba(75, 192, 192, 0.2)', // 배경 색상
                        fill: true, // 선 아래 영역을 채움
                        tension: 0.1 // 선의 부드러움
                    }]
                },
                options: {
                    responsive: true,
                    scales: {
                        x: {},
                        y: {
                            beginAtZero: true, // Y축 시작점을 0으로 설정
                        }
                    }
                }
            });
        }
    } catch (error) {
        console.error('Failed to load price history:', error);
        document.getElementById('priceHistoryMessage').textContent = "가격 이력을 불러오는 중 오류가 발생했습니다.";
        document.getElementById('priceHistoryChart').style.display = 'none'; // 차트 숨기기
    }
}
document.addEventListener('DOMContentLoaded', () => {
    const productId = document.getElementById('priceHistoryArea').getAttribute('data-product-id');
    console.log("Loaded productId:", productId); // 디버깅 코드
    if (productId) {
        loadPriceHistory(productId);
    }
});
