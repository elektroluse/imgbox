import { Table, TableBody, TableCell, TableHead, TableHeader, TableRow } from "../ui/table";
import { TagCount } from "../../types/TagCount";
import LinkTag from "../LinkTags/LinkTag";
interface TagCountTableProps{

    data: TagCount[];
}

const TagCountTable = ({data}: TagCountTableProps) =>{
    return(
        <div className = "rounded-md border">
            <Table>
                <TableHeader>
                    <TableRow>
                        <TableHead>Tag</TableHead>
                        <TableHead>Count</TableHead>
                        <TableHead className="w-[100px]"></TableHead>
                    </TableRow>
                </TableHeader>
                <TableBody>
                    {data.map((tagCount,index) =>
                        <TableRow key = {index}>
                           
                            
                                <TableCell>
                                    <LinkTag name = {tagCount.name}/>
                                </TableCell>
                                <TableCell>{tagCount.count}</TableCell>
                                
                            <TableCell></TableCell>
                        </TableRow>                   
                    )}
                </TableBody>
            </Table>
        </div>
    )
};

export default TagCountTable;