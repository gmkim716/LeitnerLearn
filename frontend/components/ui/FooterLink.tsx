import Link from "next/link";

interface FooterLinkProps
  extends React.AnchorHTMLAttributes<HTMLAnchorElement> {
  text?: string;
  href: string;
  children: React.ReactNode;
}

export default function FooterLink({ text, href, children }: FooterLinkProps) {
  return (
    <p className="mt-6 text-sm">
      {text && <span className="mr-2">{text}</span>}
      <Link className="text-linkBlue font-bold hover:underline" href={href}>
        {children}
      </Link>
    </p>
  );
}
