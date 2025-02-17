import { useParams } from "react-router-dom";
import Header from "../components/Navbar/Header";
import {getImgboxList} from "../services/fetchService";
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
                  
                  {!exists &&
                        <>
                            <p className="text-lg">User does not exist!</p>
                        </>
                    }
                    <h1 className="font-bold text-5xl text-center m-3 text-orange-200"> Imageboxes by user : {params.username} </h1>
                     <div className="max-w-lg mx-auto bg-slate-100 rounded-md p-5">
                     <div className='space-y-2'>
                    {exists && typeof(data) !== "undefined" &&
                        <>  
                            <ImgboxTable data={data}/>
                        </> 
                    }
                    </div>
                    </div>
        </main>
                  
    )

}

export default UserProfile;