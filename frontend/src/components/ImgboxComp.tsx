
import { ImgboxDto } from "../types/ImgboxDto";
import { useAuth } from "../services/AuthProvider";

import ListTags from "./ListTags";

type ImgboxCompProps = {
    imgboxDto : ImgboxDto
}

 const ImgboxComp = ({imgboxDto} : ImgboxCompProps) => {
    const auth = useAuth();
    
    return(
        
        <div className="max-w-lg mx-auto bg-orange-100 rounded-md p-5">
            <h1 className="font-semibold text-5xl"> Title : {imgboxDto.title} </h1>
            {imgboxDto.description.length > 0 &&
                <p> Description : {imgboxDto.description}</p>
            }
            <div className="flex items-center gap-1">
                Tags : <ListTags data = {imgboxDto.tags} />

            </div>
            
            

        </div>


     
    )

}
export default ImgboxComp;