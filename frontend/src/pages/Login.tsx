import LoginForm from "../components/Forms/LoginForm";
import Header from "../components/Navbar/Header";

export function Login(){

  return(
         <main className="bg-slate-50 h-screen w-screen space-y-5">
             <Header />
             <LoginForm />
         </main>
     )
}