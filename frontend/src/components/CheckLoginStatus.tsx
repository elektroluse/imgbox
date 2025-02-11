import { useEffect } from "react";
import { toast } from "sonner";
import { UserInfo } from "../types/UserInfo";
import { useAuth } from "../services/AuthProvider";

type LoginStatusProps = {
    setStatus : (val : boolean)=> void
}

const CheckLoginStatus = ({setStatus} : LoginStatusProps) => {

    const BASE_URL = 'http://localhost:8080/api/v1';
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
            //setUser(userInfo);
            if(statusCode != 200){
              setStatus(false);
              toast.error("You are not logged in or token has expired!");
              
            }
            if(statusCode == 200){
              setStatus(true);
              toast.success("You are logged in!");
            }
          
          } catch (e : any) {
            //setError(e)
            if(statusCode === 401){
              setStatus(false);
              toast.error("You are not logged in or token has expired!");
            }else{
              toast.error("Backend is not up");
            }
            
          } 
          
        };
    
        fetchUser();
      }, [])

      return(
        <>
    
        </>
      )
}


export default CheckLoginStatus;