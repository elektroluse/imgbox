import { Link } from "react-router-dom";
import MainNav from "./MainNav";
import { useAuth } from "../../services/AuthProvider";
import { useState } from "react";

export default function Header(){
    const auth = useAuth();
    const [isLoggedIn, setIsLoggedIn] = useState(false);
    
    if(auth.token === ""){setIsLoggedIn(false);}
    else{setIsLoggedIn(true);}

    return(
        <header className="sticky top-0 w-full border-b bg-slate-100">
            <div className="h-14 container flex items-center">
                {/** DESKTOP */}

                <MainNav />
            

                <h1 className="flex items-center gap-3 justify-end flex-1">
                    {!isLoggedIn &&
                        <Link to = "/login">Login</Link> && 
                        <Link to = "/register"> Register</Link>
                    }
                    
                    {isLoggedIn &&
                        <Link to = "/me">{auth.user}</Link> &&
                        <Link to = "/logout">Log out</Link>
                    }
                </h1>
            </div>
         
        </header>
    );
}