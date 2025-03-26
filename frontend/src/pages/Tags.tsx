import { useEffect, useState } from "react";
import Header from "../components/Navbar/Header";
import { useAuth } from "../services/AuthProvider";
import { TagCount } from "../types/TagCount";
import { getTagCountPage } from "../services/fetchService";
import { DataTable } from "../components/DataTable/data-table";
import { tagCountColumns } from "../components/DataTable/columnTypes/tagCountColumns";
import { TagCountResponse } from "../types/TagCountResponse";
import { PagingData } from "../types/PagingData";
import { Button } from "../components/ui/button";
import { ChevronLeft, ChevronRight } from "lucide-react";

export function Tags(){
    const [data, setData] = useState<TagCount[] | null>(null);
    const [page, setPage] = useState<number>(0);
    const [size, setSize] = useState<number>(5);
    const [pageMetaData, setPageMetadata] = useState<PagingData>();
    const [cache, setCache] = useState<Record<number,TagCount[]>>({});
    const auth = useAuth();
    
    useEffect(() => {
        (async () => {

            if(cache[page]){
                setData(cache[page]);
                return;
            }
            const apiResponse = await getTagCountPage(auth.token,page,size) as TagCountResponse;
            
            setData(apiResponse.data);
            setPageMetadata(apiResponse.pageMetadata);
            setCache(prev => ({ ...prev, [page]: apiResponse.data }));
            //console.log(data);
        })()
    },[page,size])

    return(
        <main className="bg-gray-400 h-screen w-screen space-y-5">
            <Header />
            <h1 className="font-bold text-5xl text-center m-3 text-orange-200"> All tags sorted by frequency </h1>
            <div className='space-y-2'>
            
            {data !== null &&
                <DataTable columns = {tagCountColumns} data = {data} />
            }
            </div>
            <div className="flex max-w-lg mx-auto rounded-md items-center justify-center gap-3">
              <Button onClick={() => setPage(page - 1)}
                      disabled={page == 0}
                >
                <ChevronLeft /> Prev
              </Button>
              <Button onClick={() => setPage(page + 1)}
                disabled={page == pageMetaData?.totalPages! - 1}
                >
                <ChevronRight /> Next
              </Button>
            </div>
    
            
        </main>
    )
}