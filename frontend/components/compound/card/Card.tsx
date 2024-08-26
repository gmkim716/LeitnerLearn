import CardBody from "./Card.Body";
import CardFooter from "./Card.Footer";
import CardHeader from "./Card.Header";

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

Card.Header = CardHeader;
Card.Body = CardBody;
Card.Footer = CardFooter;
