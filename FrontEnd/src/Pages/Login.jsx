import React from 'react'
import LoginForm from '../Components/LoginForm'

const Login = () => {
  return (
<div className='flex flex-row h-screen justify-center items-center bg-gray-100'>
        <div className=" relative w-1/2 h-screen flex justify-center items-center">
        <div className="w-[100%] h-[100%]">
            <img src="/background.jpg" alt="" />
        </div>

        </div>
        <div className="w-1/2 h-screen flex flex-col gap-6 items-center justify-center bg-blue-50 ">
            <div className="flex flex-col w-[60%] justify-center gap-6">
                <span className=" text-blue-900 text-5xl font-bold">Erands</span>
                <span className="text-gray-500">Lorem ipsum dolor, sit amet consectetur adipisicing elit. Quam, at architecto commodi est consequatur facilis tenetur debitis dignissimos eligendi totam.</span>
            </div>
            <div className="w-[60%] shadow-2xl">
                <LoginForm/>
            </div>
        </div>

    </div>
  )
}

export default Login