import AuthForm from "./AuthForm";
import Form from "./Form";

export default function EditProfileForm() {
  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    console.log("회원정보 수정 폼 제출");
  };

  return (
    <>
      <AuthForm
        method="PUT"
        title="회원정보 수정"
        buttonLabel="회원정보 수정"
        onSubmit={handleSubmit}
        // email="test@gmail.com"
        isEmailEditable={false}
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
        <div className="mb-5 text-left">
          <Form.Label htmlFor="confirm-password">비밀번호 변경 확인</Form.Label>
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
      </AuthForm>
    </>
  );
}
