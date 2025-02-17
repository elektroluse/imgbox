import { useEffect, useState } from "react";
import TagCountTable from "../components/DataTable/TagCountTable";
import Header from "../components/Navbar/Header";
import { useAuth } from "../services/AuthProvider";
import { TagCount } from "../types/TagCount";
import { getTagCountList } from "../services/fetchService";

export function Tags(){
    const [data, setData] = useState<TagCount[] | null>(null)
    const auth = useAuth();
    
    useEffect(() => {
        (async () => {
            const apiResponse = await getTagCountList(auth.token) as TagCount[];
            
            setData(apiResponse);
            //console.log(data);
        })()
    },[])

    return(
        <main className="bg-gray-400 h-screen w-screen space-y-5">
            <Header />
            <div className="max-w-lg mx-auto bg-slate-100 rounded-md p-5">
            <div className='space-y-2'>
            {data !== null &&
                <TagCountTable data = {data} />
            }
            </div>
            </div>
            
        </main>
    )
}