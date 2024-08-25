// deck-detil은 card-list와 동일한 페이지를 나타냅니다. 혼동을 막기 위해서 card-list를 기준으로 설정합니다.

import LearningCard from "@/components/Card/LearningCard";
import useCards from "@/hooks/useCards";
import { LearningCardType } from "@/types/learningCard";
import { useRouter } from "next/router";

export default function CardListPage() {
  const router = useRouter();
  const { deckId } = router.query;
  const { cards } = useCards(Number(deckId));

  return (
    <main className="flex flex-col items-center justify-center flex-1 text-center px-4 bg-background">
      <h1 className="text-textGray my-12 font-bold text-3xl">
        덱 이름: 기초 영어 단어
      </h1>
      <div className="flex flex-wrap justify-center gap-6 mb-8">
        {/* {cards.map((card: LearningCardType, index) => (
          <LearningCard
            key={index}
            term={card.term}
            definition={card.definition}
            exampleSentence={card.exampleSentence}
            buttonText="카드 보기"
          />
        ))} */}
      </div>

      <div className="flex flex-wrap justify-center gap-4">
        <button className="bg-skyBlue text-white px-6 py-4 text-xl cursor-pointer rounded-lg transition-transform duration-300 hover:scale-105">
          카드 추가
        </button>
        <button className="bg-red-500 text-white px-6 py-4 text-xl cursor-pointer rounded-lg transition-transform duration-300 hover:scale-105">
          덱 삭제
        </button>
      </div>
    </main>
  );
}
