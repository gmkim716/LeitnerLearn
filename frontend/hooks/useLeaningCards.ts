import getLeanringCards from "@/pages/api/make-learning-cards";
import { LearningCardType } from "@/types/learningCard";
import { useCallback, useEffect, useState } from "react";

export default function useLearningCards(userId: number) {
  const [cards, setCards] = useState<LearningCardType | null>(null);

  const fetchLearningCards = useCallback(async () => {
    try {
      const cards = await getLeanringCards(userId);
      setCards(cards);
    } catch (error) {
      console.error("학습 카드 데이터를 조회하는데 실패했습니다.", error);
    }
  }, [userId]);

  useEffect(() => {
    if (userId) {
      fetchLearningCards();
    }
  }, [fetchLearningCards, userId]);

  return { cards, refetch: fetchLearningCards };
}
