import React, { useState } from 'react';
import axios from 'axios';

const RegistrationForm = () => {
  const [firstName, setFirstName] = useState("");
  const [lastName, setLastName] = useState("");
  const [phNo, setPhone] = useState("");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [rePassword, setRePassword] = useState("");
  const [birthDate, setBirthDate] = useState("");

  const handleSubmit = async (e) => {
    e.preventDefault();

    const formData = {
      firstName,
      lastName,
      phNo,
      email,
      password,
      rePassword,
      birthDate,
    };

    try {
        const res = axios.post("http://localhost:8080/public/register",formData)
    //   console.log(JSON.stringify(formData, null, 2));
    console.log(res)
    } catch (error) {
      console.error("There was an error submitting the form:", error);
    }
  };

  return (
    <div className='bg-white rounded-md p-8 shadow-lg'>
      <form className="flex flex-col gap-6" onSubmit={handleSubmit}>
        <div className="w-full flex flex-row gap-4">
          <div className="flex flex-col w-[50%]">
            <label htmlFor="firstName" className="mb-2 text-gray-700 font-semibold">First Name</label>
            <input 
              id="firstName" 
              type="text" 
              placeholder='e.g., Nick' 
              className="p-2 border rounded focus:outline-none focus:ring-2 focus:ring-blue-400"
              value={firstName}
              onChange={(e) => setFirstName(e.target.value)}
            />
          </div>

          <div className="flex flex-col w-[50%]">
            <label htmlFor="lastName" className="mb-2 text-gray-700 font-semibold">Last Name</label>
            <input 
              id="lastName" 
              type="text" 
              placeholder='e.g., Colman' 
              className="p-2 border rounded focus:outline-none focus:ring-2 focus:ring-blue-400"
              value={lastName}
              onChange={(e) => setLastName(e.target.value)}
            />
          </div>
        </div>

        <div className="flex flex-col">
          <label htmlFor="phoneNumber" className="mb-2 text-gray-700 font-semibold">Phone Number</label>
          <input 
            id="phoneNumber" 
            type="tel" 
            placeholder='e.g., +1 234 567 890' 
            className="p-2 border rounded focus:outline-none focus:ring-2 focus:ring-blue-950"
            value={phNo}
            onChange={(e) => setPhone(e.target.value)}
          />
        </div>

        <div className="flex flex-col">
          <label htmlFor="email" className="mb-2 text-gray-700 font-semibold">Email</label>
          <input 
            id="email" 
            type="email" 
            placeholder='e.g., xyz@gmail.com' 
            className="p-2 border rounded focus:outline-none focus:ring-2 focus:ring-blue-950"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
          />
        </div>

        <div className="flex flex-col">
          <label htmlFor="password" className="mb-2 text-gray-700 font-semibold">Password</label>
          <input 
            id="password" 
            type="password" 
            placeholder='Enter your password' 
            className="p-2 border rounded focus:outline-none focus:ring-2 focus:ring-blue-950"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
          />
        </div>

        <div className="flex flex-col">
          <label htmlFor="rePassword" className="mb-2 text-gray-700 font-semibold">Re-enter Password</label>
          <input 
            id="rePassword" 
            type="password" 
            placeholder='Re-enter your password' 
            className="p-2 border rounded focus:outline-none focus:ring-2 focus:ring-blue-950"
            value={rePassword}
            onChange={(e) => setRePassword(e.target.value)}
          />
        </div>

        <div className="flex flex-col">
          <label htmlFor="birthDate" className="mb-2 text-gray-700 font-semibold">Birth Date</label>
          <input 
            id="birthDate" 
            type="date" 
            className="p-2 border rounded focus:outline-none focus:ring-2 focus:ring-blue-950"
            value={birthDate}
            onChange={(e) => setBirthDate(e.target.value)}
          />
        </div>

        <button type="submit" className="mt-4 p-2 bg-blue-900 text-white rounded-md hover:bg-blue-950">Register</button>
      </form>
    </div>
  );
};

export default RegistrationForm;
