import DeckCard from "@/components/Compound/card/DeckCard";
import useDeck from "@/hooks/useDeck";
import Link from "next/link";

const tempCount = "10개 입니다";
const tempUserId = 1;

export default function DeckListPage() {
  const { decks } = useDeck(tempUserId);

  return (
    <main className="flex flex-col items-center justify-center text-center px-4 bg-background">
      <h1 className="text-textGray my-12 font-bold text-4xl">내 덱 목록</h1>
      <div className="flex flex-wrap justify-center gap-6 mb-8">
        {decks.map((deck: DeckType) => (
          <DeckCard
            key={deck.id}
            id={deck.id}
            title={deck.name}
            subTitle={tempCount}
            buttonText="덱 보기"
          />
        ))}
      </div>
      <Link
        href="#"
        className="bg-skyBlue text-white border-none max-w-sm w-full py-4 text-xl cursor-pointer rounded-lg mt-8 transition-transform duration-300 hover:scale-105"
      >
        덱 추가
      </Link>
    </main>
  );
}
