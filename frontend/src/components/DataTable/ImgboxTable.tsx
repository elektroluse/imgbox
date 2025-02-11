import { ImgboxDto } from "../../types/ImgboxDto";
import { Badge } from "../ui/badge";
import { Table, TableBody, TableCell, TableHead, TableHeader, TableRow } from "../ui/table";
import { Link } from "react-router-dom";

interface ImgboxTableProps {

    data : ImgboxDto[]
}


const ImgboxTable = ({data} : ImgboxTableProps) => {
   
    return(
        <div className = "rounded-md border">
            <Table>
                <TableHeader>
                    <TableRow>
                        <TableHead>Title</TableHead>
                        <TableHead>Tags</TableHead>
                        <TableHead className="w-[100px]"></TableHead>
                    </TableRow>
                </TableHeader>
                <TableBody>
                    {data.map((imgboxDto) =>
                        <TableRow key = {imgboxDto.id}>
                            <TableCell>{imgboxDto.title}</TableCell>
                            <div className="flex gap-3">
                                <TableCell>{
                                    
                                    imgboxDto.tags.map(
                                    (tag,index) => <Badge key={index}> {tag} </Badge>)}
                                    
                                </TableCell>
                                </div>
                            <TableCell>
                                <Link 
                                to = {"/imgbox/" + imgboxDto.id}
                                state={imgboxDto}
                                className="hover:text-blue-800 hover:underline"
                                > View </Link>
                            </TableCell>
                        </TableRow>                   
                    )}
                </TableBody>
            </Table>
        </div>
    )
}

export default ImgboxTable;

