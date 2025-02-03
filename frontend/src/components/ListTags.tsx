import { Badge } from "./ui/badge";

interface ListTagsProps  {

    data : string[]
}

const ListTags = ({data} : ListTagsProps) => {
        return(
        <>
        {
            data.map(
            (tag) => <Badge> {tag} </Badge>)
        }
        </>
        )
}

export default ListTags;