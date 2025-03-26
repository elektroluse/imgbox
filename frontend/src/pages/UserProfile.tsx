import { useParams, useSearchParams } from "react-router-dom";
import Header from "../components/Navbar/Header";
import {getImgboxList, getPageableImgboxList} from "../services/fetchService";
import { ApiResponse } from "../types/ApiResponse";
import { useEffect, useState } from "react";
import { ImgboxDto } from "../types/ImgboxDto";
import { useAuth } from "../services/AuthProvider";
import ImgboxTable from "../components/DataTable/ImgboxTable";
import { DataTable } from "../components/DataTable/data-table";
import { columns } from "../components/DataTable/columnTypes/titleAndTags";
import { Button } from "../components/ui/button";
import { ChevronLeft, ChevronRight } from "lucide-react";
import { PagingData } from "../types/PagingData";
import { ImgboxListPage } from "../types/ImgboxListPage";
import { toast } from "sonner";

function UserProfile(){

    const [data, setData] = useState<ImgboxDto[]>();
    const [cache, setCache] = useState<Record<number,ImgboxDto[]>>({});
    const [cachedUser, setCachedUser] = useState("");
    const [exists, setExists] = useState(true);
    const [currPage, setCurrPage] = useState(0);
    const [size, setSize] = useState(5);
    const [pageMetaData, setPageMetadata] = useState<PagingData>();
    const params = useParams();
    const auth = useAuth();
    
 
    
    useEffect(() => {
        (async () => {
            
            if(cache[currPage]){
                if(cachedUser === params.username!){
                    setData(cache[currPage]);
                    return;
                }
                
              }
            const apiResponse = await getPageableImgboxList(params.username!, auth.token, currPage, size) as ImgboxListPage;
            
            if(apiResponse.statusCode === 401){
                toast.error("You are not authorized to view this page");
                return;
            }
            
            
            if(apiResponse.statusCode === 404){
                setExists(false);
                return;
            } 
            else{
                setExists(true);
            }
            setCache(prev => ({ ...prev, [currPage]: apiResponse.data  }));
            setCachedUser(params.username!);
            setData(apiResponse.data);
            setPageMetadata(apiResponse.pageMetadata);
            setCache(cache)
            console.log(data);
        })()
    },[params.username, currPage, size])

    
    return(
         <main className="bg-gray-400 h-screen w-screen space-y-5">
                  <Header />
                  
                  {!exists &&
                        <>
                            <p className="text-lg">User does not exist!</p>
                        </>
                    }
                    <h1 className="font-bold text-5xl text-center m-3 text-orange-200"> Imageboxes by user : {params.username} </h1>
                     <div className="">
                     <div className='space-y-2'>
                    {exists && typeof(data) !== "undefined" && Object.keys(data).length > 0 &&
                        <>  
                            <DataTable columns = {columns} data={data} />
                            <div className="flex max-w-lg mx-auto rounded-md items-center justify-center gap-3">
                            <Button onClick={() => setCurrPage(currPage - 1)}
                                    disabled={currPage == 0}
                                >
                                <ChevronLeft /> Prev
                            </Button>
                            <Button onClick={() => setCurrPage(currPage + 1)}
                                disabled={currPage == pageMetaData?.totalPages! - 1}
                                >
                                <ChevronRight /> Next
                            </Button>
                        </div>
                        </> 
                    }
                    </div>
                    </div>
            
        </main>
                  
    )

}

export default UserProfile;