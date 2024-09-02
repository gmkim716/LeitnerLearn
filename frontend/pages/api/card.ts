async function fetchCard(cardId: number) {
  const response = await fetch(`http://localhost:8080/api/v1/cards/${cardId}`);

  if (!response.ok) {
    throw new Error("Card 조회에 실패했습니다");
  }
  const data = await response.json();
  return data;
}

export async function getCard(cardId: number) {
  const data = await fetchCard(cardId);
  return data;
}

async function fetchLearningCardUpdateResult(
  cardId: number,
  userId: number,
  correct: boolean
) {
  const response = await fetch(
    `http://localhost:8080/api/v1/study/card/update-result/${cardId}/${userId}?correct=${correct}`,
    {
      method: "POST",
    }
  );

  if (!response.ok) {
    throw new Error("학습카드 풀이에 실패했습니다");
  }

  const data = await response.json();
  return data;
}

export async function getLearningCardUpdateResult(
  cardId: number,
  userId: number,
  correct: boolean
) {
  const data = await fetchLearningCardUpdateResult(cardId, userId, correct);
  return data;
}
