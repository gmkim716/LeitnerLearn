import type { Config } from "tailwindcss";

const config: Config = {
  content: [
    "./pages/**/*.{js,ts,jsx,tsx,mdx}",
    "./components/**/*.{js,ts,jsx,tsx,mdx}",
    "./app/**/*.{js,ts,jsx,tsx,mdx}",
  ],
  theme: {
    extend: {
      colors: {
        background: "#ecf0f1",
        textGray: "#2c3e50",
        buttonGreen: "#27ae60",
        buttonGreenHover: "#2ecc71",
        skyBlue: "#2980b9",
        footerYellow: "#f1c40f",
        headerBlue: "#2c3e50",
        inputBorderGray: "#bdc3c7",
        neutralGray: "#7f8c8d",
      },
      backgroundImage: {
        "gradient-radial": "radial-gradient(var(--tw-gradient-stops))",
        "gradient-conic":
          "conic-gradient(from 180deg at 50% 50%, var(--tw-gradient-stops))",
      },
    },
  },
  plugins: [],
};
export default config;
