import { ApiResponse } from "../types/ApiResponse";
import { ImgboxDto } from "../types/ImgboxDto";

async function getImgboxList(username : string, token : string) : Promise<ApiResponse>{
    const BASE_URL = "http://localhost:8080/api/imgbox/"
    let statusCode = -1;
    const header = new Headers();
    header.append("Authorization", "Bearer " + token);
    try {

        const response = await fetch(`${BASE_URL}${username}`,
            {
                method : "get",
                headers: header
            });
        statusCode = response.status;
        const listOfImgboxes = await(response.json()) as ImgboxDto[];
        
        return {
            data : listOfImgboxes,
            statusCode : statusCode
        } as ApiResponse;
        
        
    } catch (error) {
        console.log(statusCode);
        console.log(error);
        return {
            data : {} as ImgboxDto[],
            statusCode : 404
        } as ApiResponse

    }
}

export default getImgboxList;