import { useContext, createContext, useState } from "react";
import { LoginResponseDto } from "../types/LoginResponseDto";



interface ProviderProps {
    user: string | null,
    token: string,
    register(values : userAuthRequestDto) : void,
    login (values : userAuthRequestDto): void,
    logout(): void
}

type userAuthRequestDto = {
  username : string,
  password : string
}

const AuthContext = createContext<ProviderProps>({
    user: null,
    token: "",
    register: () => {},
    login: () => {},
    logout: () => {}
})

const AuthProvider = ({ children }: { children: React.ReactNode}) => {
    const storedInfo =  localStorage.getItem('user') ? JSON.parse(localStorage.getItem('user') || '{}') : null;
    const [user, setUser ] = useState<string | null>(storedInfo?.user);
    const [token, setToken ] = useState(storedInfo?.token || "");
    const [message, setMessage] = useState("");

    const register = async (values : userAuthRequestDto) => {
        const header = new Headers();
        header.append("Content-Type","application/json");
        //header.append("Access-Control-Allow-Origin")
  
        try{
          const response = await fetch("http://localhost:8080/api/auth/register",
             {method : 'POST',
               body : JSON.stringify(values),
               headers : header,
               mode : "cors"
               }
            );
          console.log(response.status)
          console.log(response.text())
          if(response.status == 201){

            setMessage("Sucessfully Registered user: " + values.username);
          }
          if(response.status == 400){
            setMessage("Username is already in use : " + values.username)
          }
         
        } catch (e) {
          console.error(e)
        }
    }

    const login = async (values : userAuthRequestDto) => {

        const header = new Headers();
        header.append("Content-Type","application/json");
        //header.append("Access-Control-Allow-Origin")
  
        try{
          const response = await fetch("http://localhost:8080/api/auth/login",
             {method : 'POST',
               body : JSON.stringify(values),
               headers : header,
               mode : "cors"
               }
            );
          
          if(response.status == 200){

            const serverResponse : LoginResponseDto =  await response.json();
            const userObj = {user : values.username , token : serverResponse.accessToken}
            localStorage.setItem("user", JSON.stringify(userObj));
            setUser(userObj.user);
            setToken(token);
            setMessage("Login successfull : " + userObj.user);
            console.log(localStorage.getItem("user"));
          }
          else{
            setMessage("Login failed : " + values.username)
          }
         
        } catch (e) {
          console.error(e)
        }
    }

    const logout = () => {
        setUser(null);
        setToken("");
        localStorage.removeItem("user");
    }
    return (
        <AuthContext.Provider value={{user,token, register, login, logout}}>
            { children }
        </AuthContext.Provider>
    )
    
}

export default AuthProvider;

export const useAuth = () => {
    return useContext(AuthContext);
}


 
