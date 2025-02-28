import { toast } from "sonner"
import { Button } from "../../components/ui/button"
import {
  Dialog,
  DialogContent,
  DialogDescription,
  DialogFooter,
  DialogHeader,
  DialogTitle,
  DialogTrigger,
} from "../../components/ui/dialog"
import { sendDeleteRequest } from "../../services/AuthHelper"
import { useAuth } from "../../services/AuthProvider"
import { ImgboxDto } from "../../types/ImgboxDto"
import EditForm from "../Forms/EditForm"
 
 export type EditDalogProps = {
    currentData : ImgboxDto
 } 

 

export function EditDialog({currentData} : EditDalogProps) {
  const auth = useAuth();
  const deleteRequest = async () => {
    try{
        const result = await sendDeleteRequest(currentData.id, auth.token);
        if(result == 200){
          toast.success("Deleted the imgbox!");
        }else{
          toast.error("Could not delete imgbox!");
        }
    }
    catch(error){
      console.log(error);
      toast.error("Error when trying to delete");
    }
  }

  return (
    <Dialog>
      <DialogTrigger asChild>
        <Button className="w-full"variant="link">Edit Imgbox</Button>
      </DialogTrigger>
      <DialogContent className="sm:max-w-[425px]">
        <DialogHeader>
          <DialogTitle>Edit imgbox</DialogTitle>
          <DialogDescription>
            Make changes to your imgbox here. Click submit when you're done.
          </DialogDescription>
        </DialogHeader>
            <EditForm currentData={currentData}/>
        <DialogFooter>
          <Button
          className="w-full"
          variant={"destructive"} 
          onClick={deleteRequest} >Delete imgbox</Button>
        </DialogFooter>
      
      </DialogContent>
    </Dialog>
  )
}