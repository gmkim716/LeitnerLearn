import Link from "next/link";
import Card from "./Card";

interface DeckCardProps {
  id: number;
  title: string;
  subTitle: string;
  buttonText: string;
}

export default function DeckCard({
  id,
  title,
  subTitle,
  buttonText,
}: DeckCardProps) {
  return (
    <Card>
      <Card.Header>{title}</Card.Header>
      <Card.Body>{subTitle}</Card.Body>
      <Card.Footer>
        <Link
          href={`card-list/${id}`}
          className="bg-buttonGreen text-white py-2 px-4 rounded-md hover:bg-buttonGreenHover"
        >
          {buttonText}
        </Link>
      </Card.Footer>
    </Card>
  );
}
