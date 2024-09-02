import Footer from "@/components/Footer";
import Header from "@/components/Header";
import "@/styles/globals.css";
import type { AppProps } from "next/app";
import { useRouter } from "next/router";
import { useEffect } from "react";

export default function App({ Component, pageProps }: AppProps) {
  const router = useRouter();

  useEffect(() => {
    const token = sessionStorage.getItem("access-token");
    const protectedPaths = ["/study"];

    const isProtectedPath = protectedPaths.some((path) =>
      router.pathname.startsWith(path)
    );

    if (isProtectedPath && !token) {
      router.push("/login");
    }
  }, [router]);

  return (
    <>
      <Header /> <Component {...pageProps} /> <Footer />
    </>
  );
}
