import { UserType } from "../types/UserType"

interface UserListItemProps{
    user: UserType
}

export default function UserListItem({user}: UserListItemProps){
    return(
        <div>
            <label className="flex items-center gap-2 border border-gray-600 bg-white hover:bg-slate-50">
                <span className="font-bold text-gray-600">
                    {user.username}    
                </span>
            </label>
            
        </div>
    )
}