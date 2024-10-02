import React, { useEffect, useState, useMemo } from 'react';
import NavigationBar from '../Components/NavigationBar';
import Footer from '../Components/Footer';

import AddIcon from '@mui/icons-material/Add';
import RemoveIcon from '@mui/icons-material/Remove';
import FavoriteIcon from '@mui/icons-material/Favorite';
import DeleteIcon from '@mui/icons-material/Delete';
import CloseIcon from '@mui/icons-material/Close'; // New close icon

import axios from 'axios';
import { getCookie } from '../Handlers/RouteHandlers';
import { useNavigate } from 'react-router-dom';
import { useSelector, useDispatch } from 'react-redux'; // Added useDispatch
import { loadCart, removeItem } from '../Redux/CartSlice';

const CartPage = () => {
  const cart = useSelector((state)=>state.cart.CartItems)
  const [cartItems, setCartItems] = useState([...cart]);
  const [selectedAddress, setSelectedAddress] = useState(null);
  const [address, setAddress] = useState([]);
  const [active, setActive] = useState(false); // For address popup

  const navigate = useNavigate();
  const user = useSelector((state) => state.user.User);
  const dispatch = useDispatch(); // Added useDispatch for Redux actions
  console.log(user)
  // Increment function
  const inc = (index) => {
    setCartItems((prevItems) =>
      prevItems.map((item, i) =>
        i === index ? { ...item, quantity: item.quantity + 1 } : item
      )
    );
  };

  // Decrement function
  const dec = (index) => {
    setCartItems((prevItems) =>
      prevItems.map((item, i) =>
        i === index
          ? { ...item, quantity: item.quantity > 1 ? item.quantity - 1 : 1 }
          : item
      )
    );
  };

  // Handle Delete Cart Item
  const handleDelete = async (e, itemId) => {
    e.preventDefault();
    try {
      const response = await axios.delete(`http://localhost:8080/cart/${itemId}`, {
        headers: {
          Authorization: `Bearer ${getCookie('token')}`,
        },
      });
      if (response.status === 200) {
        dispatch(removeItem({id:itemId})); // Dispatching removeItem action
      }
    } catch (error) {
      console.error(error);
    }
  };

  // Checkout Function
  const handleCheckout = () => {
    navigate(`/checkout/${user.userId}`);
  };

  // Place Order
  const placeOrder = async () => {
    try {
      await axios.post(
        `http://localhost:8080/order/placeOrders/${user.userId}/`,
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

  // Optimized subtotal calculation
  const subtotal = useMemo(() => {
    return cartItems.reduce((total, item) => total + item.quantity * item.price, 0);
  }, [cartItems]);

  // Dynamic addon charges
  const gst = subtotal * 0.18;
  const sgst = subtotal * 0.05;
  const handlingCharges = 40;
  const deliveryCharges = 40;
  const total = subtotal + gst + sgst + handlingCharges + deliveryCharges;
  const placeOrders = async () => {
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

  const handlePayment = async () => {
    try {
      const response = await axios.post('http://localhost:8080/payment/create', null, {
        headers: {
          Authorization: `Bearer ${getCookie('token')}`,
        },
      });
      if (response.status === 200 && response.data) {
        const redirectUrl = response.data;
        window.location.href = redirectUrl; // Redirect to PayPal for payment
      } else {
        console.error('Payment creation failed', response.data);
      }
    } catch (error) {
      console.error('Payment error:', error);
    }
  };


  useEffect(() => {
    const fetchData = async () => {
      try {
        const response = await axios.get(`http://localhost:8080/cart/${user.userId}`, {
          headers: {
            Authorization: `Bearer ${getCookie('token')}`,
          },
        });
        setCartItems(response.data);
        dispatch(loadCart(response.data));
      } catch (error) {
        console.error(error);
      }
    };
    fetchData();
  }, [user.userId, dispatch]);

  useEffect(() => {
    const fetchAddressData = async () => {
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
    fetchAddressData();
  }, [user]);

  useEffect(() => {
    if (address.length > 0) {
      setSelectedAddress(address[0]); 
    }
  }, [address]);
  useEffect(() => {
    setCartItems([...cart]);
  }, [cart]);
  
  return (
    <>
      <NavigationBar />

      <div className="w-[80%] mx-auto min-h-screen py-8">
        <h2 className="text-3xl font-semibold mb-8">My Cart</h2>

        <div className="flex flex-row-reverse justify-between gap-8">
          {/* Subtotal and Address Section */}
          <div className="w-[27%] bg-gray-100 p-6 rounded-md shadow-lg">
            <div className="text-lg font-medium text-gray-800 mb-4">
              <span>{`Subtotal (${cartItems.length} items): `}</span>
              <span className="text-xl">{`Rs. ${subtotal.toFixed(2)}`}</span>
            </div>

            <div className="flex flex-col text-lg gap-2">
              <span>GST Charges (18%): Rs. {gst.toFixed(2)}</span>
              <span>SGST Charges (5%): Rs. {sgst.toFixed(2)}</span>
              <span>Handling Charges: Rs. {handlingCharges}</span>
              <span>Delivery cost: Rs. {deliveryCharges}</span>
            </div>

            {/* Select and Show Delivery Address */}
            <div className="mt-6">
              <div className="text-md font-medium cursor-pointer text-blue-600 mb-2" onClick={() => setActive(true)}>
                Select other address
              </div>
              <div>
                <span className="font-medium">Delivery Address:</span>
                {selectedAddress && (
                  <div className=" p-2 mt-2 rounded-md">
                    <span>{`${selectedAddress.street}, ${selectedAddress.city}, ${selectedAddress.state}, ${selectedAddress.zipCode}`}</span>
                    <div className="text-sm cursor-pointer hover:text-blue-600 mt-1">Edit address</div>
                  </div>
                )}
              </div>
            </div>

            {/* Pay Button */}
            <button
              className="w-full mt-6 bg-blue-500 text-white font-semibold py-2 rounded-md hover:bg-blue-600 transition duration-200"
              onClick={placeOrders}
            >
              Pay Rs. {total.toFixed(2)}
            </button>
          </div>

          {/* Cart Items Table */}
          {cartItems.length > 0 && (
            <div className="w-[70%] bg-gray-100 p-4 rounded-md shadow-md overflow-x-auto">
              <table className="w-full text-left">
                <tbody>
                  {cartItems.map((item, index) => (
                    <tr key={item.cartItem} className="hover:bg-gray-200 transition ease-in border-b">
                      <td className="text-center">
                        <img
                          src={item.image.length > 0 ? item.image[0].imageUrl : '/woman.png'}
                          alt={item.title}
                          className="h-24 w-24 object-contain mx-auto"
                        />
                      </td>
                      <td className="px-4 py-2">{item.title}</td>
                      <td className="text-center">
                        <button className="p-1 bg-gray-300 hover:bg-gray-400 rounded-md" onClick={() => dec(index)}>
                          <RemoveIcon />
                        </button>
                        <span className="px-4">{item.quantity}</span>
                        <button className="p-1 bg-gray-300 hover:bg-gray-400 rounded-md" onClick={() => inc(index)}>
                          <AddIcon />
                        </button>
                      </td>
                      <td className="px-4 py-2">{`Rs. ${item.price.toFixed(2)}`}</td>
                      <td className="px-4 py-2">{`Rs. ${(item.price * item.quantity).toFixed(2)}`}</td>
                      <td>
                        <button className="text-gray-600 hover:text-gray-800" onClick={(e) => handleDelete(e, item.cartItem)}>
                          <DeleteIcon />
                        </button>
                      </td>
                    </tr>
                  ))}
                </tbody>
              </table>
            </div>
          )}
        </div>
      </div>

      <Footer />
    </>
  );
};

export default CartPage;
