export interface LearningCardType {
  id: number;
  term: string;
  definition: string;
  exampleSentence: string;
  nextReviewAt: string; // 시각
  tags: string[];
}
