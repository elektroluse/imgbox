import { ImgboxDto } from "../../types/ImgboxDto";
import { Table, TableBody, TableCell, TableHead, TableHeader, TableRow } from "../ui/table";

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
                            <a href = {"#"}> {/* Link to the imgbox page for this element */}
                                <TableCell>{imgboxDto.tags}</TableCell>
                                </a>
                            <TableCell></TableCell>
                        </TableRow>                   
                    )}
                </TableBody>
            </Table>
        </div>
    )
}

export default ImgboxTable;

