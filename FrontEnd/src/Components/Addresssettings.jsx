import axios from 'axios';
import React, { useEffect, useState, useCallback } from 'react';
import { getCookie } from '../Handlers/RouteHandlers';
import { useSelector } from 'react-redux';

// Address Item Component
const AddressItem = React.memo(({ address, onEdit, onDelete }) => (
  <div className="flex flex-col bg-gray-200 rounded-md p-2">
    <div className="flex">
      <span>{address.street}</span>
      <span>, {address.state}</span>
      <span>, {address.city}</span>
      <span>, {address.zipCode}</span>
    </div>
    <div className="flex flex-row gap-2 mt-2">
      <span onClick={onEdit} className="cursor-pointer">Edit</span>
      <button onClick={onDelete} className="">Delete</button>
    </div>
  </div>
));

// Address Form Component
const AddressForm = ({ addressData, handleAddressChange, onSubmit, closeForm }) => (
  <div className="flex w-full items-center justify-center bg-gray-100 bg-opacity-30 backdrop-blur-sm absolute">
    <form onSubmit={onSubmit} className="w-[90%] sm:w-[50%] md:w-[30%] bg-white rounded-lg shadow-2xl p-6 flex flex-col gap-6 relative">
      {/* Close Button */}
      <button onClick={closeForm} type="button" className="absolute top-4 right-4 text-gray-500 hover:text-gray-700">
        ✕
      </button>
      {/* Form Title */}
      <h2 className="text-2xl font-semibold text-gray-700 text-center">
        {addressData==="Edit Address" ? "Edit Address" : "Add New Address"}
      </h2>
      {/* Address Fields */}
      {['street', 'state', 'city', 'zipCode'].map((field) => (
        <div key={field} className="flex flex-col gap-2">
          <label className="text-sm font-medium text-gray-600 capitalize">{field}</label>
          <input
            type="text"
            id={field}
            className="border border-gray-300 focus:border-blue-500 focus:ring-2 focus:ring-blue-500 rounded-md p-2 text-gray-700 shadow-sm"
            placeholder={`Enter your ${field}`}
            value={addressData[field]}
            onChange={handleAddressChange}
          />
        </div>
      ))}
      {/* Submit Button */}
      <button type="submit" className="w-full py-2 bg-blue-500 text-white font-medium rounded-md shadow-md hover:bg-blue-600">
        {addressData==="Edit Address" ? "Update Address" : "Save Address"}
      </button>
    </form>
  </div>
);

// Main Address Settings Component
const AddressSettings = ({editAddressActive, setEditAddressActive }) => {
  const user = useSelector((state) => state.user.User);
  const [addresses, setAddresses] = useState([]);
  const [newAddressActive, setNewAddressActive] = useState(false);
  const [editActive,setEditActive] = useState(false)
  const [newAddress, setNewAddress] = useState({ userId: user.userId, street: "", state: "", city: "", zipCode: "" });
  const [editingAddressId, setEditingAddressId] = useState(null);

  const handleNewAddress = (e) => {
    const { id, value } = e.target;
    setNewAddress((prev) => ({ ...prev, [id]: value }));
  };

  const fetchAddresses = useCallback(async () => {
    try {
      const response = await axios.get(`http://localhost:8080/user/userAddresses/${user.userId}`, {
        headers: { Authorization: `Bearer ${getCookie('token')}` }
      });
      setAddresses(response.data);
    } catch (error) {
      console.error("Error fetching addresses:", error);
    }
  }, [user.userId]);

  const handleAddAddress = useCallback(async (e) => {
    e.preventDefault();
    try {
      const response = await axios.post(`http://localhost:8080/user/addAddress`, newAddress, {
        headers: { Authorization: `Bearer ${getCookie('token')}` }
      });
      if (response.status === 200) {
        alert(response.data);
        setAddresses((prev) => [...prev, newAddress]);
        setNewAddressActive(false);
      }
    } catch (error) {
      console.error("Error adding address:", error);
    }
  }, [newAddress]);

  const handleDeleteAddress = useCallback(async (addressId) => {
    try {
      const response = await axios.delete(`http://localhost:8080/user/deleteAddress/${addressId}`, {
        headers: { Authorization: `Bearer ${getCookie('token')}` }
      });
      if (response.status === 200) {
        setAddresses((prevAddresses) => 
          prevAddresses.filter((address) => address.addressId !== addressId)
        );
      }
    } catch (error) {
      console.error("Error deleting address:", error);
    }
  }, []);

  const handleEditAddress = (address) => {
    setEditingAddressId(address.addressId);
    setNewAddress(address); 
    setEditActive(true);
  };

  const handleUpdateAddress = useCallback(async (e) => {
    e.preventDefault();
    try {
      const response = await axios.put(`http://localhost:8080/user/updateAddress/${editingAddressId}`, newAddress, {
        headers: { Authorization: `Bearer ${getCookie('token')}` }
      });
      if (response.status === 200) {
        alert(response.data);
        setAddresses((prev) => prev.map(address => 
          address.addressId === editingAddressId ? newAddress : address
        ));
        setEditActive(false);
      }
    } catch (error) {
      console.error("Error updating address:", error);
    }
  }, [newAddress, editingAddressId]);

  useEffect(() => {
    fetchAddresses();
  }, [fetchAddresses]);

  return (
    <div className="relative flex min-h-screen bg-gray-100 bg-opacity-30 backdrop-blur-sm">
      {newAddressActive && (
        <AddressForm
          addressData={"Add New Address"}
          handleAddressChange={handleNewAddress}
          onSubmit={handleAddAddress}
          closeForm={() => setNewAddressActive(false)}
        />
      )}
      {editActive && (
        <AddressForm
          addressData={"Edit Address"}
          handleAddressChange={handleNewAddress}
          onSubmit={handleUpdateAddress}
          closeForm={() => setEditActive(false)}
        />
      )}

      <div className="bg-white shadow-2xl w-[70%] h-[60vh] p-4 flex flex-col mx-[15%] gap-4 rounded-md">
        <span className="w-full flex items-center justify-center text-xl font-semibold">Your Address</span>
        <button
          onClick={() => setEditAddressActive(false)}
          className="absolute top-4 right-[18%] text-gray-500 hover:text-gray-700"
        >
          ✕
        </button>
        <button className="bg-blue-800 text-white mx-4 w-[15vh] p-1 rounded-md" onClick={() => setNewAddressActive(true)}>
          Add Address
        </button>
        <div className="p-4 flex flex-col gap-2">
          {addresses.map((address, index) => (
            <AddressItem
              key={index}
              address={address}
              onEdit={() => handleEditAddress(address)} // Set up edit functionality
              onDelete={() => handleDeleteAddress(address.addressId)}
            />
          ))}
        </div>
      </div>
    </div>
  );
};

export default AddressSettings;
