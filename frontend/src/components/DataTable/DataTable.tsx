import { UserInfo } from "../../types/UserInfo";
import { Table, TableBody, TableCell, TableHead, TableHeader, TableRow } from "../ui/table";
interface DataTableProps{

    data: UserInfo[];
}

const DataTable = ({data}: DataTableProps) =>{
    return(
        <div className = "rounded-md border">
            <Table>
                <TableHeader>
                    <TableRow>
                        <TableHead>User Id</TableHead>
                        <TableHead>Username</TableHead>
                        <TableHead className="w-[100px]"></TableHead>
                    </TableRow>
                </TableHeader>
                <TableBody>
                    {data.map((userInfo) =>
                        <TableRow key = {userInfo.id}>
                            <TableCell>{userInfo.id}</TableCell>
                            <a href = {"#/user/" + userInfo.username}>
                                <TableCell>{userInfo.username}</TableCell>
                                </a>
                            <TableCell></TableCell>
                        </TableRow>                   
                    )}
                </TableBody>
            </Table>
        </div>
    )
};

export default DataTable;