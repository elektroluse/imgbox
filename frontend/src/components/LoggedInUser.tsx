import { useEffect, useState } from "react";
import { UserInfo } from "../types/UserInfo";
import { useAuth } from "../services/AuthProvider";
import { toast } from "sonner";

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
          let statusCode : number = -1;
            
          try{
            const response = await fetch(`${BASE_URL}/me`, {
                method : 'get',
                headers : header
            });
            statusCode = response.status;
            const userInfo = await (response.json()) as UserInfo 
            setUser(userInfo);
            setUsername(userInfo.username);
            if(statusCode != 200){
              setUsername("You are not logged in or token has expired");
              setLoggedIn(false);
              toast.error("You are not logged in or token has expired!");
              
            }
            if(statusCode == 200){
              setLoggedIn(true);
              toast.success("You are logged in!");
            }
          
          } catch (e : any) {
            //setError(e)
            if(statusCode === 401){
              setUsername("You are not logged in or token has expired");
              setLoggedIn(false);
              toast.error("You are not logged in or token has expired!");
            }else{
              setUsername("Backend is not up");
            }
            
          } 
          
        };
    
        fetchUser()
      }, [])

      return(
        <div> 
          <h1 className="text-bold text-lg"> Welcome :  {username}</h1>
        </div>
      )


}