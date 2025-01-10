import LoggedInUser from "../components/LoggedInUser"
import Header from "../components/Navbar/Header"

export function Home(){

    return(
        <>
            <Header/>
            <h1> THIS IS HOME </h1>
            <LoggedInUser />
        </>
    )
}