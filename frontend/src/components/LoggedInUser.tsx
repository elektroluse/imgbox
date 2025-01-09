import { useEffect, useState } from "react";
import { UserInfo } from "../types/UserInfo";

export default function LoggedInUser(){

    const BASE_URL = 'http://localhost:8080/api/v1'
    const [user, setUser] = useState<UserInfo>();
    useEffect(() => {
        const fetchUsers = async () => {
          //setIsLoading(true);
          const header = new Headers();
          header.append("Authorization", "Bearer " + localStorage.getItem("accessToken"));
            
          try{
            const response = await fetch(`${BASE_URL}/me`, {
                method : 'get',
                headers : header
            });
            const userInfo = await (response.json()) as UserInfo
            setUser(userInfo)
          } catch (e : any) {
            //setError(e)
          } finally{
            console.log("sfsdf")
          }
                
    
          
        };
    
        fetchUsers()
      }, [])


}