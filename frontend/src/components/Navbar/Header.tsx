import MainNav from "./MainNav";

export default function Header(){

    return(
        <header className="sticky top-0 w-full border-b bg-slate-100">
            <div className="h-14 container flex items-center">
                {/** DESKTOP */}

                <MainNav />
            

                <h1 className="flex items-center gap-3 justify-end flex-1">
                    <a href = "/login">Login</a>
                    <a href = "/register"> Register</a>
                </h1>
            </div>
         
        </header>
    );
}