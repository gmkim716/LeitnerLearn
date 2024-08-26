import LearningCard from "@/components/Card/LearningCard";
import Button from "@/components/ui/Button";
import useReview from "@/hooks/useReviews";
import { getLearning } from "@/pages/api/learning";
import { LearningCardType } from "@/types/learningCard";
import { useRouter } from "next/router";
import { useEffect, useState } from "react";

export default function LearningPage() {
  const router = useRouter();

  const { deckId } = router.query;
  const { cards, refetch } = useReview(Number(deckId));

  const [showAnswer, setShowAnswer] = useState(false);
  const [isAnswerChecked, setIsAnswerChecked] = useState(false);

  const currentCard = cards[0] as LearningCardType;

  useEffect(() => {
    console.log("cards", cards);
  }, [cards]);

  const handleShowAnswer = () => {
    setShowAnswer(true);
    setIsAnswerChecked(true);
  };

  const handleCorrectAnswer = async () => {
    console.log("정답입니다");
    await getLearning(currentCard.id, true);
    refetch();
    handleNextCard();
  };

  const handleWrongAnswer = async () => {
    console.log("오답입니다");
    const result = await getLearning(currentCard.id, false);
    handleNextCard();
  };

  const handleNextCard = () => {
    setShowAnswer(false);
    setIsAnswerChecked(false);
  };

  return (
    <main className="flex flex-col items-center justify-center flex-1 w-full max-w-4xl mx-auto text-center px-4 bg-background py-10 gap-8">
      <h1 className="text-textGray font-bold text-3xl">학습하기</h1>
      {currentCard && (
        <LearningCard
          term={currentCard.term}
          definition={currentCard.definition}
          exampleSentence={currentCard.exampleSentence}
          showAnswer={showAnswer}
        />
      )}
      {!currentCard && <p>학습할 카드가 없습니다.</p>}
      {!showAnswer && currentCard && (
        <div className="flex space-x-4">
          <Button color="green" onClick={handleShowAnswer}>
            정답 확인
          </Button>
        </div>
      )}
      {showAnswer && isAnswerChecked && (
        <div className="flex space-x-4">
          <Button color="blue" onClick={handleCorrectAnswer}>
            정답
          </Button>
          <Button color="red" onClick={handleWrongAnswer}>
            오답
          </Button>
        </div>
      )}
    </main>
  );
}
