import React, { useState } from 'react';
import axios from 'axios';
import { getCookie } from '../Handlers/RouteHandlers';

const AddProductVariantForm = ({ prop, setAddProductVariantViewActive }) => {
  const { productId, productTitle, productDescription } = prop; // Destructure prop for easier access

  const [formData, setFormData] = useState({
    productId: productId,
    price: 0,
    discount: '',
    size: '',
    colour: 'BLACK',
    stockCount: '',
    images:["https://a.media-amazon.com/images/I/51iK4MhVMwL._SY606_.jpg","https://a.media-amazon.com/images/I/51uC25HEopL._SY606_.jpg"],
    productTitle: productTitle, // Include title and description in formData if needed
    productDescription: productDescription,
  });

  // Handle input change
  const handleChange = (e) => {
    const { id, value } = e.target;
    setFormData((prevData) => ({
      ...prevData,
      [id]: id === 'size' ? value.toUpperCase() : value,
    }));
  };

  // Handle form submission
  const handleSubmit = async (e) => {
    e.preventDefault();
    console.log('Form Data:', JSON.stringify(formData, null, 2));

    try {
      const res = await axios.post(
        'http://localhost:8080/seller/product/seller/addProductVariant',
        formData,
        {
          headers: {
            Authorization: `Bearer ${getCookie("token")}`          
          },
        }
      );
      console.log(res);
    } catch (error) {
      console.log(error);
    }
  };

  return (
    <div className="flex flex-col w-[100%] items-center justify-center p-4 gap-4">
      <form
        className="bg-white p-6 rounded-lg shadow-xl w-full max-w-lg space-y-4"
        onSubmit={handleSubmit}
      >
        <button
          type="button"
          className="bg-red-600 w-[15%] p-1 text-white font-medium rounded-sm"
          onClick={() => setAddProductVariantViewActive(false)} // Corrected handler
        >
          Close
        </button>
        <h2 className="text-2xl font-bold text-gray-800 mb-4">Add New Product Variant</h2>

        <div className="flex flex-col w-full gap-2">
          <label htmlFor="productTitle" className="font-semibold text-gray-700">
            Product Title
          </label>
          <input
            type="text"
            id="productTitle"
            value={formData.productTitle}
            onChange={handleChange}
            className="p-2 border border-gray-300 rounded focus:outline-none focus:ring-2 focus:ring-green-400"
            disabled
          />
        </div>

        <div className="flex flex-col w-full gap-2">
          <label htmlFor="productDescription" className="font-semibold text-gray-700">
            Product Description
          </label>
          <textarea
            id="productDescription"
            rows="4"
            value={formData.productDescription}
            onChange={handleChange}
            className="p-2 border border-gray-300 rounded focus:outline-none focus:ring-2 focus:ring-green-400"
            disabled
          ></textarea>
        </div>

        <div className="flex flex-col w-full gap-2">
          <label htmlFor="price" className="font-semibold text-gray-700">
            Price
          </label>
          <input
            type="number"
            id="price"
            value={formData.price}
            onChange={handleChange}
            className="p-2 border border-gray-300 rounded focus:outline-none focus:ring-2 focus:ring-green-400"
            placeholder="Enter product price"
          />
        </div>

        <div className="flex flex-col w-full gap-2">
          <label htmlFor="discount" className="font-semibold text-gray-700">
            Discount
          </label>
          <input
            type="number"
            id="discount"
            value={formData.discount}
            onChange={handleChange}
            className="p-2 border border-gray-300 rounded focus:outline-none focus:ring-2 focus:ring-green-400"
            placeholder="Enter discount"
          />
        </div>

        <div className="flex flex-col w-full gap-2">
          <label htmlFor="size" className="font-semibold text-gray-700">
            Size
          </label>
          <input
            type="text"
            id="size"
            value={formData.size}
            onChange={handleChange}
            className="p-2 border border-gray-300 rounded focus:outline-none focus:ring-2 focus:ring-green-400"
            placeholder="Enter product size"
          />
        </div>

        <div className="flex flex-col w-full gap-2">
          <label htmlFor="stockCount" className="font-semibold text-gray-700">
            No of Stocks
          </label>
          <input
            type="number"
            id="stockCount"
            value={formData.stockCount}
            onChange={handleChange}
            className="p-2 border border-gray-300 rounded focus:outline-none focus:ring-2 focus:ring-green-400"
            placeholder="Enter number of stocks"
          />
        </div>

        <button
          type="submit"
          className="w-full py-2 bg-green-500 text-white rounded hover:bg-green-600 transition duration-200 font-semibold"
        >
          Add Product Variant
        </button>
      </form>
    </div>
  );
};

export default AddProductVariantForm;
