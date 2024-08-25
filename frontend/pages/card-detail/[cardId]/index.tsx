import useCard from "@/hooks/useCard";
import { useRouter } from "next/router";

export default function CardDetailPage() {
  const router = useRouter();
  const { cardId } = router.query;

  const handlePrevButton = () => {
    router.back();
  };

  // console.log("card", card);

  return (
    <div className="flex flex-col py-10 bg-background text-center">
      <main className="flex-1 flex items-center justify-center">
        <div className="w-full max-w-lg p-8 bg-white rounded-lg shadow-md">
          <h1 className="text-textGray text-2xl font-semibold mb-5">apple</h1>
          <p className="text-neutralGray text-lg my-3 leading-relaxed">사과</p>
          <p className="text-neutralGray text-lg my-3 leading-relaxed">
            I like to eat an apple every morning.
          </p>
          <div className="flex justify-center space-x-4 mt-6">
            <button
              className="bg-buttonGreen rounded-md text-white py-2 px-4 text-base transition-colors duration-300 hover:bg-buttonGreenHover"
              onClick={() => (location.href = "edit-card.html")}
            >
              카드 수정
            </button>
            <button
              className="bg-buttonGreen rounded-md text-white py-2 px-4 text-base transition-colors duration-300 hover:bg-buttonGreenHover"
              onClick={() => (location.href = "delete-card.html")}
            >
              카드 삭제
            </button>
            <button
              className="bg-buttonGreen rounded-md text-white py-2 px-4 text-base transition-colors duration-300 hover:bg-buttonGreenHover"
              onClick={handlePrevButton}
            >
              돌아가기
            </button>
          </div>
        </div>
      </main>
    </div>
  );
}
