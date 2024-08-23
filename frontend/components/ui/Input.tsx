interface InputProps extends React.InputHTMLAttributes<HTMLInputElement> {
  id: string;
  name: string;
}

export default function Input({
  id,
  name,
  type = "text",
  required = false,
  ...props
}: InputProps) {
  return (
    <input
      className="w-full p-2 border border-gray-300 rounded-lg"
      type={type}
      id={id}
      name={name}
      required={required}
      {...props}
    />
  );
}
