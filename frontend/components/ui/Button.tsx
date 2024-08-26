interface ButtonProps extends React.ButtonHTMLAttributes<HTMLButtonElement> {
  children: React.ReactNode;
  color?: string;
  onClick?: () => void;
}

export default function Button({
  color = "green",
  children,
  onClick,
}: ButtonProps) {
  return (
    <button
      className={`bg-${color}-600 text-white py-3 px-6 rounded-lg w-full mt-4 transition duration-300 hover:bg-${color}-500`}
      onClick={onClick}
    >
      {children}
    </button>
  );
}
