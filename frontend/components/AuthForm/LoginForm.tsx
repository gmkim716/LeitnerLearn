import AuthForm from ".";
import Form from "../ui/Form";

export default function LoginForm() {
  const handleSubmit = (e: React.FormEvent) => {
    console.log("로그인 폼 제출");
  };

  return (
    <AuthForm
      title="로그인"
      buttonLabel="로그인"
      method="POST"
      onSubmit={handleSubmit}
    >
      <div className="mb-5 text-left">
        <Form.Label htmlFor="confirm-password">비밀번호 변경</Form.Label>
        <Form.Input
          type="password"
          id="confirm-password"
          name="confirm-password"
          required
        />
      </div>
      <Form.FooterLink text="계정이 없으신가요?" href="/register">
        회원가입
      </Form.FooterLink>
    </AuthForm>
  );
}
