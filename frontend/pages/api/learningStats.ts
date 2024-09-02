async function fetchLearningStats(userId: number) {
  const response = await fetch(
    `http://localhost:8080/api/v1/study/stats/${userId}`
  );

  if (!response.ok) {
    throw new Error("사용자의 학습 통계 조회에 실패했습니다");
  }

  const data = await response.json();
  return data;
}

// 학습 결과를 기록합니다.
export async function getLearningStats(userId: number) {
  const data = await fetchLearningStats(userId);
  return data;
}

//
