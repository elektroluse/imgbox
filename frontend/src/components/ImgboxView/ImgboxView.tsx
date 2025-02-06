import { ImgboxDto } from "../../types/ImgboxDto";

import ListTags from "../ListTags";
import { Link } from "react-router-dom";

type ImgboxViewProps = {
    imgboxDto : ImgboxDto,
    image : string
}

 const ImgboxView = ({imgboxDto,image} : ImgboxViewProps) => {
    
    return(
        
        <>
              <div className="max-w-lg mx-auto bg-orange-100 rounded-md">
                <header className="sticky top-0 w-full border-b bg-red-500 rounded-md">
                    <Link
                     to={"/user/" + imgboxDto.user.username} 
                     className="font-bold text-xl hover:underline ml-1">{imgboxDto.user.username}</Link>
                </header> 
                <h1 className="font-semibold text-5xl text-center m-1">{imgboxDto.title} </h1>
                
                <div className="justify-center sm:px-12 p-8 w-full ">
                    <img className="object-scale-down max-h-64 drop-shadow-md rounded-md m-auto" src={image}></img>
                    <div className="flex justify-center gap-1 m-2">
                        <ListTags data = {imgboxDto.tags} />
                        
                </div>
                <p className="w-full">{imgboxDto.description}</p>
                </div>
                
                
  

              </div>
            
        </>  
            

        


     
    )

}
export default ImgboxView;