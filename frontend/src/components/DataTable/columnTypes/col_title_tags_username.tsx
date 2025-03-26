import { ColumnDef } from "@tanstack/react-table"
import ListTags from "../../ListTags"
import { Link } from "react-router-dom"
import { UserInfo } from "../../../types/UserInfo"

export type ImgboxColumn = {
    id : bigint,
    title : string,
    description : string,
    tags : string[],
    fileUrl : string,
    user : UserInfo,
    createdAt : Date


}

export const titleTagsUsername: ColumnDef<ImgboxColumn>[] = [

    {
        accessorKey : "title",
        header : "Title",
        cell:({row}) => {
            
           return <Link 
            to = {"/imgbox/" + row.original.id}
            state={row.original}
            className="hover:text-blue-800 hover:underline"
            > {row.original.title} </Link>
          
        }
        
    },
    {
        accessorKey : "tags",
        header : "Tags",
        cell: ({row}) => {
            const tags : string[] = row.getValue("tags")
            return <ListTags data = {tags}/ >
        }
    },
    {
        accessorKey:"user",
        header : "User",
        cell:({row}) => {
             const userInfo : UserInfo = row.getValue("user");
             return <Link
                className="hover:text-blue-800 hover:underline" 
                to={"/user/" + userInfo.username}>{userInfo.username}</Link>
        }
    }
    
]