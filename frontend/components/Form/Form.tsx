import Link from "next/link";

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

Form.Label = function Label({ htmlFor, children, ...props }: LabelProps) {
  return (
    <label htmlFor={htmlFor} className="block mb-2 font-bold" {...props}>
      {children}
    </label>
  );
};
Form.Input = function Input({
  id,
  name,
  type = "text",
  required = false,
  ...props
}: InputProps) {
  return (
    <input
      type={type}
      id={id}
      name={name}
      required={required}
      className="w-full p-2 border border-gray-300 rounded-lg"
      {...props}
    />
  );
};
Form.Button = function Button({ children, ...props }: ButtonProps) {
  return (
    <button
      className="bg-green-600 text-white py-3 px-6 rounded-lg w-full mt-4 transition duration-300 hover:bg-green-500"
      {...props}
    >
      {children}
    </button>
  );
};
Form.FooterLink = function FooterLink({
  text,
  href,
  children,
}: FooterLinkProps) {
  return (
    <p className="mt-6 text-sm">
      {text && <span className="mr-2">{text}</span>}
      <Link className="text-skyBlue font-bold hover:underline" href={href}>
        {children}
      </Link>
    </p>
  );
};
