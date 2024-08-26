async function fetchReviews(deckId: number) {
  const response = await fetch(
    `http://localhost:8080/api/v1/study/deck/review/${deckId}`,
    {}
  );
  if (!response.ok) {
    throw new Error("Deck 조회에 실패했습니다");
  }
  const data = await response.json();
  return data;
}

export default async function getReviews(deckId: number) {
  const data = await fetchReviews(deckId);
  return data;
}
