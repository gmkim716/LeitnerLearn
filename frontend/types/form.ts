interface LabelProps extends React.LabelHTMLAttributes<HTMLLabelElement> {
  htmlFor: string;
  children: React.ReactNode;
}

interface InputProps extends React.InputHTMLAttributes<HTMLInputElement> {
  id: string;
  name: string;
}

interface ButtonProps extends React.ButtonHTMLAttributes<HTMLButtonElement> {
  children: React.ReactNode;
}

interface FooterLinkProps
  extends React.AnchorHTMLAttributes<HTMLAnchorElement> {
  text?: string;
  href: string;
  children: React.ReactNode;
}
