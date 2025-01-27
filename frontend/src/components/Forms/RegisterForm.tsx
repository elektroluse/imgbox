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
  Button
} from "../ui/button"
import {
  Form,
  FormControl,
  FormDescription,
  FormField,
  FormItem,
  FormLabel,
  FormMessage,
} from "../ui/form"
import {
  Input
} from "../ui/input"
import {
  PasswordInput
} from "../ui/password-input"
import {sendRegisterDto} from "../../services/AuthHelper"
import { RegisterResponseDto } from "../../types/RegisterResponseDto"

const formSchema = z.object({
  username: z.string().min(3).max(20),
  password: z.string().min(3).max(72)
});

//const BASE_URL = 'http://localhost:8080/api/'

export default function MyForm() {
  const [message,setMessage] = useState("");
  const [success,setSuccess] = useState(false)

  const form = useForm < z.infer < typeof formSchema >> ({
    defaultValues : {
      username : "",
      password : ""
    },
    resolver: zodResolver(formSchema),

  })
  


  
  function onSubmit(values: z.infer < typeof formSchema > ) {
      
    const sendRegRequest = async () =>{
        const response = await (sendRegisterDto(values)) as RegisterResponseDto;
        if(response.completed){
          setSuccess(true);
          setMessage(response.message);
          toast.success(response.message);
        }
        else{
          setSuccess(false);
          setMessage(response.message);
          toast.error(response.message);
        }
      }
    try {
      
      
      sendRegRequest();
      

    } catch (error) {
      console.error("Form submission error", error);
      toast.error("Failed to submit the form. Please try again.");
    }
  }
  

  return (
    <Form {...form}>
      <form onSubmit={form.handleSubmit(onSubmit)} className="space-y-8 max-w-3xl mx-auto py-10">
        
        <FormField
          control={form.control}
          name="username"
          render={({ field }) => (
            <FormItem>
              <FormLabel>Username</FormLabel>
              <FormControl>
                <Input 
                placeholder="Enter your desired username"
                
                type="text"
                {...field} />
              </FormControl>
              <FormDescription>This is your public display name.</FormDescription>
              <FormMessage />
            </FormItem>
          )}
        />
        
        <FormField
          control={form.control}
          name="password"
          render={({ field }) => (
            <FormItem>
              <FormLabel>Password</FormLabel>
              <FormControl>
                <PasswordInput placeholder="Placeholder" {...field} />
              </FormControl>
              <FormDescription>Enter your password.</FormDescription>
              <FormMessage />
            </FormItem>
          )}
        />
        
        <Button type="submit">Register</Button>
        <div>
          <p className={`underline ${success ? "text-green-800" : "text-red-700"} ` }>{message}</p>
          </div>

      </form>
    </Form>
    
  )
}