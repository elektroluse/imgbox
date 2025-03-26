import { useState } from "react";
import Header from "../components/Navbar/Header"
import CheckLoginStatus from "../components/CheckLoginStatus";
import SearchForm from "../components/Forms/SearchForm";
import { ImgboxDto } from "../types/ImgboxDto";
import { useAuth } from "../services/AuthProvider";
import { DataTable } from "../components/DataTable/data-table";
import {titleTagsUsername } from "../components/DataTable/columnTypes/col_title_tags_username";

export function Home(){
    const [isLoggedIn, setIsLoggedIn] = useState<boolean>(false);
    const [searchResult, setSearchResult] = useState<ImgboxDto[] | null>(null)
    const auth = useAuth();

    function setLoggedIn(val : boolean) {
        setIsLoggedIn(val);
    }

    function setSearchData(data : ImgboxDto[]){
        setSearchResult(data);
    }
    return(
        <>
        <main className="bg-gray-400 min-h-screen w-screen space-y-5 ">
            <Header/>
            
            <CheckLoginStatus setStatus = {setLoggedIn}/>
            <h1 className="font-bold text-5xl text-center m-3 text-slate-100"> Welcome to Imgbox </h1>
            <h2 className="font-semibold text-2xl text-center m-3 text-orange-300"
                > Search for an imgbox by keyword in title
            </h2>
           
            <SearchForm loggedIn = {isLoggedIn} setSearchData = {setSearchData}  />
            
            {searchResult !== null && searchResult.length > 0 &&
                <div className="">
                <DataTable columns = {titleTagsUsername} data={searchResult}/>
                </div>
            }
            
            </main>
        </>
    )
}