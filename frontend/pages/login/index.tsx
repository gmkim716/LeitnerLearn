import LoginForm from "@/components/AuthForm/LoginForm";
import React from "react";

export default function LoginPage() {
  return (
    <main className="flex items-center justify-center">
      <div className="bg-white p-10 my-12 rounded-lg shadow-lg w-full max-w-lg text-center">
        <LoginForm />
      </div>
    </main>
  );
}
