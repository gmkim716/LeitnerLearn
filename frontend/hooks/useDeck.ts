import getDecks from "@/pages/api/deck";
import { useCallback, useEffect, useState } from "react";

export default function useDeck(userId: number) {
  const [decks, setDecks] = useState([]);

  const fetchDecks = useCallback(async () => {
    try {
      const decks = await getDecks(userId);
      setDecks(decks);
    } catch (error) {
      console.error("덱 리스트 데이터를 조회하는데 실패했습니다.", error);
    }
  }, [userId]);

  useEffect(() => {
    fetchDecks();
  }, [userId]);

  return { decks };
}
