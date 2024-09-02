async function fetchReviewCards(userId: number) {
  const response = await fetch(
    `http://localhost:8080/api/v1/review-cards/${userId}`,
    {}
  );
  if (!response.ok) {
    throw new Error("review-cards 조회에 실패했습니다");
  }
  const data = await response.json();
  return data;
}

export default async function getReviewCards(userId: number) {
  const data = await fetchReviewCards(userId);
  return data;
}
