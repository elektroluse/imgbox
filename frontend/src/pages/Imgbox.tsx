import { useEffect, useState } from "react";
import { useLocation, useParams } from "react-router-dom";
import { BlobResponse, getImgboxFromId, getImgboxImage, SingleImgboxResponse } from "../services/fetchService";
import { ImgboxDto } from "../types/ImgboxDto";
import { useAuth } from "../services/AuthProvider";
import ImgboxComp from "../components/ImgboxComp";
import Header from "../components/Navbar/Header";
import { toast } from "sonner";
import ImgboxView from "../components/ImgboxView/ImgboxView";

function Imgbox(){
    const location = useLocation();
    const auth = useAuth();
    const [fetchedImgbox, setFetchedImgbox] = useState<ImgboxDto|null>();
    const [image, setImage] = useState("");
    const [error,setError] = useState(false)
    const params = useParams();

   
    
    useEffect( () => {
        const fetchOnlyImage = async (data : ImgboxDto) => {   
            const response = await getImgboxImage(data,auth.token) as BlobResponse;
            if (response.status === 200){
                setImage(URL.createObjectURL(response.blob));
                setError(false);
            }else{
                setError(true);
                toast.error("The image could not be fetched from server!");
            }
        }

        const fetchAll = async () => {
            const response = await getImgboxFromId(params.imgbox!,auth.token) as SingleImgboxResponse;
            if(response.status === 200){
                fetchOnlyImage(response.data);
                setFetchedImgbox(response.data);
                setError(false);
            }else{
                setError(true);
                toast.error("The imgbox does not exist!")
            } 
        }
        if(location.state !== null){
            fetchOnlyImage(location.state);
        }
        else{
            fetchAll();
        } 
        
    },[params.imgbox,fetchedImgbox]

    )



    return(
        <main className="bg-gray-400 min-h-screen w-screen space-y-5 ">
            <Header/>
            {
                location.state !== null &&
                
                <ImgboxView imgboxDto={location.state} image={image}/>         
                
            }
            {
                fetchedImgbox !== null && typeof(fetchedImgbox) !== "undefined" &&
                !error &&
                  
                <ImgboxView imgboxDto={fetchedImgbox} image={image} />
            
            }
            {
                error &&
                <p>ERROR : 404</p>
            }
            
            
            
            

        </main>
    )



}

export default Imgbox;