import { House } from 'lucide-react'
import React from 'react'
import { Link } from 'react-router-dom'

export default function MainNav() {
  return (
    <div className="flex">
        <Link to = "/"> 
            <House className="text-orange-500 ml-3 hover:bg-slate-300" />
        </Link>
        
        <nav className="flex items-center gap-3 ml-5">
            <Link to="/users" className="hover:bg-fuchsia-600">Users</Link>
            <Link to="/tags">Tags</Link>
            <Link to="/upload">Upload</Link>
        </nav>
    </div>
  )
}
