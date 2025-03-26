import { PagingData } from "./PagingData"
import { TagCount } from "./TagCount"

export type TagCountResponse = {
    data : TagCount[],
    pageMetadata : PagingData,
    statusCode : number
}