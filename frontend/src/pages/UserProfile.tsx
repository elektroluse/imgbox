import { useParams } from "react-router-dom";
import Header from "../components/Navbar/Header";

function UserProfile(){

    const params = useParams();
    
    
    return(
         <main className="bg-gray-400 h-screen w-screen space-y-5">
                  <Header />
                  <h1>{params.username}</h1>
        </main>
                  
    )

}

export default UserProfile;