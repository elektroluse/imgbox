import ImgboxCreateForm from "../components/Forms/ImgboxCreateForm";
import ImgboxUploadForm from "../components/Forms/ImgboxUploadForm";
import Header from "../components/Navbar/Header";
import { useAuth } from "../services/AuthProvider";

export function Upload(){
    const auth = useAuth();
    return(
        <main className="bg-slate-50 h-screen w-screen space-y-5">
            <Header/>
            <ImgboxUploadForm />
        </main>
        
    )
}