import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import { ImgboxDto } from "../types/ImgboxDto";
import { getImgboxesByTag } from "../services/fetchService";
import { useAuth } from "../services/AuthProvider";
import ImgboxTable from "../components/DataTable/ImgboxTable";
import Header from "../components/Navbar/Header";

function Tag(){
    const auth = useAuth();
    const params = useParams();
    const [data, setData] = useState<ImgboxDto[] | null>(null);
    
    useEffect(() => {
        (async () => {
            const apiResponse = await getImgboxesByTag(params.tag!,auth.token) as ImgboxDto[];
            
            setData(apiResponse);
            //console.log(data);
        })()
    },[params.tag])

    return(
        <main className="bg-gray-400 h-screen w-screen space-y-5">
        <Header />
        <h1 className="font-semibold text-5xl text-center m-1">{"Imgboxes with tag : " + params.tag}</h1>
           <div className="max-w-lg mx-auto bg-slate-100 rounded-md p-5">
           <div className='space-y-2'>
          {data !== null  &&
              <>  
                  <ImgboxTable data={data}/>
              </> 
          }
          </div>
          </div>
    </main>
    )

}
export default Tag;