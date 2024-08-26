export default function FormInput({
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
}
