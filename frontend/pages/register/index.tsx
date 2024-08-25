import RegisterForm from "@/components/Form/RegisterForm";

export default function RegisterPage() {
  return (
    <main className="flex items-center justify-center bg-background text-black">
      <div className="bg-white p-10 my-12 rounded-lg shadow-lg w-full max-w-lg text-center">
        <RegisterForm />
      </div>
    </main>
  );
}
