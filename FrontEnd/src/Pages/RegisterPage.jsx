import React from 'react'
import NavigationBar from '../Components/NavigationBar'
import RegisterationForm from '../Components/RegisterationForm'

const RegisterPage = () => {
  return (
    <div className='flex flex-row h-screen justify-center items-center bg-blue-50'>
        <div className="w-1/2 flex justify-center items-center">
            <div className="flex h-screen w-full">
                <img src="/background.jpg" alt="" className="" />
            </div>
        </div>
        <div className="w-1/2 flex flex-col h-screen gap-6 items-center justify-center">
            <div className="flex flex-col w-[60%] justify-center gap-6">
                <span className=" text-blue-900 text-2xl font-semibold">Sign up now and start shopping smarter</span>
            </div>
            <div className="w-[60%] shadow-2xl">
                <RegisterationForm/>
            </div>
        </div>

    </div>
  )
}

export default RegisterPage