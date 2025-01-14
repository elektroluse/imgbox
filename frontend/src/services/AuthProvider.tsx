import { useContext, createContext, useState } from "react";
import { LoginResponseDto } from "../types/LoginResponseDto";



interface ProviderProps {
    user: string | null,
    token: string,
    login (values : LoginDetails ): void,
    logout(): void
}

type LoginDetails = {
  username : string,
  token : string
}

const AuthContext = createContext<ProviderProps>({
    user: null,
    token: "",
    login: () => {},
    logout: () => {}
})

const AuthProvider = ({ children }: { children: React.ReactNode}) => {
    const storedInfo =  localStorage.getItem('user') ? JSON.parse(localStorage.getItem('user') || '{}') : null;
    const [user, setUser ] = useState(storedInfo?.username || "");
    const [token, setToken ] = useState(storedInfo?.token || "");

   

    const login = async (values : LoginDetails ) => {
  
      const userObj = {... values}
      localStorage.setItem("user", JSON.stringify(userObj));
      setUser(userObj.username);
      setToken(token);
    
    }

    const logout = () => {
        setUser(null);
        setToken("");
        localStorage.removeItem("user");
        console.log("Auth provider --> Logged out")
    }
    return (
        <AuthContext.Provider value={{user,token, login, logout}}>
            { children }
        </AuthContext.Provider>
    )
    
}

export default AuthProvider;

export const useAuth = () => {
    return useContext(AuthContext);
}


 
