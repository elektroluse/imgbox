import LinkTag from "./LinkTags/LinkTag";
import { Badge } from "./ui/badge";

interface ListTagsProps  {

    data : string[]
}

const ListTags = ({data} : ListTagsProps) => {
        return(
        <>
        {
            data.map(
            (tag,index) => <LinkTag name={tag} key={index} /> )
        }
        </>
        )
}

export default ListTags;