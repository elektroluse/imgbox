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

const formSchema = z.object({
  username: z.string().min(3).max(20),
  password: z.string().min(3).max(72)
});

const BASE_URL = 'http://localhost:8080/api/'

export default function LoginForm() {
  const [isLoggedIn,setIsLoggedIn] = useState("");
  const [success,setSuccess] = useState(false)

  const form = useForm < z.infer < typeof formSchema >> ({
    defaultValues : {
      username : "",
      password : ""
    },
    resolver: zodResolver(formSchema),

  })
  


  
  function onSubmit(values: z.infer < typeof formSchema > ) {
    
    const sendLoginDto = async () => {
        const header = new Headers();
        header.append("Content-Type","application/json");
        //header.append("Access-Control-Allow-Origin")
  
        try{
          const response = await fetch("http://localhost:8080/api/auth/login",
             {method : 'POST',
               body : JSON.stringify(values),
               headers : header,
               mode : "cors"
               }
            );
          console.log(response.status)
          console.log(response.text())
          const responseToken = response.json().then(value => value.accesToken);
          if(response.status == 200){

            setIsLoggedIn("Login successful : " + values.username + "\n" + responseToken);
            setSuccess(true);
          }
          else{
            setIsLoggedIn("Login failed : " + values.username);
            setSuccess(false);
          }
         
        } catch (e) {
          console.error(e)
        }
    }



    try {
    
      sendLoginDto();

      toast(
        <pre className="mt-2 w-[340px] rounded-md bg-slate-950 p-4">
          <code className="text-white">{JSON.stringify(values, null, 2)}</code>
        </pre>
      );
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
                placeholder="Enter your username"
                
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
        
        <Button type="submit">Log in</Button>
        <div>
          <p className={`underline ${success ? "text-green-800" : "text-red-700"} ` }>{isLoggedIn}</p>
          </div>

      </form>
    </Form>
    
  )
}