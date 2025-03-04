import { AuthDto } from "../types/AuthDto";
import { ImgboxEditForm } from "../types/ImgboxEditForm";
import { ImgboxUploadFormType } from "../types/ImgboxUploadFormType";
import { LoginResponseDto } from "../types/LoginResponseDto";
import { RegisterResponseDto } from "../types/RegisterResponseDto";
import { UploadResponseDto } from "../types/UploadResponseDto";

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

export async function sendUploadDto(values : ImgboxUploadFormType, token : string, username : string) : Promise<UploadResponseDto> {
  const API_URL = "http://localhost:8080/api/imgbox/upload"

  const header = new Headers();
  header.append("Authorization", "Bearer " + token);
  console.log(header.get("Authorization"));
  let statusCode : number = -1;
  
  let jsonPart = {
    title : values.title,
    description : values.description,
    tags : values.tags,
    username : username
  }
  const jsonData = new Blob([JSON.stringify(jsonPart)], {
    type : "application/json"
  })
  const data = new FormData();
  data.append("imgboxdto", jsonData);
  data.append("file", values.files![0]);

  try{
    const response = await fetch(API_URL, 
      {
        method : 'post',
        headers : header,
        body : data
      });
    statusCode = response.status;

    if(statusCode === 201){
      const serverResponse = await (response.json()) as UploadResponseDto;
      serverResponse.success = true;
      return serverResponse;
    }
    if(statusCode === 401){
      return {message : "You need to be logged in to upload!", success : false}
    }
    
  } catch (e : any) {
    
    console.log(e);
  }
  
  return {message : "Upload was not successful", success : false};
  
}

export async function sendEditRequest(values : ImgboxEditForm, token : string) : Promise<number>{
  const API_URL = "http://localhost:8080/api/imgbox/id/"
  const header = new Headers();
  header.append("Authorization", "Bearer " + token);
  header.append("Content-Type","application/json");
  console.log(header.get("Authorization"));
  let statusCode : number = -1;
  try{
    const response = await fetch(`${API_URL}${values.id}`, 
      {
        method : 'put',
        headers : header,
        body : JSON.stringify({...values, id : Number(values.id)})
      });
    statusCode = response.status;

    
    
  } catch (e : any) {
    
    console.log(e);
  }
  
  return statusCode;

}

export async function sendDeleteRequest(id : bigint, token : string) : Promise<number>{
  const API_URL = "http://localhost:8080/api/imgbox/id/"
  const header = new Headers();
  header.append("Authorization", "Bearer " + token);
  console.log(header.get("Authorization"));
  let statusCode : number = -1;
  try{
    const response = await fetch(`${API_URL}${id}`, 
      {
        method : 'delete',
        headers : header,
      });
    statusCode = response.status;

    
    
  } catch (e : any) {
    
    console.log(e);
  }
  
  return statusCode;

}





