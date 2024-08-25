interface CardProps {
  children: React.ReactNode;
}

export default function Card({ children }: CardProps) {
  return (
    <div className="bg-white p-5 rounded-lg shadow-md w-64 text-center transition-transform duration-300 group hover:-translate-y-1">
      {children}
    </div>
  );
}

Card.Header = function CardHeader({ children }: { children: React.ReactNode }) {
  return <div className="text-2xl font-bold text-blue-500">{children}</div>;
};

Card.Body = function CardBody({ children }: { children: React.ReactNode }) {
  return <div className="my-3 text-gray-600">{children}</div>;
};

Card.Footer = function CardFooter({ children }: { children: React.ReactNode }) {
  return <div className="mt-4">{children}</div>;
};
