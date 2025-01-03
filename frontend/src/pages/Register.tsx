import MyForm from "../components/Forms/RegisterForm";
import Header from "../components/Navbar/Header";

export function Register(){

    return(
        <main className="bg-slate-50 h-screen w-screen space-y-5">
            <Header />
            <MyForm />
        </main>
    )
}