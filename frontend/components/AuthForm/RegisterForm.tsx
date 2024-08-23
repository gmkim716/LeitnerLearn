import AuthForm from ".";
import Form from "../ui/Form";

export default function RegisterForm() {
  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    console.log("회원가입 폼 제출");
  };

  return (
    <>
      <AuthForm
        title="회원가입"
        buttonLabel="회원가입"
        method="POST"
        onSubmit={handleSubmit}
      >
        <div className="mb-5 text-left">
          <Form.Label htmlFor="confirm-password">비밀번호</Form.Label>
          <Form.Input
            type="password"
            id="confirm-password"
            name="confirm-password"
            required
          />
        </div>
        <div className="mb-5 text-left">
          <Form.Label htmlFor="confirm-password">비밀번호 확인</Form.Label>
          <Form.Input
            type="password"
            id="confirm-password"
            name="confirm-password"
            required
          />
        </div>
        <div className="mb-5 text-left">
          <Form.Label htmlFor="username">사용자명</Form.Label>
          <Form.Input type="text" id="username" name="username" required />
        </div>
        <Form.FooterLink text="이미 계정이 있으신가요?" href="/login">
          로그인
        </Form.FooterLink>
      </AuthForm>
    </>
  );
}
