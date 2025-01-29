import { UserInfo } from "./UserInfo"

export type ImgboxDto = {

    id : bigint,
    title : string,
    description : string,
    tags : string[]
    fileUrl : string,
    user : UserInfo,
    createdAt : Date
};
