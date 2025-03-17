import { PagingData } from "./PagingData"
import { UserInfo } from "./UserInfo"

export type PageUserInfo = {

    content : UserInfo[] | null,
    page : PagingData | null
}