import { zodResolver } from "@hookform/resolvers/zod";
import { useForm } from "react-hook-form";
import { z } from "zod";
import { EditDalogProps } from "../DialogComp/EditDialog";
import { useAuth } from "../../services/AuthProvider";
import {
    Form,
    FormControl,
    FormDescription,
    FormField,
    FormItem,
    FormLabel,
    FormMessage,
  } from "../../components/ui/form"
import { Input } from "../ui/input";
import { Textarea } from "../ui/textarea";
import { TagsInput } from "../ui/tags-input";
import { Button } from "../ui/button";
import { toast } from "sonner";
import { sendEditRequest } from "../../services/AuthHelper";
  

const formSchema = z.object({
  id: z.coerce.bigint(),
  title: z.string().min(1).max(255),
  description: z.string().max(2048).optional(),
  tags: z.array(z.string()).nonempty("Please at least one item").max(4),

});


export default function EditForm({currentData} : EditDalogProps){
    const auth = useAuth();
    function onSubmit(values: z.infer < typeof formSchema > ) {
        console.log("Edit submitted");
        const initEditRequest = async () => {
          
          const result = await sendEditRequest(values, auth.token);
          if(result == 200){
            toast.success("Edit executed");
          }else{
            toast.error("Edit failed");
          }
        }
        
        try {
          initEditRequest();
          
          
         
        } catch (e : any) {
          console.log(e);
          toast.error(e);
        }
             
      }

    

    const form = useForm < z.infer < typeof formSchema >> ({
    resolver: zodResolver(formSchema),
    defaultValues: {
        id : currentData.id,
        title: currentData.title,
        description: currentData.description,
        tags: currentData.tags    
    },
    })

    return(
        <Form {...form}>
        <form onSubmit={form.handleSubmit(onSubmit)} className="space-y-8 max-w-3xl mx-auto py-10">
          
          <FormField
            control={form.control}
            name="title"
            render={({ field }) => (
              <FormItem>
                <FormLabel>Title</FormLabel>
                <FormControl>
                  <Input 
                  placeholder="Your title"
                  
                  type="text"
                  {...field} />
                </FormControl>
                <FormDescription>Title of your imgbox</FormDescription>
                <FormMessage />
              </FormItem>
            )}
          />
          
          <FormField
            control={form.control}
            name="description"
            render={({ field }) => (
              <FormItem>
                <FormLabel>Description</FormLabel>
                <FormControl>
                  <Textarea
                    placeholder="Placeholder"
                    className="resize-none"
                    {...field}
                  />
                </FormControl>
                <FormDescription>Describe your imgbox</FormDescription>
                <FormMessage />
              </FormItem>
            )}
          />
          
          <FormField
            control={form.control}
            name="tags"
            render={({ field }) => (
              <FormItem>
                <FormLabel>Tags</FormLabel>
                <FormControl>
                  <TagsInput
                    value={field.value}
                    onValueChange={field.onChange}
                    placeholder="Enter your tags"
                  />
                </FormControl>
                <FormDescription>Add tags.</FormDescription>
                <FormMessage />
              </FormItem>
            )}
          />
        <Button type="submit" > Submit
        </Button>
      </form>
    </Form>
    )

}