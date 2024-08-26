export default function FormLabel({ htmlFor, children, ...props }: LabelProps) {
  return (
    <label htmlFor={htmlFor} className="block mb-2 font-bold" {...props}>
      {children}
    </label>
  );
}
