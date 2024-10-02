import axios from 'axios';
import React, { useEffect, useState } from 'react';
import { getCookie } from '../Handlers/RouteHandlers';
import { useDispatch, useSelector } from 'react-redux';
import { addItem } from '../Redux/CartSlice';

const Add = ({ variantId, stockNumber }) => {
  const user = useSelector((state) => state.user.User);
  const dispatch = useDispatch();
  const [quantity, setQuantity] = useState(1);
  const [formData, setFormData] = useState({
    userId: user.userId,
    productVariantId: variantId,
    productCount: quantity,
  });

  useEffect(() => {
    setFormData((prevData) => ({
      ...prevData,
      productCount: quantity, // Ensure to update productCount here
    }));
  }, [quantity]);

  const handleQuantity = (type) => {
    if (type === "d" && quantity > 1) {
      setQuantity((prev) => prev - 1);
    }
    if (type === "i" && quantity < stockNumber) {
      setQuantity((prev) => prev + 1);
    }
  };

  const addItemToCart = async () => { // Renamed for clarity
    console.log(`Added to cart: Variant ID - ${variantId}, Quantity - ${quantity}`);
    try {
      const res = await axios.post("http://localhost:8080/cart/addToCart", formData, {
        headers: {
          Authorization: `Bearer ${getCookie("token")}`,
        },
      });
      console.log(res);
      if (res.status === 200) {
        dispatch(addItem()); // Dispatch addItem to increment count
      }
    } catch (error) {
      console.log(error);
    }
  };

  const isLoading = false; // Simulated loading state

  return (
    <div className="flex flex-col gap-4">
      <h4 className="font-medium">Choose Quantity</h4>
      <div className="flex justify-between">
        <div className="flex items-center gap-4">
          <div className="bg-lite py-2 px-4 rounded-3xl flex items-center justify-between w-32">
            <button
              className="cursor-pointer text-xl disabled:cursor-not-allowed disabled:opacity-20"
              onClick={() => handleQuantity("d")}
              disabled={quantity === 1}
            >
              -
            </button>
            {quantity}
            <button
              className="cursor-pointer text-xl disabled:cursor-not-allowed disabled:opacity-20"
              onClick={() => handleQuantity("i")}
              disabled={quantity === stockNumber}
            >
              +
            </button>
          </div>
          {stockNumber < 1 ? (
            <div className="text-xs">Product is out of stock</div>
          ) : (
            <div className="text-xs">
              Only <span className="text-orange-500">{stockNumber} items</span> left!
              <br /> Don't miss it
            </div>
          )}
        </div>
        <button
          onClick={addItemToCart} // Call the renamed function
          disabled={isLoading}
          className="w-36 text-sm rounded-3xl bg-lite text-contrast py-2 px-4 hover:bg-contrast hover:text-white disabled:cursor-not-allowed disabled:bg-pink-200 disabled:ring-0 disabled:text-white disabled:ring-none"
        >
          Add to Cart
        </button>
      </div>
    </div>
  );
};

export default Add;
