import { TruckIcon } from '@heroicons/react/16/solid';
import React, { useState } from 'react'
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import { useDispatch } from 'react-redux';
import { login } from '../Redux/userSlice';
import { data } from 'autoprefixer';
const LoginForm = () => {
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [errorMessage, setErrorMessage] = useState('');
    const navigate = useNavigate();
    const dispatch = useDispatch();

    const handleSubmit = async (e) => {
        e.preventDefault();
        setErrorMessage("")

        const formData = {
            email,
            password
        };

        try {
            const response = await axios.post("http://localhost:8080/authenticate/login", formData);

            console.log(response.data.token); // Handle successful response
            dispatch(login({userId:response.data.userId,userName:response.data.userName}))
            document.cookie = `token=${response.data.token}; path=/;`;
            navigate("/")
        } 
        catch (error) {
            // Handle HTTP errors and other errors
            if (error.response) {
                // Request was made and server responded with a status code
                if (error.response.status === 401) {
                    setErrorMessage('Invalid username or password');
                } else {
                    setErrorMessage('An error occurred. Please try again.');
                }
            } else if (error.request) {
                // Request was made but no response was received
                setErrorMessage('No response received from the server. Please check your network.');
            } else {
                // Something happened in setting up the request that triggered an Error
                setErrorMessage('An error occurred while setting up the request.');
            }
            console.error('Login failed:', error);
        }
    }
  return (
    <div className='bg-white rounded-md p-8 shadow-lg'>
        <form action="" className="flex flex-col gap-4">
            <div className="flex flex-col">
                <label htmlFor="" className="">Email address</label>
                <input 
                 value={email}
                 onChange={(e)=> setEmail(e.target.value)}
                 type="email"
                 placeholder='e.g., xyz@gmail.com'
                 className="p-2 border rounded focus:outline-none focus:ring-2 focus:ring-blue-950" />
            </div>

            <div className="flex flex-col">
                <label htmlFor="" className="">Password</label>
                <input 
                 value={password}
                 onChange={(e)=> setPassword(e.target.value)}
                 type="password" 
                 placeholder='Enter your password' 
                 className="p-2 border rounded focus:outline-none focus:ring-2 focus:ring-blue-950" />
            </div>
            {errorMessage.length !=0 
                ?<div className=" flex items-center justify-center">
                    <span className="text-red-600">{errorMessage}</span>
                </div>
                :<></>
            }
            <button type="submit" onClick={handleSubmit} className="mt-4 p-2 bg-blue-900 text-white rounded-md hover:bg-blue-950">Login</button>
        </form>

    </div>
  )
}

export default LoginForm