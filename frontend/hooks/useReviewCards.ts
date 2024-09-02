import getReviewCards from "@/pages/api/reviewCards";
import { useCallback, useEffect, useState } from "react";

export default function useReviewCards(userId: number) {
  const [cards, setCards] = useState(null);

  const fetchReviewCards = useCallback(async () => {
    try {
      const cards = await getReviewCards(userId);
      setCards(cards);
    } catch (error) {
      console.error(
        "복습중인 카드 리스트 데이터를 조회하는데 실패했습니다.",
        error
      );
    }
  }, [userId]);

  useEffect(() => {
    if (userId) {
      fetchReviewCards();
    }
  }, [fetchReviewCards, userId]);

  return { cards, refetch: fetchReviewCards };
}
