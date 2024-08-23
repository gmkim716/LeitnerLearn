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
                className="text-lg font-bold hover:bg-linkBlue trasition duration-300 px-3 py-2 rounded"
                href="/index.html"
              >
                홈
              </Link>
            </li>
            <li>
              <Link
                className="text-lg font-bold hover:bg-linkBlue trasition duration-300 px-3 py-2 rounded"
                href="/login.html"
              >
                로그인
              </Link>
            </li>
            <li>
              <Link
                className="text-lg font-bold hover:bg-linkBlue trasition duration-300 px-3 py-2 rounded"
                href="/register.html"
              >
                회원가입
              </Link>
            </li>
            <li>
              <Link
                className="text-lg font-bold hover:bg-linkBlue trasition duration-300 px-3 py-2 rounded"
                href="/about.html"
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
