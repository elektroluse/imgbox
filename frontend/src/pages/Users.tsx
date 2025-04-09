import { useEffect, useState } from 'react'
import { UserInfo } from '../types/UserInfo';
import Header from '../components/Navbar/Header';
import { ChevronRight, ChevronLeft } from "lucide-react"
import { Button } from '../components/ui/button';
import { PagingData } from '../types/PagingData';
import { getUsersByPage } from '../services/fetchService';
import { usernameAndId } from '../components/DataTable/columnTypes/userAndId';
import { DataTable } from '../components/DataTable/data-table';
import { useSearchParams } from 'react-router-dom';
import { Select, SelectContent, SelectItem, SelectTrigger, SelectValue } from '../components/ui/select';
//import './App.css'


function Users() {

  const [error, setError] = useState();
  const [isLoading, setIsLoading] = useState(false);
  const [users, setUsers] = useState<UserInfo[]>([]);
  
  
  const [pageMetaData, setPageMetadata] = useState<PagingData>();
  const [cache, setCache] = useState<Record<number,UserInfo[]>>({});  
  const [searchParams, setSearchParams] = useSearchParams();
  const [currPage, setCurrPage] = useState(Number(searchParams.get("page")) || 0);
  const [size, setSize] = useState(Number(searchParams.get("size")) || 5);
  
  useEffect(() =>  {
    const currentParams = Object.fromEntries([...searchParams]);
    console.log(currentParams);
    if(currentParams["size"]){
      if(Number(currentParams["size"]) == size){
        
      }else{
        setSize(Number(currentParams["size"]));
        setCache({});
        setCurrPage(0);
      }
      
    }
    if(currentParams["page"] && Number(currentParams["page"]) != currPage){
      setCurrPage(Number(currentParams["page"]));
    }
    
  }, [searchParams]);

  

  useEffect(() => {
    const fetchUsers = async () => {
      setIsLoading(true);

      try{

          if(cache[currPage]){
            setUsers(cache[currPage]);
            return;
          }
          const result =  await getUsersByPage(currPage,size)
          const fetchedUsers = result["content"];
          const pageMetadata = result["page"];
          
          setPageMetadata(pageMetadata!)
          if(fetchedUsers !== null){
            setCache(prev => ({ ...prev, [currPage]: fetchedUsers }));
            setUsers(fetchedUsers);
          }
          
          
        
        }catch (e : any) {
        setError(e)
      } finally{
        setIsLoading(false);
      }
            

      
    };

    fetchUsers()
  }, [currPage, size])

 

   
  if(isLoading){
    {}
    return <div className="bg-gray-400 h-screen w-screen space-y-5">
      <Header />
      LOADING...
      
      </div>
  }

  if(error){
    
    return <div>
      <Header />
        ERORR! Try again
      </div>
  }

  return (
      
      <main className="bg-gray-400 h-screen w-screen space-y-5">
          <Header />
      <h1 className = "font-bold text-5xl text-center m-3 text-orange-200"> Users </h1>
      <div className="">
      
        <div className='space-y-2'>
        <Select onValueChange={(value) => setSearchParams({page : "0", size : value })}>
        <SelectTrigger className="w-[90px] mx-auto bg-slate-300">
          <SelectValue placeholder={size} />
        </SelectTrigger>
        <SelectContent>
          <SelectItem value="5">5</SelectItem>
          <SelectItem value="10">10</SelectItem>
          <SelectItem value="15">15</SelectItem>
        </SelectContent>
      </Select>
            <DataTable columns = {usernameAndId} data = {users} />
        </div>
              
      </div>
      <div className="flex max-w-lg mx-auto rounded-md items-center justify-center gap-3">
              <Button onClick={() => setSearchParams(
                {
                  page : (currPage - 1).toString(),
                  size : size.toString()
                })}
                disabled={currPage == 0}
                >
                <ChevronLeft /> Prev
              </Button>
              <Button onClick={() => setSearchParams(
                {
                  page : (currPage + 1).toString(),
                  size : size.toString()
                })}
                disabled={currPage == pageMetaData?.totalPages! - 1}
                >
                <ChevronRight /> Next
              </Button>
      </div>
      </main>
  )
}
export default Users;