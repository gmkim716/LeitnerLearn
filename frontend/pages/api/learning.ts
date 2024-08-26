async function fetchLearning(cardId: number, correct: boolean) {
  const response = await fetch(
    `http://localhost:8080/api/v1/study/card/${cardId}/review?correct=${correct}`,
    { method: "POST" }
  );
  if (!response.ok) {
    throw new Error("학습 복습 결과 기록에 실패했습니다");
  }

  console.log("response", response);
}

// 학습 결과를 기록합니다.
export async function getLearning(cardId: number, correct: boolean) {
  const data = await fetchLearning(cardId, correct);
  return data;
}

//
