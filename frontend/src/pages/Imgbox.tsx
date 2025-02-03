import { useEffect, useState } from "react";
import { useLocation, useParams } from "react-router-dom";
import { BlobResponse, getImgbox } from "../services/fetchService";
import { ImgboxDto } from "../types/ImgboxDto";
import { useAuth } from "../services/AuthProvider";
import ImgboxComp from "../components/ImgboxComp";
import Header from "../components/Navbar/Header";

function Imgbox(){
    const location = useLocation();
    const auth = useAuth();
    const [image, setImage] = useState("");
    
    useEffect( () => {
        (async () => {
        const response = await getImgbox(location.state,auth.token) as BlobResponse;
        if (response.status === 200){
            setImage(URL.createObjectURL(response.blob));
        }

        }
            
    )()
        
    },[]

    )


    return(
        <main>
            <Header/>
            {
                location.state !== null &&
                <ImgboxComp imgboxDto={location.state}/>
                
            }
            <img src={image}></img>
            
            

        </main>
    )



}

export default Imgbox;