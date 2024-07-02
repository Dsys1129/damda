document.addEventListener('DOMContentLoaded', function() {
    // 숨겨진 필드에서 커플 ID 읽어오기
    const id = document.getElementById('couple-id').value;
    console.log(id);

    // 커플 정보를 가져오기 위한 API 호출
    fetch(`/couples/${id}`)
        .then(response => {
            if (response.ok) {
                return response.json();
            } else {
                throw new Error('Failed to load couple data');
            }
        })
        .then(data => {
            const coupleData = data.data;
            const feedImagePrefix = '/feeds_image/'
            const coupleImagePrefix = '/couples_image/'

            // 프로필 이미지 설정
            const imageContainer = document.getElementById('imageContainer');
            const profileImage = document.createElement('img');
            profileImage.src = coupleImagePrefix + coupleData.coupleImage;

            profileImage.style.width = '100%';
            profileImage.style.height = '100%';
            profileImage.style.borderRadius = '50%';
            imageContainer.appendChild(profileImage);


            // 커플명 설정
            document.getElementById('nicknameContainer').textContent = coupleData.coupleName;

            // D-Day 설정
            document.getElementById('dateContainer').textContent = `D+ ${coupleData.dday}`;

            // 피드 갯수 설정
            document.getElementById('num_feed').textContent = coupleData.feedCount;

            // 사용자 정보 설정
            const maleInfo = coupleData.user.find(user => user.gender === 'MALE');
            const femaleInfo = coupleData.user.find(user => user.gender === 'FEMALE');

            if (maleInfo) {
                document.getElementById('male').textContent = `남 ${maleInfo.age}세`;
            }

            if (femaleInfo) {
                document.getElementById('female').textContent = `여 ${femaleInfo.age}세`;
            }

            // 피드 이미지 설정
            const feedImages = coupleData.feed.map(feedItem => feedItem.image);
            const myFeedElements = document.querySelectorAll('.my_feed img');
            for (let i = 0; i < myFeedElements.length; i++) {
                if (feedImages[i]) {
                    myFeedElements[i].src = feedImagePrefix + feedImages[i];

                } else {
                    myFeedElements[i].style.display = 'none';
                }
            }
        })
        .catch(error => {
            console.error('Error:', error);
            alert('커플 데이터를 불러오는 중 오류가 발생했습니다.');
        });
});

function goBack() {
    history.back();
}
