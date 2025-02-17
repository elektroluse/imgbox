import { Link } from "react-router-dom";
import { Table, TableBody, TableCell, TableHead, TableHeader, TableRow } from "../ui/table";
import { TagCount } from "../../types/TagCount";
import { Badge } from "../ui/badge";
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
                                    <Link
                                    className="hover:text-blue-800 hover:underline" 
                                    to={"/tag/" + tagCount.name}><Badge>{tagCount.name}</Badge></Link>
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