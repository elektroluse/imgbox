import { ImgboxDto } from "./ImgboxDto";
import { PagingData } from "./PagingData";

export type ImgboxListPage = {
    data : ImgboxDto[],
    pageMetadata : PagingData,
    statusCode : number
}