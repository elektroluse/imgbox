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

export default function MyForm() {

  const form = useForm < z.infer < typeof formSchema >> ({
    resolver: zodResolver(formSchema),

  })
  


  
  function onSubmit(values: z.infer < typeof formSchema > ) {
    
    
    const fetchUsers = async () => {
        
  
        try{
          const response = await fetch(`api/auth/register`, {method : 'POST', body : JSON.stringify(values)});
          console.log(response.status)
          console.log(response.json())
        } catch (e) {
          console.error(e)
        }
    }



    try {
      console.log(values);
      fetchUsers()



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
        
        <Button type="submit">Submit</Button>
      </form>
    </Form>
  )
}