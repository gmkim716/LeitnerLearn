import EditProfileForm from "@/components/AuthForm/EditProfileForm";

export default function EditProfilePage() {
  return (
    <main className="flex items-center justify-center">
      <div className="bg-white p-10 my-12 rounded-lg shadow-lg w-full max-w-lg text-center">
        <EditProfileForm />
      </div>
    </main>
  );
}
