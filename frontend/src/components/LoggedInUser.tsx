import { useEffect, useState } from "react";
import { UserInfo } from "../types/UserInfo";
import { useAuth } from "../services/AuthProvider";

export default function LoggedInUser(){

    const BASE_URL = 'http://localhost:8080/api/v1'
    const [user, setUser] = useState<UserInfo>();
    const[loggedIn, setLoggedIn] = useState(false);
    const[username, setUsername] = useState("");
    const auth = useAuth();
    useEffect(() => {
        const fetchUser = async () => {
          //setIsLoading(true);
          const header = new Headers();
          header.append("Authorization", "Bearer " + auth.token);
          console.log(header.get("Authorization"));
            
          try{
            const response = await fetch(`${BASE_URL}/me`, {
                method : 'get',
                headers : header
            });
            const userInfo = await (response.json()) as UserInfo 
            setUser(userInfo);
            setUsername(userInfo.username);
            if(response.status != 200){
               
              setUsername("You are not logged in or token has expired");
              setLoggedIn(false);
              
            }
            if(response.status == 200){
              setUsername(user!!.username);
              setLoggedIn(true);
            }
          
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