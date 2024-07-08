document.getElementById('file-input').addEventListener('change', function(event) {
    const file = event.target.files[0];
    if (file) {
        const reader = new FileReader();
        reader.onload = function(e) {
            document.getElementById('profile-pic').src = e.target.result;
        };
        reader.readAsDataURL(file);
    }
});

document.getElementById('register-form').addEventListener('submit', async function(event) {
    event.preventDefault();

    const userId = document.getElementById('userId').value;
    const password = document.getElementById('password').value;
    const nickname = document.getElementById('nickname').value;
    const age = document.getElementById('age').value;
    let gender = document.getElementById('gender').value;

    const user = {
        userId: userId,
        password: password,
        nickname: nickname,
        age: age,
        gender: gender
    };

    console.log(user);


    const formData = new FormData();
    formData.append('user', new Blob([JSON.stringify(user)], { type: 'application/json' }));
    const fileInput = document.getElementById('file-input');
    if (fileInput.files[0]) {
        formData.append('image', fileInput.files[0]);
    } else {
        alert('프로필 이미지를 등록해주세요');
        return;
    }

    try {
        const response = await fetch('/signup', {
            method: 'POST',
            body: formData,
            credentials: 'same-origin'
        });

        if (response.ok) {
            const data = await response.json();
            alert(data.message);
            window.location.href = '/login';
        } else if (response.status == 409) {
            alert('이미 존재하는 아이디입니다.');
        } else if (response.status == 400) {
            alert('잘못된 요청입니다. 모든 필드를 올바르게 입력했는지 확인해주세요.');
        } else {
            alert('회원가입 중 오류가 발생했습니다.');
        }
    } catch (error) {
        console.error('회원가입 중 오류 발생:', error);
        alert('회원가입 중 오류가 발생했습니다.');
    }
});

function goBack() {
    history.back();
}
