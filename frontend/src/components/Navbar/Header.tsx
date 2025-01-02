import { Link } from "react-router-dom";
import MainNav from "./MainNav";

export default function Header(){

    return(
        <header className="sticky top-0 w-full border-b bg-slate-100">
            <div className="h-14 container flex items-center">
                {/** DESKTOP */}

                <MainNav />
            

                <h1 className="flex items-center gap-3 justify-end flex-1">
                    <Link to = "/login">Login</Link>
                    <Link to = "/register"> Register</Link>
                </h1>
            </div>
         
        </header>
    );
}