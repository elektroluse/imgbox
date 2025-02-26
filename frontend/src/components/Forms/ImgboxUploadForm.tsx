"use client"
import {
  useState
} from "react"
import {
  toast
} from "sonner"
import {
  useForm
} from "react-hook-form"
import {
  zodResolver
} from "@hookform/resolvers/zod"
import * as z from "zod"
import {
  cn
} from "../../lib/utils"
import {
  Button
} from "../../components/ui/button"
import {
  Form,
  FormControl,
  FormDescription,
  FormField,
  FormItem,
  FormLabel,
  FormMessage,
} from "../../components/ui/form"
import {
  Input
} from "../../components/ui/input"
import {
  Textarea
} from "../../components/ui/textarea"
import {
  TagsInput
} from "../../components/ui/tags-input"
import {
  CloudUpload,
  Paperclip
} from "lucide-react"
import {
  FileInput,
  FileUploader,
  FileUploaderContent,
  FileUploaderItem
} from "../../components/ui/file-upload"
import { sendUploadDto } from "../../services/AuthHelper"
import { UploadResponseDto } from "../../types/UploadResponseDto"
import { useAuth } from "../../services/AuthProvider"


const MAX_FILE_SIZE = 1000000 // 1MB
const ACCEPTED_IMAGE_TYPES = [
  "image/png",
  "image/jpeg",
  "image/svg+xml",
  "image/webp"
]

const formSchema = z.object({
  title: z.string().min(1).max(255),
  description: z.string().max(2048).optional(),
  tags: z.array(z.string()).nonempty("Please at least one item").max(4),
  files: z.array(
    z.instanceof(File).refine((file) => file.size < 4 * 1024 * 1024, {
      message: "File size must be less than 4MB",
    }),
  )
  .max(1, {
    message: "Maximum 1 files are allowed",
  })
  .nullable(),
});


export default function ImgboxUploadForm() {
  const auth = useAuth();
  //const [files, setFiles] = useState < File[] | null > ([]);
  
  const dropZoneConfig = {
    maxFiles: 1,
    maxSize: 1024 * 1024 * 4 * 1000,
    multiple: false,
  };
  const form = useForm < z.infer < typeof formSchema >> ({
    resolver: zodResolver(formSchema),
    defaultValues: {
      tags: ["test"],
      title: "",
      description: "",
      files : null
      
    },
  })

  


   function onSubmit(values: z.infer < typeof formSchema > ) {
    
    const sendUploadRequest = async () => {
      const serverResponse = await (sendUploadDto(values, auth.token, auth.user!)) as UploadResponseDto;
      if(serverResponse.success){
        toast.success(serverResponse.message);
      } 
      else{
        toast.error(serverResponse.message);
      }
      
    }

    try {
      
      sendUploadRequest();
      
      
     
    } catch (e : any) {
      console.log(e);
      toast.error(e);
    }
      
      
      
      
  }


  return (
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
        
            <FormField
              control={form.control}
              name="files"
              render={({field}) => (
                <FormItem>
                  <FormLabel>Select File</FormLabel>
                  <FormControl>
                    <FileUploader
                      value={field.value}
                      onValueChange={field.onChange}
                      dropzoneOptions={dropZoneConfig}
                    
                      className="relative bg-background rounded-lg p-2"
                    >
                      <FileInput
                        id="file"
                        className="outline-dashed outline-1 outline-slate-500"
                              

      
                      >
                        <div className="flex items-center justify-center flex-col p-8 w-full ">
                          <CloudUpload className='text-gray-500 w-10 h-10' />
                          <p className="mb-1 text-sm text-gray-500 dark:text-gray-400">
                            <span className="font-semibold">Click to upload</span>
                            &nbsp; or drag and drop
                          </p>
                          <p className="text-xs text-gray-500 dark:text-gray-400">
                            SVG, PNG, JPG or GIF
                          </p>
                        </div>
                  
                      </FileInput>
                      <FileUploaderContent className="">
                      {field.value && field.value.length > 0 && 
                    
                      field.value.map((file, i) => (
                        <FileUploaderItem
                          key={i}
                          index={i}>
                            
                          <Paperclip className="h-4 w-4 stroke-current" />
                          <span>{file.name}</span>
                        
                        </FileUploaderItem>
                      ))}
                  </FileUploaderContent>
                    </FileUploader>
                  </FormControl>
                  <FormDescription>Select a file to upload.</FormDescription>
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