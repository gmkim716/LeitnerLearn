async function fetchDecks(userId: number) {
  const response = await fetch(
    `http://localhost:8080/api/v1/decks/user/${userId}`,
    {}
  );
  if (!response.ok) {
    throw new Error("Deck 조회에 실패했습니다");
  }
  const data = await response.json();
  return data;
}

export default async function getDecks(userId: number) {
  const data = await fetchDecks(userId);
  return data;
}
