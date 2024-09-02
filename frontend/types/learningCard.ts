export interface LearningCardType {
  id: number;
  term: string;
  definition: string;
  exampleSentence: string;
  nextReviewAt: string;
  shuffledCards?: LearningCardType[];
}
