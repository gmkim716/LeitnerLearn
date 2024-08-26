import getCards from "@/pages/api/cards";
import { useCallback, useEffect, useState } from "react";

export default function useCards(deckId: number) {
  const [cards, setCards] = useState([]);

  const fetchCards = useCallback(async () => {
    try {
      const cards = await getCards(deckId);
      setCards(cards);
    } catch (error) {
      console.error("덱 리스트 데이터를 조회하는데 실패했습니다.", error);
    }
  }, [deckId]);

  useEffect(() => {
    fetchCards();
  }, [fetchCards]);

  return { cards, refetch: fetchCards };
}
