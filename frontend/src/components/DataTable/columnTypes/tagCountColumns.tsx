import { ColumnDef } from "@tanstack/react-table"
import LinkTag from "../../LinkTags/LinkTag"


export type TagCountColumns = {
    name : string, 
    count : number
   
}

export const tagCountColumns: ColumnDef<TagCountColumns>[] = [

    {
        accessorKey : "name",
        header : "Tag",
        cell: ({row}) => {
            const tag : string = row.getValue("name")
            return <LinkTag name = {tag}/>
        }
    },
    {
        accessorKey:"count",
        header : "Count",
        cell:({row}) => {
             const count : number = row.getValue("count");
             return count;
        }
    }
    
]