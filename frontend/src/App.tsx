import { useEffect, useState } from 'react'
import { dummyData } from './data/dummyData'
import DataTable from './components/DataTable/DataTable'
import { UserInfo } from './types/UserInfo';
import Header from './components/Navbar/Header';
//import './App.css'

const BASE_URL = 'http://localhost:8080/api/v1'

function App() {

  const [error, setError] = useState();
  const [isLoading, setIsLoading] = useState(false);
  const [users, setUsers] = useState<UserInfo[]>([]);

  useEffect(() => {
    const fetchUsers = async () => {
      setIsLoading(true);

      try{
        const response = await fetch(`${BASE_URL}/users`, {method : 'get'});
        const users = (await response.json()) as UserInfo[];
        setUsers(users);
      } catch (e : any) {
        setError(e)
      } finally{
        setIsLoading(false);
      }
            

      
    };

    fetchUsers()
  }, [])
   
  if(isLoading){
    
    return <div>
      <Header />
      LOADING...
      
      </div>
  }

  if(error){
    
    return <div>
      <Header />
        ERORR! Try again
      </div>
  }

  return (
      
      <main className="bg-gray-400 h-screen w-screen space-y-5">
          <Header />
      <h1 className = "font-bold text-center"> USERS </h1>
      <div className="max-w-lg mx-auto bg-slate-100 rounded-md p-5">
        <div className='space-y-2'>
            <DataTable data = {users} />
            
        
        </div>
      </div>
      </main>
  )
}

export default App
