import Form from "../compound/form/Form";

interface AuthFormProps {
  method: "GET" | "POST" | "PUT";
  title: string;
  buttonLabel: string;
  onSubmit: (e: React.FormEvent) => void;
  children?: React.ReactNode;
  email?: string;
  isEmailEditable?: boolean;
}

export default function AuthForm({
  method,
  title,
  buttonLabel,
  onSubmit,
  children,
  email,
  isEmailEditable = true,
}: AuthFormProps) {
  return (
    <Form method={method} onSubmit={onSubmit}>
      <h1 className="text-blue-600 text-2xl">{title}</h1>
      <div className="mb-5 text-left">
        <Form.Label htmlFor="email">이메일</Form.Label>
        <Form.Input
          type="email"
          id="email"
          name="email"
          required
          value={email}
          disabled={!isEmailEditable}
        />
      </div>
      {children}
      <Form.Button type="submit">{buttonLabel}</Form.Button>
    </Form>
  );
}
