import Link from "next/link";

export default function Header() {
  return (
    <header className="bg-headerBlue text-white py-4 px-6 w-full shadow-lg text-center">
      <div className="container mx-auto flex justify-between items-center px-6">
        <div className="text-2xl font-bold">LeitnerLearn</div>
        <nav>
          <ul className="flex space-x-6">
            <li>
              <Link
                className="text-lg font-bold hover:bg-skyBlue trasition duration-300 px-3 py-2 rounded"
                href="/"
              >
                홈
              </Link>
            </li>
            <li>
              <Link
                className="text-lg font-bold hover:bg-skyBlue trasition duration-300 px-3 py-2 rounded"
                href="/study"
              >
                학습하기
              </Link>
            </li>
            <li>
              <Link
                className="text-lg font-bold hover:bg-skyBlue trasition duration-300 px-3 py-2 rounded"
                href="/my-words"
              >
                단어장
              </Link>
            </li>
            <li>
              <Link
                className="text-lg font-bold hover:bg-skyBlue trasition duration-300 px-3 py-2 rounded"
                href="/about"
              >
                소개
              </Link>
            </li>
          </ul>
        </nav>
      </div>
    </header>
  );
}
