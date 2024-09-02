export default function FormInput({
  id,
  type = "text",
  required = false,
  value,
  setValue,
  onChange,
  ...props
}: InputProps) {
  return (
    <input
      className="w-full p-2 border border-gray-300 rounded-lg"
      type={type}
      id={id}
      required={required}
      value={value}
      onChange={onChange}
      {...props}
    />
  );
}
