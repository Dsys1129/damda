document.getElementById('file-input').addEventListener('change', function(event) {
    const file = event.target.files[0];
    if (file) {
        const reader = new FileReader();
        reader.onload = function(e) {
            document.querySelector('.upload-image img').src = e.target.result;
        };
        reader.readAsDataURL(file);
    }
});

document.getElementById('couple-form').addEventListener('submit', async function (event) {
    event.preventDefault();

    const name = document.getElementById('name').value;
    const dDay = document.getElementById('dDay').value;

    const couple = {
        name: name,
        dDay: dDay
    };

    const formData = new FormData();
    formData.append('couple', new Blob([JSON.stringify(couple)], {type: 'application/json'}));
    const fileInput = document.getElementById('file-input');
    if (fileInput.files[0]) {
        formData.append('image', fileInput.files[0]);
    } else {
        alert('프로필 이미지를 등록해주세요');
        return;
    }

    try {
        const response = await fetch('/couples', {
            method: 'POST',
            body: formData,
            credentials: 'same-origin'
        });

        if (response.ok) {
            const data = await response.json();
            // let bodyElement = data.body['code'];
            console.log(data)
            alert(`커플 생성 성공! 5분내로 상대방이 코드를 입력하면 커플이 성사됩니다! 
            ${data.data.code}`);
            // alert(bodyElement)
            // window.location.href = '/success';
        } else if (response.status == 400) {
            alert('잘못된 요청입니다. 모든 필드를 올바르게 입력했는지 확인해주세요.');
        } else {
            alert('커플 생성 중 오류가 발생했습니다.');
        }
    } catch (error) {
        console.error('커플 생성 중 오류 발생:', error);
        alert('커플 생성 중 오류가 발생했습니다.');
    }
});

function goBack() {
    history.back();
}