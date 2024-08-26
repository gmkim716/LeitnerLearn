import FormButton from "./Form.Button";
import FormFooterLink from "./Form.FooterLink";
import FormInput from "./Form.Input";
import FormLabel from "./Form.Label";

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

Form.Label = FormLabel;
Form.Input = FormInput;
Form.Button = FormButton;
Form.FooterLink = FormFooterLink;
