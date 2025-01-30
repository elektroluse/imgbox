import { useParams } from "react-router-dom";
import Header from "../components/Navbar/Header";
import getImgboxList from "../services/fetchService";
import { ApiResponse } from "../types/ApiResponse";
import { useEffect, useState } from "react";
import { ImgboxDto } from "../types/ImgboxDto";
import { useAuth } from "../services/AuthProvider";
import ImgboxTable from "../components/DataTable/ImgboxTable";

function UserProfile(){

    const [data, setData] = useState<ImgboxDto[]>();
    const [exists, setExists] = useState(true);
    const params = useParams();
    const auth = useAuth();
    
    useEffect(() => {
        (async () => {
            const apiResponse = await getImgboxList(params.username!, auth.token) as ApiResponse;
            if(apiResponse.statusCode === 404){
                setExists(false);
            }else{
                setExists(true);
            }
            setData(apiResponse.data);
            console.log(data);
        })()
    },[params.username])
    
    return(
         <main className="bg-gray-400 h-screen w-screen space-y-5">
                  <Header />
                  <h1>{params.username}</h1>
                  {!exists &&
                        <>
                            <p className="text-lg">User does not exist!</p>
                        </>
                    }
                    {exists && typeof(data) !== "undefined" &&
                        <>
                            <ImgboxTable data={data}/>
                        </> 
                    }
                    
        </main>
                  
    )

}

export default UserProfile;