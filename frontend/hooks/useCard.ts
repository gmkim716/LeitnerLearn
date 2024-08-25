import getCard from "@/pages/api/card";
import { useCallback, useEffect, useState } from "react";

export default function useCard(cardId: number) {
  const [card, setCard] = useState();

  const fetchCard = useCallback(async () => {
    try {
      const card = await getCard(cardId);
      setCard(card);
    } catch (error) {
      console.error("덱 리스트 데이터를 조회하는데 실패했습니다.", error);
    }
  }, [cardId]);

  useEffect(() => {
    fetchCard();
  }, [cardId]);

  return { card };
}
