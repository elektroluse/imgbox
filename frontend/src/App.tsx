import { useState } from 'react'
import { dummyData } from './data/DummyData'
import UserListItem from './components/UserListItem'
//import './App.css'

function App() {

  return (
    
      <main className="py-10 bg-gray-500 h-screen w-screen space-y-5">
      <h1 className = "font-bold text-center"> USERS </h1>
      <div className="max-w-lg mx-auto bg-slate-100 rounded-md p-5">
        <div className='space-y-2'>
          {dummyData.map(UserType => (
            <UserListItem
             key={UserType.id}
             user={UserType} />
            
          ))}
        </div>
      </div>
      </main>
  )
}

export default App
