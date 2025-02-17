import { Link } from "react-router-dom";
import { Badge } from "../ui/badge";

type LinkTagProps =   {

    name : string
}

const LinkTag = ({name} : LinkTagProps) => {

    return(
        <Link to={"/tag/" + name}>
            <Badge className="m-0.5">{name}</Badge>
        </Link>
    )

}

export default LinkTag;
    