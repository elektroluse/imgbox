import { useEffect, useState } from 'react'
import { dummyData } from './data/dummyData'
import DataTable from './components/DataTable/DataTable'
//import './App.css'

function App() {

  useEffect(() => {
    fetch("http://localhost:8080/api/v1/users",{method: "GET"})
      .then((response) => response.json())
      .then((data) => console.log(data))
      .catch(error => console.error(error))
  }

  )

  return (
    
      <main className="py-10 bg-gray-500 h-screen w-screen space-y-5">
      <h1 className = "font-bold text-center"> USERS </h1>
      <div className="max-w-lg mx-auto bg-slate-100 rounded-md p-5">
        <div className='space-y-2'>
            <DataTable data = {dummyData} />
            
        
        </div>
      </div>
      </main>
  )
}

export default App
