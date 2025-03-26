import { UserInfo } from "os";
import { ApiResponse } from "../types/ApiResponse";
import { ImgboxDto } from "../types/ImgboxDto";
import { PageUserInfo } from "../types/PageUserInfo";
import { TagCount } from "../types/TagCount";
import { PagingData } from "../types/PagingData";
import { ImgboxListPage } from "../types/ImgboxListPage";
import { TagCountResponse } from "../types/TagCountResponse";


async function getPageableImgboxList(
    username : string,
     token : string,
     page : number,
     size : number) : Promise<ImgboxListPage>{
    const BASE_URL = "http://localhost:8080/api/imgbox/";
    let statusCode = -1;
    const header = new Headers();
    header.append("Authorization", "Bearer " + token);
    try {
        const response = await fetch(`${BASE_URL}username/${username}?page=${page}&size=${size}`,
            {
                method : "get",
                headers: header
            });
        statusCode = response.status;
        
        const receivedBody = await(response.json());
        const data : ImgboxDto[] = receivedBody["content"];
        const paging : PagingData = receivedBody["page"];

        return {
            data : data,
            pageMetadata : paging,
            statusCode : statusCode
        } 
        
        
    } catch (error) {
        console.log(error);
        return {
            data : [],
            pageMetadata : {} as PagingData,
            statusCode : statusCode
        }
    }
}
async function getImgboxList(username : string, token : string) : Promise<ApiResponse>{
    const BASE_URL = "http://localhost:8080/api/imgbox/";
    let statusCode = -1;
    const header = new Headers();
    header.append("Authorization", "Bearer " + token);
    try {

        const response = await fetch(`${BASE_URL}username/${username}`,
            {
                method : "get",
                headers: header
            });
        statusCode = response.status;
        const data = await(response.json());
        const listOfImgboxes = data["content"] as ImgboxDto[]
        
        return {
            data : listOfImgboxes,
            statusCode : statusCode
        } 
        
        
    } catch (error) {
        console.log(statusCode);
        console.log(error);
        return {
            data : {} as ImgboxDto[],
            statusCode : statusCode
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

async function fetchSearchTermList(searchTerm : string, token : string) : Promise<ImgboxDto[]>{
    
        const BASE_URL ="http://localhost:8080/api/imgbox/search/title/";
        let statusCode = -1;
        const header = new Headers();
        header.append("Authorization", "Bearer " + token);
        try {

            const response = await fetch(`${BASE_URL}${searchTerm}`,
                {
                    method : "get",
                    headers: header
                });
            statusCode = response.status;
            const listOfImgboxes = await(response.json()) as ImgboxDto[];
            
            return listOfImgboxes;
            
        } catch (error) {
            console.log(error);

           return [];
           
        }

}

async function getTagCountPage(
    token : string,
     page : number,
     size : number) : Promise<TagCountResponse>{

    const BASE_URL ="http://localhost:8080/api/tags/list/all/count";
    const header = new Headers();
    let statusCode = -1;
    header.append("Authorization", "Bearer " + token);

    try {

        const response = await fetch(`${BASE_URL}?page=${page}&size=${size}`,
            {
                method : "get",
                headers: header
            });
        statusCode = response.status;
        const responseBody = await(response.json());
        const data : TagCount[] = responseBody["content"];
        const pageInfo : PagingData = responseBody["page"];
        return {
            data : data,
            pageMetadata : pageInfo,
            statusCode : statusCode
        }
        
    } catch (error) {
        console.log(error);

       return {
            data : [],
            statusCode : statusCode
       };
       
    }
}

async function getImgboxesByTag(tag : string, token : string)
 : Promise<ImgboxDto[]>{

    const BASE_URL ="http://localhost:8080/api/tags/list/imgboxes/";
    const header = new Headers();
    let statusCode = -1;
    header.append("Authorization", "Bearer " + token);
    try {

        const response = await fetch(`${BASE_URL}${tag}`,
            {
                method : "get",
                headers: header
            });
        statusCode = response.status;
        const listOfImgboxes = await(response.json()) as ImgboxDto[];
        
        return listOfImgboxes;
        
    } catch (error) {
        console.log(error);

       return [];
       
    }

}

async function getUsersByPage(page : number, size : number) : Promise<PageUserInfo>{
    
    const PAGEABLE_URL = "http://localhost:8080/api/users";
    try{
        const response = await fetch(`${PAGEABLE_URL}?page=${page}&size=${size}`,
            {method : 'get'});
        const pageOfUserInfo : PageUserInfo = await (response.json());
        
        return pageOfUserInfo

    } catch(error){
        console.log(error);
        
    }
    return {content : null, page : null };
}

export {getImgboxList, getImgboxImage,
     getImgboxFromId, fetchSearchTermList,
      getTagCountPage,getImgboxesByTag,getUsersByPage,getPageableImgboxList};