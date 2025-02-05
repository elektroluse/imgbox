import { ApiResponse } from "../types/ApiResponse";
import { ImgboxDto } from "../types/ImgboxDto";

async function getImgboxList(username : string, token : string) : Promise<ApiResponse>{
    const BASE_URL = "http://localhost:8080/api/imgbox/";
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
        } 
        
        
    } catch (error) {
        console.log(statusCode);
        console.log(error);
        return {
            data : {} as ImgboxDto[],
            statusCode : 404
        } 

    }
}

export type BlobResponse = {
    blob : Blob,
    status : number
};


async function getImgboxImage(imgboxDto : ImgboxDto, token : string) : Promise<BlobResponse>{
    const BASE_URL ="http://localhost:8080/api/imgbox/storage/";
    const objectKey = imgboxDto.fileUrl;
    let statusCode = -1;
    const header = new Headers();
    header.append("Authorization", "Bearer " + token);
    

    try {
        const response = await fetch(`${BASE_URL}${objectKey}`, {
            method : "get",
            headers : header,
            mode : "cors"
        });

        statusCode = response.status;
        const fileBlob = await(response.blob()) as Blob;
        return {
            blob : fileBlob,
            status : statusCode
        };

        
    } catch (error) {

        console.log(error);
        return {
            blob : {} as Blob,
            status : 404
        }
    }
    
    
    
}

export type SingleImgboxResponse = {
    data : ImgboxDto,
    status : number
};

async function getImgboxFromId(id : string, token : string) : Promise<SingleImgboxResponse>{
    const BASE_URL ="http://localhost:8080/api/imgbox/id/";
    let statusCode = -1;
    const header = new Headers();
    header.append("Authorization", "Bearer " + token);

    try {
        const response = await fetch(`${BASE_URL}${id}`, {
            method : "get",
            headers : header,
            mode : "cors"
        });

        const imgbox =  await response.json() as ImgboxDto
        statusCode = response.status;
        
        return {
            data : imgbox,
            status : statusCode
        };

        
    } catch (error) {

        console.log(error);
        return {
            data : {} as ImgboxDto,
            status : 404
        }
    }
    
}

export {getImgboxList, getImgboxImage, getImgboxFromId};