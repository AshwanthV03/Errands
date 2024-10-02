import axios from 'axios';
import React, { useState } from 'react';
import { useSelector } from 'react-redux';
import { getCookie } from '../Handlers/RouteHandlers';

const EditUserForm = ({ setEditActive }) => {
  const user = useSelector((state)=>state.user.User)
  const [formData, setFormData] = useState({
    toUpdateUserId:user.userId,
    firstName: '',
    lastName: '',
    email: '',
    phone: ''
  });

  const handleChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    console.log('Form Data:', formData, getCookie('token'));
    try {
      const response = await axios.put(
        `http://localhost:8080/user/updateUser/${user.userId}`,
        formData, // Pass formData as the second argument (data)
        {
          headers: { Authorization: `Bearer ${getCookie('token')}` }
        }
      );
      if (response.status === 200) alert("User Updated");
    } catch (error) {
      console.log(error);
    }
  };

  return (
<div className="flex justify-center items-center min-h-screen bg-gray-100 bg-opacity-30 backdrop-blur-sm">
<div className="bg-white p-8 rounded-lg shadow-2xl w-full max-w-md">
        {/* Close Button */}
        <div 
          onClick={() => setEditActive(false)} 
          className="rounded-md border border-red-500 text-red-500 hover:bg-red-500 hover:text-white w-[6vh] px-1 cursor-pointer my-1"
        >
          Close
        </div>
        
        <h2 className="text-2xl font-semibold text-gray-800 mb-6">Edit User Details</h2>

        <form onSubmit={handleSubmit} className="space-y-4 ">
          <div>
            <label className="block text-sm font-medium text-gray-700">First Name</label>
            <input
              type="text"
              name="firstName"
              value={formData.firstName}
              onChange={handleChange}
              placeholder="Enter your first name"
              className="mt-1 block w-full px-4 py-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-2 focus:ring-indigo-500 focus:border-indigo-500"
            />
          </div>

          <div>
            <label className="block text-sm font-medium text-gray-700">Last Name</label>
            <input
              type="text"
              name="lastName"
              value={formData.lastName}
              onChange={handleChange}
              placeholder="Enter your last name"
              className="mt-1 block w-full px-4 py-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-2 focus:ring-indigo-500 focus:border-indigo-500"
            />
          </div>

          <div>
            <label className="block text-sm font-medium text-gray-700">Email Address</label>
            <input
              type="email"
              name="email"
              value={formData.email}
              onChange={handleChange}
              placeholder="Enter your email"
              className="mt-1 block w-full px-4 py-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-2 focus:ring-indigo-500 focus:border-indigo-500"
            />
          </div>

          <div>
            <label className="block text-sm font-medium text-gray-700">Phone Number</label>
            <input
              type="tel"
              name="phone"
              value={formData.phone}
              onChange={handleChange}
              placeholder="Enter your phone number"
              className="mt-1 block w-full px-4 py-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-2 focus:ring-indigo-500 focus:border-indigo-500"
      
            />
          </div>

          <div>
            <button
              type="submit"
              className="w-full bg-indigo-500 text-white py-2 px-4 rounded-md hover:bg-indigo-600 focus:outline-none focus:ring-2 focus:ring-indigo-500 focus:ring-opacity-50"
            >
              Save Changes
            </button>
          </div>
        </form>
      </div>
    </div>
  );
};

export default EditUserForm;
