import axios from 'axios';
import React, { useState } from 'react'
import { getCookie } from '../Handlers/RouteHandlers';
import { useSelector } from 'react-redux';

const AddAddressForm = ({setAddressActive}) => {
    const user = useSelector(store=>store.user.User);
    console.log(getCookie("token"))
    const [formData, setFormData] = useState({
        userId: user.userId,  
        street: '',
        city: '',
        state: '',
        zipCode: '',
      });
    
      // Handle form input changes
      const handleChange = (e) => {
        const { name, value } = e.target;
        setFormData({
          ...formData,
          [name]: value,
        });
      };
    
      // Handle form submission
      const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            // /updateUser/{userId}
            const response = await axios.post(`http://localhost:8080/user/addAddress`, {
                headers: {
                    Authorization: `Bearer ${getCookie("token")}`, 
                },
              });
              console.log(response.data)
        } catch (error) {
            console.log(error)
        }
      };
    
      return (
        <form onSubmit ={handleSubmit} className="space-y-4 p-4 bg-white shadow-md rounded-md">
        <button className="p-1 bg-red-600 text-white" onClick={()=>setAddressActive(false)}>close</button>    
          {/* Street Field */}
          <div>
            <label htmlFor="street" className="block text-gray-700">Street:</label>
            <input
              type="text"
              id="street"
              name="street"
              value={formData.street}
              onChange={handleChange}
              className="w-full px-3 py-2 border rounded-md"
            />
          </div>
    
          {/* City Field */}
          <div>
            <label htmlFor="city" className="block text-gray-700">City:</label>
            <input
              type="text"
              id="city"
              name="city"
              value={formData.city}
              onChange={handleChange}
              className="w-full px-3 py-2 border rounded-md"
            />
          </div>
    
          {/* State Field */}
          <div>
            <label htmlFor="state" className="block text-gray-700">State:</label>
            <input
              type="text"
              id="state"
              name="state"
              value={formData.state}
              onChange={handleChange}
              className="w-full px-3 py-2 border rounded-md"
            />
          </div>
    
          {/* Zip Code Field */}
          <div>
            <label htmlFor="zipCode" className="block text-gray-700">Zip Code:</label>
            <input
              type="text"
              id="zipCode"
              name="zipCode"
              value={formData.zipCode}
              onChange={handleChange}
              className="w-full px-3 py-2 border rounded-md"
            />
          </div>
    
          {/* Submit Button */}
          <button type="submit" className="w-full bg-blue-500 text-white py-2 rounded-md mt-4">
            Submit
          </button>
        </form>
      );
}

export default AddAddressForm