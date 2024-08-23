interface ButtonProps extends React.ButtonHTMLAttributes<HTMLButtonElement> {
  children: React.ReactNode;
}

export default function Button({ children, ...props }: ButtonProps) {
  return (
    <button
      className={`bg-green-600 text-white py-3 px-6 rounded-lg w-full mt-4 transition duration-300 hover:bg-green-500 ${props.className}`}
      {...props}
    >
      {children}
    </button>
  );
}
