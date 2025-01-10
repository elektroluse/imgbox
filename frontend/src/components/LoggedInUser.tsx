import { useEffect, useState } from "react";
import { UserInfo } from "../types/UserInfo";

export default function LoggedInUser(){

    const BASE_URL = 'http://localhost:8080/api/v1'
    const [user, setUser] = useState<UserInfo>();
    const[loggedIn, setLoggedIn] = useState(false);
    const[username, setUsername] = useState("");
    useEffect(() => {
        const fetchUser = async () => {
          //setIsLoading(true);
          const header = new Headers();
          header.append("Authorization", "Bearer " + localStorage.getItem("accessToken"));
            
          try{
            const response = await fetch(`${BASE_URL}/me`, {
                method : 'get',
                headers : header
            });
            const userInfo = await (response.json()) as UserInfo 
            setUser(userInfo);
            setUsername(userInfo.username);
            if(response.status != 200){
               
              setUsername(" you are not logged in");
              
            }
            setLoggedIn(true);
          
          } catch (e : any) {
            //setError(e)
          } finally{
            console.log("sfsdf")
          }
          
        };
    
        fetchUser()
      }, [])

      return(
        <div> 
          <h1 className="text-bold text-lg"> Welcome {username}</h1>
        </div>
      )


}