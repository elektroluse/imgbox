import { ColumnDef } from "@tanstack/react-table"
import { Link } from "react-router-dom"

export type UserColumn = {
   
    id : number;
    username : string
    
}

export const usernameAndId : ColumnDef<UserColumn>[] = [

    {
        accessorKey : "id",
        header : "Id",
        cell: ({row}) => {
            const id : string = row.getValue("id");
            return id;
        }
    },
    {
        accessorKey:"username",
        header : "Username",
        cell:({row}) => {
             const username : string = row.getValue("username");
             return <Link
                className="hover:text-blue-800 hover:underline" 
                to={"/user/" + username}>{username}</Link>
        }
    }
    
]