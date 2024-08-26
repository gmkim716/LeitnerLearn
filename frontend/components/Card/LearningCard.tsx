import Card from "../compound/card/Card";

interface LearningCardProps {
  term: string;
  definition: string;
  exampleSentence: string;
  showAnswer: boolean;
}

export default function LearningCard({
  term,
  definition,
  exampleSentence,
  showAnswer,
}: LearningCardProps) {
  return (
    <Card>
      <Card.Header>{term}</Card.Header>
      {showAnswer && (
        <Card.Body>
          <p>{definition}</p>
          {exampleSentence && <p>{exampleSentence}</p>}
        </Card.Body>
      )}
    </Card>
  );
}
