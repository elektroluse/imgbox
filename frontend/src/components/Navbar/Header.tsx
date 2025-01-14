import { Link } from "react-router-dom";
import MainNav from "./MainNav";
import { useAuth } from "../../services/AuthProvider";
import { useState } from "react";

export default function Header(){
    const auth = useAuth();
    


    return(
        <header className="sticky top-0 w-full border-b bg-slate-100">
            <div className="h-14 container flex items-center">
                {/** DESKTOP */}

                <MainNav />
            

                <h1 className="flex items-center gap-3 justify-end flex-1">
                    
                    {auth.token.length === 0 &&
                        <>
                            <Link to = "/login">Login</Link> 
                            <Link to = "/register"> Register</Link>
                        </>
                    }
                    
                    {auth.token.length > 0 &&
                        <>
                            <Link to = "/">{auth.user}</Link> 
                            <button onClick={auth.logout}>Log out</button>
                        </>
                    }
                    
                </h1>
            </div>
         
        </header>
    );
}