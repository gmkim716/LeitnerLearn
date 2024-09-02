// 인증 관련 API 호출 함수

async function fetchLogin(email: string, password: string) {
  const response = await fetch(`http://localhost:8080/api/v1/auth/login`, {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify({ email, password }),
  });

  if (!response.ok) {
    throw new Error("로그인 요청에 실패했습니다.");
  }

  const token = await response.text();

  sessionStorage.setItem("access-token", token);

  return token;
}

export async function getLogin(email: string, password: string) {
  const token = await fetchLogin(email, password);
  return token;
}
