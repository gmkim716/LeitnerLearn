import getReviews from "@/pages/api/review";
import { useCallback, useEffect, useState } from "react";

export default function useReview(deckId: number) {
  const [cards, setCards] = useState([]);

  const fetchReview = useCallback(async () => {
    try {
      const cards = await getReviews(deckId);
      setCards(cards);
    } catch (error) {
      console.error("덱 리스트 데이터를 조회하는데 실패했습니다.", error);
    }
  }, [deckId]);

  const refetch = () => {
    fetchReview();
  };

  useEffect(() => {
    fetchReview();
  }, [deckId]);

  return { cards, refetch };
}
