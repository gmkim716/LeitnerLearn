export default function CardHeader({
  children,
}: {
  children: React.ReactNode;
}) {
  return <div className="text-2xl font-bold text-blue-500">{children}</div>;
}
