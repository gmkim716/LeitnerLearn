async function fetchCards(deckId: number) {
  const response = await fetch(
    `http://localhost:8080/api/v1/cards/deck/${deckId}`,
    {}
  );
  if (!response.ok) {
    throw new Error("Card 조회에 실패했습니다");
  }
  const data = await response.json();
  return data;
}

export default async function getCards(deckId: number) {
  const data = await fetchCards(deckId);
  return data;
}
