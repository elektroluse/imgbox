import { useEffect, useState } from 'react';
import {HashRouter, Routes, Route} from 'react-router-dom';
import { dummyData } from './data/dummyData';
import DataTable from './components/DataTable/DataTable';
import { UserInfo } from './types/UserInfo';
import Header from './components/Navbar/Header';
import { Home } from './pages/Home';
import Users from './pages/Users';
import { Login } from './pages/Login';
import { Register } from './pages/Register';
import { Tags } from './pages/Tags';
import AuthProvider from './services/AuthProvider';
import { Upload } from './pages/Upload';
import { Toaster } from 'sonner';
import UserProfile from './pages/UserProfile';
//import './App.css'

const BASE_URL = 'http://localhost:8080/api/v1';

function App() {
  return(
    
    <HashRouter>
      <Toaster richColors/>
      <AuthProvider>
        <Routes>
          <Route path="/" element = {<Home />} />
          <Route path="/users" element = {<Users />} />
          <Route path="/tags" element = {<Tags />} /> 
          <Route path="/login" element = {<Login />} /> 
          <Route path="/register" element = {<Register />} />
          <Route path="/upload" element = {<Upload />} />
          <Route path="user/:username" element = {<UserProfile />} />  

        </Routes>
      </AuthProvider>
    </HashRouter>
  )

  
}

export default App
