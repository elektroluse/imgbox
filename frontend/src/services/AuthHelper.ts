import { AuthDto } from "../types/AuthDto";
import { LoginResponseDto } from "../types/LoginResponseDto";
import { RegisterResponseDto } from "../types/RegisterResponseDto";

export async function sendRegisterDto (values : AuthDto) : Promise<RegisterResponseDto> {
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
      const responseFromServer = await (response.json()) as RegisterResponseDto
      return responseFromServer;
     
    } catch (e) {
      console.error(e)  
    }
    return {
        username : values.username,
        message : "Something went wrong!",
        completed : false
    };
}

export async function sendLoginDto(values : AuthDto) : Promise<LoginResponseDto> {
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
            if(response.status !== 200){
                return {accessToken : "", authenticated : false, message : "Login unsuccessful", tokenType:""};
            } 
            
            const responseFromServer = await (response.json()) as LoginResponseDto;
            return responseFromServer;

         
        } catch (e) {
          console.error(e)
        } 
        return {accessToken : "", authenticated : false, message : "Server is down", tokenType:""};
}


