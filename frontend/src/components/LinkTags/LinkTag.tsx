import { Link } from "react-router-dom";
import { Badge } from "../ui/badge";

type LinkTagProps =   {

    name : string
}

const LinkTag = ({name} : LinkTagProps) => {

    return(
        <Link to={"/tag/" + name}>
            <Badge>{name}</Badge>
        </Link>
    )

}

export default LinkTag;
    