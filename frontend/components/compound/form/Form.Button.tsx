export default function FormButton({ children, ...props }: ButtonProps) {
  return (
    <button
      className="bg-green-600 text-white py-3 px-6 rounded-lg w-full mt-4 transition duration-300 hover:bg-green-500"
      {...props}
    >
      {children}
    </button>
  );
}
