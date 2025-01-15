import ImgboxUploadForm from "../components/Forms/ImgboxUploadForm";
import Header from "../components/Navbar/Header";
import { useAuth } from "../services/AuthProvider";

export function Upload(){
    const auth = useAuth();
    return(
        <>
            <Header/>
            <ImgboxUploadForm />
        </>
    )
}