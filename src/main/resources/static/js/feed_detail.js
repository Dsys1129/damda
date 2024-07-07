const feedImagePrefix = '/feeds_image/';
const coupleImagePrefix = '/couples_image/';

// 뒤로가기
function goBack() {
    history.back();
}

async function toggleHeart(feedId) {
    const heart = document.getElementById('heart');
    const isLiked = heart.src.endsWith('fill_heart.png');
    const url = `/feeds/likes/${feedId}`;

    try {
        const response = await fetch(url, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                liked: !isLiked
            })
        });

        if (response.ok) {
            heart.src = isLiked ? '../static/images/empty_heart.png' : '../static/images/fill_heart.png';
        } else {
            console.error('Failed to toggle like:', response.statusText);
        }
    } catch (error) {
        console.error('Error toggling like:', error);
    }
}

// 모달 열기 함수
function openModal() {
    document.getElementById('myModal').style.display = 'block';
}

// 모달 닫기 함수
function closeModal() {
    document.getElementById('myModal').style.display = 'none';
}

// 모달 외부 클릭 시 닫기
window.onclick = function (event) {
    const modal = document.getElementById('myModal');
    if (event.target == modal) {
        modal.style.display = 'none';
    }
}

async function confirmDelete() {
    const confirmation = confirm("정말 삭제하시겠습니까?");
    if (confirmation) {
        try {
            const response = await fetch(`/feeds/${feedId}`, {
                method: 'DELETE',
            });
            if (response.ok) {
                alert("피드가 성공적으로 삭제되었습니다.");
                window.location.href = '/'; // 삭제 후 피드 목록 페이지로 이동
            } else {
                alert("피드 삭제에 실패했습니다.");
            }
        } catch (error) {
            console.error('Error deleting feed:', error);
            alert("피드 삭제 중 오류가 발생했습니다.");
        }
    }
}

// 피드 상세 정보 가져오기
async function fetchFeedDetail(feedId) {
    try {
        const response = await fetch(`/feeds/${feedId}`);
        const data = await response.json();
        console.log(data)

        if (data && data.data) {
            const feedDetail = data.data;
            document.getElementById('feed-image').src = feedImagePrefix + feedDetail.image;
            document.getElementById('couple-image').src = coupleImagePrefix + feedDetail.coupleImage;
            document.getElementById('couple-name').textContent = feedDetail.coupleName;
            document.getElementById('location-address').textContent = '(' + feedDetail.address + ')';
            document.getElementById('feed-contents').innerHTML = feedDetail.contents;
            document.getElementById('location-name').textContent = feedDetail.photoSpotName;
            const year = feedDetail.contentsDate[0];
            const month = String(feedDetail.contentsDate[1]).padStart(2, '0');
            const day = String(feedDetail.contentsDate[2]).padStart(2, '0');
            const date = `${year}-${month}-${day}`;
            document.getElementById('feed-date').textContent = date;
            document.getElementById('heart').src = feedDetail.liked ? '../static/images/fill_heart.png' : '../static/images/empty_heart.png';
            // 좋아요 버튼 클릭 이벤트 설정
            document.getElementById('heart').addEventListener('click', function () {
                toggleHeart(feedId);
            });
            loadKakaoMap(feedDetail.latitude, feedDetail.longitude);

        }
    } catch (error) {
        console.error('Error fetching feed detail:', error);
    }
}

document.getElementById('feed-edit').addEventListener('click', () => {
    location.href = `/feeds-edit/${feedId}`;
});

// 페이지 로드 시 피드 상세 정보를 가져옵니다.
document.addEventListener('DOMContentLoaded', () => {
    console.log(feedId);
    if (feedId) {
        fetchFeedDetail(feedId);
    } else {
        console.error('No feed ID provided in template');
    }
});

function loadKakaoMap(latitude, longitude) {
    var script = document.createElement('script');
    script.src = "//dapi.kakao.com/v2/maps/sdk.js?appkey=3616e9e8a66bf9d7b1cbb8921a841a16&autoload=false";
    script.onload = function () {
        kakao.maps.load(function () {
            var mapContainer = document.getElementById('map');
            var mapOption = {
                center: new kakao.maps.LatLng(latitude, longitude),
                level: 2,
                draggable: false, // 드래그 비활성화
                scrollwheel: false, // 스크롤 비활성화
                disableDoubleClick: true, // 더블클릭 비활성화
                disableDoubleClickZoom: true // 더블클릭 줌 비활성화
            };
            var map = new kakao.maps.Map(mapContainer, mapOption);

            var markerPosition = new kakao.maps.LatLng(latitude, longitude);
            var marker = new kakao.maps.Marker({
                position: markerPosition
            });
            marker.setMap(map);
        });
    };
    document.head.appendChild(script);
}