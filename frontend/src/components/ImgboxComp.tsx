import { useEffect, useState } from "react";
import { ImgboxDto } from "../types/ImgboxDto";
import { useAuth } from "../services/AuthProvider";
import { BlobResponse, getImgbox } from "../services/fetchService";
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
                <p>
                    Tags : <ListTags data = {imgboxDto.tags} />
                </p>

            </div>


        </main>
    )

}
export default ImgboxComp;