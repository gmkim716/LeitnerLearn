async function fetchCard(cardId: number) {
  const response = await fetch(`http://localhost:8080/api/v1/cards/${cardId}`);
  if (!response.ok) {
    throw new Error("Card 조회에 실패했습니다");
  }
  const data = await response.json();
  return data;
}

export default async function getCard(cardId: number) {
  const data = await fetchCard(cardId);
  return data;
}
