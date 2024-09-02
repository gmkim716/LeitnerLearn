import Card from "@/components/ui/card/Card";
import useLearningCards from "@/hooks/useLeaningCards";
import useUserInfo from "@/hooks/useUserInfo";
import { useEffect, useState } from "react";
import { getLearningCardUpdateResult } from "../api/card";

export default function StudyPage() {
  const userInfo = useUserInfo();
  const userId = userInfo?.userId!;
  const { cards } = useLearningCards(userId);

  const learningCardList = cards?.shuffledCards || [];

  const [showAnswer, setShowAnswer] = useState(false);
  const [currentCardIndex, setCurrentCardIndex] = useState(0);

  const currentCard = learningCardList[currentCardIndex];
  const cardId = currentCard?.id;

  const handleClickNextButton = () => {
    setShowAnswer(false); // 정답 가리기
    setCurrentCardIndex((prev) => prev + 1); // 다음 인덱스 진행

    if (currentCardIndex === learningCardList.length - 1) {
      // 리스트의 마지막 카드일 때
      alert("학습이 끝났습니다.");
    }
  };

  const handleCorrectButton = () => {
    setShowAnswer(!showAnswer);
    getLearningCardUpdateResult(cardId, userId, true);
  };

  const handleIncorrectButton = () => {
    setShowAnswer(!showAnswer);
    getLearningCardUpdateResult(cardId, userId, false);
  };

  useEffect(() => {
    console.log("currentCard", currentCard);
  }, [currentCard]);

  return (
    <main className="flex justify-center my-40">
      {currentCard && (
        <Card>
          <Card.Header>{currentCard.term}</Card.Header>
          {showAnswer && (
            <>
              <Card.Body>{currentCard.definition}</Card.Body>
              <Card.Footer>{currentCard.exampleSentence}</Card.Footer>
              <div className="flex justify-center mt-5">
                <button
                  className="bg-green-400 hover:bg-green-500 text-white font-semibold py-2 px-4 rounded-lg transition-colors duration-200 ease-in-out shadow-md focus:outline-none focus:ring-2 focus:ring-green-300"
                  onClick={handleClickNextButton}
                >
                  다음
                </button>
              </div>
            </>
          )}
          {!showAnswer && (
            <>
              <div className="flex justify-center gap-10 mt-5">
                <button
                  className="bg-blue-400 hover:bg-blue-500 text-white font-semibold py-2 px-4 rounded-lg transition-colors duration-200 ease-in-out shadow-md focus:outline-none focus:ring-2 focus:ring-blue-300"
                  onClick={handleCorrectButton}
                >
                  I know
                </button>
                <button
                  className="bg-red-400 hover:bg-red-500 text-white font-semibold py-2 px-4 rounded-lg transition-colors duration-200 ease-in-out shadow-md focus:outline-none focus:ring-2 focus:ring-red-300"
                  onClick={handleIncorrectButton}
                >
                  모르겠다
                </button>
              </div>
            </>
          )}
        </Card>
      )}
    </main>
  );
}
