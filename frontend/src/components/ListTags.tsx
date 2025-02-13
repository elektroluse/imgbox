import { Badge } from "./ui/badge";

interface ListTagsProps  {

    data : string[]
}

const ListTags = ({data} : ListTagsProps) => {
        return(
        <>
        {
            data.map(
            (tag,index) => <Badge className="m-0.5" key={index}> {tag} </Badge>)
        }
        </>
        )
}

export default ListTags;