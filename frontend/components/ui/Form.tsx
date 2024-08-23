import Button from "./Button";
import FooterLink from "./FooterLink";
import Input from "./Input";
import Label from "./Label";

interface FormProps {
  action?: string;
  method: "GET" | "POST" | "PUT";
  onSubmit?: (e: React.FormEvent) => void;
  children: React.ReactNode;
}

export default function Form({ action, method, children }: FormProps) {
  return (
    <form action={action} method={method} className="space-y-6">
      {children}
    </form>
  );
}

Form.Label = Label;
Form.Input = Input;
Form.Button = Button;
Form.FooterLink = FooterLink;
