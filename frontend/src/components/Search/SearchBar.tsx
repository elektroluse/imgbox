import { useState } from "react";
import { Button } from "../ui/button";
import { Input } from "../ui/input";
import { Search } from "lucide-react";
import { useAuth } from "../../services/AuthProvider";

function onClick() {
     const BASE_URL ="http://localhost:8080/api/imgbox/search/"; 

}

type searchBarProps = {
    loggedIn : boolean
}

const SearchBar = ({loggedIn} : searchBarProps) => {
    
    return(

        <div className="flex w-full max-w-sm items-center space-x-2 mx-auto">
            
            {!loggedIn &&
                <>
                <Input disabled type="search" placeholder="Search by title"/>
                <Button disabled 
                 variant="outline"
                 size="icon">
                    <Search />
                </Button>
                </>            
            }
            
            {loggedIn &&
                <>
                <Input type="search" placeholder="Search by title"/>
                <Button 
                    variant="outline"
                    size="icon">
                    <Search />
                </Button>
                </>
            }
            
        
      </div>
        
    )


}

export default SearchBar;