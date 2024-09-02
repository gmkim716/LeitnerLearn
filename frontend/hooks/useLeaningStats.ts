import { getLearningStats } from "@/pages/api/learningStats";
import { useCallback, useEffect, useState } from "react";

export default function useLearningStats(userId: number) {
  const [reviewingCards, setReviewingCards] = useState<ReviewingCardIdsType>();
  const [longTermMemoryCards, setLongTermMemoryCards] =
    useState<LongTermMemoryCardIdsType>();
  const [learningLevelStats, setLearningLevelStats] =
    useState<LearningLevelStatsType>();

  const fetchLearningStats = useCallback(async (userId: number) => {
    try {
      const data = await getLearningStats(userId);
      setReviewingCards(data.reviewingCards);
      setLongTermMemoryCards(data.longTermMemoryCards);
      setLearningLevelStats(data.learningLevelStats);
    } catch (error) {
      console.error("Failed to fetch learning stats:", error);
    }
  }, []);

  useEffect(() => {
    fetchLearningStats(userId);
  }, [userId, fetchLearningStats]);

  return {
    reviewingCards,
    longTermMemoryCards,
    learningLevelStats,
  };
}
