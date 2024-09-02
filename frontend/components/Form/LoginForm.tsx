import { useState } from "react";
import Form from "../ui/form/Form";
import { getLogin } from "@/pages/api/authApi";
import { useRouter } from "next/router";
import { parseJwt } from "@/pages/api/context/parseJwt";

export default function LoginForm() {
  const router = useRouter();

  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    const response = await getLogin(email, password);
    console.log("token-info", parseJwt(response));
    router.push("/study");
  };

  const handleEmailChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setEmail(e.target.value);
  };
  const handlePasswordChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setPassword(e.target.value);
  };

  return (
    <Form method="POST" onSubmit={handleSubmit}>
      <h1 className="text-blue-600 text-2xl">로그인</h1>
      <div className="mb-5 text-left">
        <Form.Label htmlFor="email">이메일</Form.Label>
        <Form.Input
          type="email"
          id="email"
          name="email"
          required
          value={email}
          setValue={setEmail}
          onChange={handleEmailChange}
        />
      </div>
      <div className="mb-5 text-left">
        <Form.Label htmlFor="current-password">비밀번호</Form.Label>
        <Form.Input
          id="current-password"
          name="current-password"
          type="password"
          required
          value={password}
          setValue={setPassword}
          onChange={handlePasswordChange}
        />
      </div>
      <Form.Button type="submit">제출버튼</Form.Button>
      <Form.FooterLink text="계정이 없으신가요?" href="/register">
        회원가입
      </Form.FooterLink>
    </Form>
  );
}
