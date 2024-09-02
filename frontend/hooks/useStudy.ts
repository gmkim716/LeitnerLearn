import { getLearningCardUpdateResult } from "@/pages/api/card";

export default async function useStudy(cardId: number, userId: number) {
  try {
    await getLearningCardUpdateResult(cardId, userId);
  } catch (error) {
    console.error("카드 학습 기록을 업데이트하는데 실패했습니다.", error);
  }
}
