import LearningCard from "@/components/Card/LearningCard";
import useCards from "@/hooks/useCards";
import { LearningCardType } from "@/types/learningCard";
import { useRouter } from "next/router";
import { useState } from "react";

export default function LearningPage() {
  const router = useRouter();

  const { deckId } = router.query;
  const { cards } = useCards(Number(deckId));

  const [currentIndex, setCurrentIndex] = useState(0);
  const [showAnswer, setShowAnswer] = useState(false);
  const [isAnswerChecked, setIsAnswerChecked] = useState(false);

  const currentCard = cards[currentIndex] as LearningCardType;

  console.log("cards", cards);

  const handleShowAnswer = () => {
    setShowAnswer(true);
    setIsAnswerChecked(true);
    ``;
  };

  const handleCorrectAnswer = () => {
    console.log("정답입니다");
    handleNextCard();
  };

  const handleWrongAnswer = () => {
    console.log("오답입니다");
    handleNextCard();
  };

  const handleNextCard = () => {
    setShowAnswer(false);
    setIsAnswerChecked(false);
    setCurrentIndex((prevIdx) => (prevIdx + 1) % cards.length);
  };

  return (
    <main className="flex flex-col items-center justify-center flex-1 w-full max-w-4xl mx-auto text-center px-4 bg-background">
      <h1 className="text-textGray my-12 font-bold text-3xl">학습하기</h1>
      {currentCard && (
        <LearningCard
          term={currentCard.term}
          definition={currentCard.definition}
          exampleSentence={currentCard.exampleSentence}
          showAnswer={showAnswer}
        />
      )}
      {!showAnswer && (
        <button
          onClick={handleShowAnswer}
          className="bg-buttonGreen text-white py-2 px-4 rounded mt-6"
        >
          정답 확인
        </button>
      )}
      {showAnswer && isAnswerChecked && (
        <div className="flex space-x-4 mt-6">
          <button
            onClick={handleCorrectAnswer}
            className="bg-blue-600 text-white py-2 px-4 rounded"
          >
            정답
          </button>
          <button
            onClick={handleWrongAnswer}
            className="bg-red-600 text-white py-2 px-4 rounded"
          >
            오답
          </button>
        </div>
      )}
    </main>
  );
}
