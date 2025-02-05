
import { ImgboxDto } from "../types/ImgboxDto";
import { useAuth } from "../services/AuthProvider";

import ListTags from "./ListTags";

type ImgboxCompProps = {
    imgboxDto : ImgboxDto
}

 const ImgboxComp = ({imgboxDto} : ImgboxCompProps) => {
    const auth = useAuth();
    
    return(
        <main className="">
            <div>
                <p> Title : {imgboxDto.title} </p>
                <p> Description : {imgboxDto.description}</p>
            
                Tags : <ListTags data = {imgboxDto.tags} />
                

            </div>


        </main>
    )

}
export default ImgboxComp;