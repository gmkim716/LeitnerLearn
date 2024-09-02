async function fetchLearningCards(userId: number) {
  const response = await fetch(
    `http://localhost:8080/api/v1/study/make-learning-cards/${userId}`,
    {}
  );

  if (!response.ok) {
    throw new Error("make-learning-cards 조회에 실패했습니다");
  }

  const data = await response.json();
  return data;
}

export default async function getLeanringCards(userId: number) {
  const data = await fetchLearningCards(userId);
  return data;
}
