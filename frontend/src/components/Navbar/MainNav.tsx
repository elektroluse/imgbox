import { House } from 'lucide-react'
import React from 'react'

export default function MainNav() {
  return (
    <div className="flex">
        <a href="/"> 
            <House className="text-orange-500 ml-3" />
        </a>
        
        <nav className="flex items-center gap-3 ml-5">
            <a href="/#/users">Users</a>
            <a href="/#/tags">Tags</a>
            <a href="/#/random">Random</a>
        </nav>
    </div>
  )
}
