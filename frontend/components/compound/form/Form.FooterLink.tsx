import Link from "next/link";

export default function FormFooterLink({
  text,
  href,
  children,
}: FooterLinkProps) {
  return (
    <p className="mt-6 text-sm">
      {text && <span className="mr-2">{text}</span>}
      <Link className="text-skyBlue font-bold hover:underline" href={href}>
        {children}
      </Link>
    </p>
  );
}
