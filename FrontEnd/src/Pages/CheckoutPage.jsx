import React, { useEffect, useState } from 'react';
import AddIcon from '@mui/icons-material/Add';
import axios from 'axios';
import { getCookie } from '../Handlers/RouteHandlers';
import { useSelector } from 'react-redux';
import AddAddressForm from '../Components/AddAddressForm';

const CheckoutPage = () => {
  const [address, setAddress] = useState([]);
  const [addressActive, setAddressActive] = useState(false);
  const [selectedAddress, setSelectedAddress] = useState(null); // Fixed typo: Corrected to setSelectedAddress

  console.log(selectedAddress)
  // Get user from Redux store
  const user = useSelector((state) => state.user.User);

  const placeOrder = async () => {
    try {
      const response = await axios.post(
        `http://localhost:8080/order/placeOrders/${user.userId}/${selectedAddress.addressId}`,
        {
          headers: {
            Authorization: `Bearer ${getCookie('token')}`,
          },
        }
      );
    } catch (error) {
      console.error(error);
    }
  };

  useEffect(() => {
    const fetchData = async () => {
      try {
        const response = await axios.get(
          `http://localhost:8080/user/userAddresses/${user.userId}`,
          {
            headers: {
              Authorization: `Bearer ${getCookie('token')}`,
            },
          }
        );
        setAddress(response.data);
      } catch (error) {
        console.error(error);
      }
    };

      fetchData();
  }, [user]);

  // Update selected address when address array changes
  useEffect(() => {
    if (address.length > 0) {
      setSelectedAddress(address[0]); // Set the first address by default
    }
  }, [address]); // Dependency added to run useEffect when 'address' changes

  return (
    <div className="flex w-[70%] mx-auto relative">
      {/* Address selector */}
      <div className="flex flex-col w-[70%]">
        <span className="text-2xl font-medium">Select Address</span>
        <div className="p-4 flex flex-col mt-4 border-2 rounded-md border-gray-200">
          <span className="border-b-2">Your Addresses</span>

          {address.length >-1? address.map((item, index) => (
            <div
              key={index}
              onClick={() => setSelectedAddress(item)} // Fixed typo: Corrected to setSelectedAddress
              className={`bg-pink-50 rounded-md p-2 my-2 ${selectedAddress && selectedAddress.addressId === item.addressId ? 'border border-blue-500' : ''}`} // Highlight selected address
            >
              <span className="text-lg font-bold">{user.userName || 'N/A'}</span>
              <span className="text">{`${item.street}, ${item.city}, ${item.state}, ${item.zipCode}`}</span>
              <div className="text-sm cursor-pointer hover:text-blue-600">Edit address</div>
            </div>
          )):<></>}

          <div className="flex gap-2 mt-2 items-center cursor-pointer">
            <AddIcon />
            <span className="hover:text-pink-600" onClick={() => { setAddressActive(true); console.log(addressActive); }}>
              Add Address
            </span>
          </div>
        </div>
      </div>

      {/* Payment */}
      <div className="bg-gray-100 rounded-md flex flex-col p-3 gap-2 w-[30%] ml-[5%]">
        <span className="text-xl font-bold text-gray-700 mx-[10%]">Order Summary</span>
        <span className="text-lg font-medium text-gray-700 mx-[10%]">{`GST (16%): ₹49`}</span>
        <span className="text-lg font-medium text-gray-700 mx-[10%]">{`Service Tax (5%): ₹29`}</span>
        <span className="text-lg font-medium text-gray-700 mx-[10%]">{`Handling Fee: ₹10`}</span>
        <span className="text-lg font-medium text-gray-700 mx-[10%]">{`Delivery Fee: ₹50`}</span>
        <button
          onClick={() => placeOrder()}
          className="w-[80%] p-1 mx-[10%] bg-pink-600 text-white rounded-md hover:bg-white hover:text-pink-600"
        >
          Place Order
        </button>
      </div>

      {/* Add Address Form Modal */}
      {addressActive && (
        <div className="fixed inset-0 flex justify-center items-center bg-black bg-opacity-50 z-50">
          <div className="bg-white p-6 rounded-md shadow-lg relative z-60">
            <AddAddressForm setAddressActive={setAddressActive} />
          </div>
        </div>
      )}
    </div>
  );
};

export default CheckoutPage;
