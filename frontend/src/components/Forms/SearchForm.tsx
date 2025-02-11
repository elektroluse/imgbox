import { zodResolver } from "@hookform/resolvers/zod";
import { useState } from "react";
import {useForm } from "react-hook-form";
import { z } from "zod";
import { Button } from "../ui/button";
import { Form, FormControl, FormDescription, FormField, FormItem, FormLabel, FormMessage } from "../ui/form";
import { Input } from "../ui/input";
import { Search } from "lucide-react";
import { fetchSearchTermList } from "../../services/fetchService";
import { useAuth } from "../../services/AuthProvider";
import { ApiResponse } from "../../types/ApiResponse";
import { ImgboxDto } from "../../types/ImgboxDto";

export type SearchFormProps = {
    loggedIn : boolean,
    setSearchData : (data : ImgboxDto[]) => void
}


const formSchema = z.object({
  search: z.string().min(1).max(255)
  
});


export default function SearchForm({loggedIn,setSearchData} : SearchFormProps) {
  const auth = useAuth();
  const form = useForm < z.infer < typeof formSchema >> ({
    defaultValues : {
      search : ""
      
    },
    resolver: zodResolver(formSchema),
  })

  const onSubmit = (values: z.infer < typeof formSchema > ) => {
    const sendSearchRequest = async () => {
        const response = await fetchSearchTermList(values.search, auth.token) as ImgboxDto[];
        setSearchData(response)
    }
    try {
        sendSearchRequest();
        
    } catch (error) {
        console.log(error);
    }
  }

  return(
    <Form {...form}>
    <form onSubmit={form.handleSubmit(onSubmit)} className="flex w-full max-w-sm items-center space-x-2 mx-auto">
     
      <FormField
        control={form.control}
        name="search"
        render={({ field }) => (
          <FormItem className="w-full">
            <FormControl>
              <Input 
              disabled = {!loggedIn}
              placeholder="Search by title"
              type="search"
              {...field} />
            </FormControl>
          </FormItem>
        )}
      />

    <Button 
        type="submit"
        disabled = {!loggedIn}
        variant="outline"
        size="icon">
        <Search />
    </Button>
      </form>
    </Form>
    
  )


}
  