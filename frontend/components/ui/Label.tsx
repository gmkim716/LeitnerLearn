interface LabelProps extends React.LabelHTMLAttributes<HTMLLabelElement> {
  htmlFor: string;
  children: React.ReactNode;
}

export default function Label({ htmlFor, children, ...props }: LabelProps) {
  return (
    <label htmlFor={htmlFor} className="block mb-2 font-bold" {...props}>
      {children}
    </label>
  );
}
