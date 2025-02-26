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
import { Input } from "../../components/ui/input"
import { Label } from "../../components/ui/label"
import { ImgboxDto } from "../../types/ImgboxDto"
import EditForm from "../Forms/EditForm"
 
 export type EditDalogProps = {
    currentData : ImgboxDto
 } 

export function EditDialog({currentData} : EditDalogProps) {
  return (
    <Dialog>
      <DialogTrigger asChild>
        <Button variant="outline">Edit Imgbox</Button>
      </DialogTrigger>
      <DialogContent className="sm:max-w-[425px]">
        <DialogHeader>
          <DialogTitle>Edit imgbox</DialogTitle>
          <DialogDescription>
            Make changes to your imgbox here. Click save when you're done.
          </DialogDescription>
        </DialogHeader>
            <EditForm currentData={currentData}/>
        <DialogFooter>
        </DialogFooter>
      </DialogContent>
    </Dialog>
  )
}