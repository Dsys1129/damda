document.getElementById('login-form').addEventListener('submit', async function(event) {
    event.preventDefault();

    const userId = document.getElementById('userId').value;
    const password = document.getElementById('password').value;

    try {
        const response = await fetch('/login', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            credentials: 'same-origin', // 세션 쿠키를 자동으로 포함
            body: JSON.stringify({ userId, password }),
        });

        if (response.ok) {
            const data = await response.json();
            alert(data.message); // 로그인 성공 메시지 출력
            // 로그인 성공 후 필요한 추가 작업 (예: 페이지 리디렉션)
            window.location.href = '/'; // 메인 페이지로 리디렉션
        } else {
            alert('아이디 및 비밀번호를 확인해주세요'); // 로그인 실패 메시지 출력
        }
    } catch (error) {
        console.error('로그인 중 오류 발생:', error);
        alert('로그인 중 오류가 발생했습니다.');
    }
});

function goBack() {
    history.back();
}