import React, { useState } from 'react';
import axios from 'axios';
import { getCookie } from '../Handlers/RouteHandlers';

const AddProductsForm = ({ setAddProductViewActive }) => {
  // State for form inputs
  const [formData, setFormData] = useState({
    productTitle: '',
    productDescription: '',
    price: '',
    discount: '',
    size: '',
    stockCount: '',
    categories: [], // Initialize as an empty array
    imageUrls:["https://a.media-amazon.com/images/I/51yvxMGTn7L._SX679_.jpg", "https://a.media-amazon.com/images/I/51pi9-ZddJL._SY606_.jpg", "	https://a.media-amazon.com/images/I/51pi9-ZddJL._SY606_.jpg"],
    sellerId: "be1337d8-7cbf-45ac-90cc-49a1276d9d53"
  });

  // Handle input change
  const handleChange = (e) => {
    const { id, value } = e.target;
    setFormData((prevData) => ({
      ...prevData,
      [id]: value,
    }));
  };

  // Handle category input
  const handleCategoryChange = (e) => {
    const { value } = e.target;
    setFormData((prevData) => ({
      ...prevData,
      categories: value.split(',').map((cat) => cat.trim().toUpperCase()), // Split by comma, trim spaces, and convert to uppercase
    }));
  };

  // Handle form submission
  const handleSubmit = async (e) => {
    e.preventDefault();
    console.log('Form Data:', JSON.stringify(formData, null, 2));
    try {
      await axios.post("http://localhost:8080/seller/product/createProduct", formData, {
        headers: {
          Authorization: `Bearer ${getCookie("token")}`          
        },
      });
      // Optionally reset formData or handle success
    } catch (error) {
      console.error(error);
    }
  };

  return (
    <div className="flex flex-col w-[100%] items-center justify-center p-4 gap-4">
      <form
        className="bg-white p-6 rounded-lg shadow-md w-full max-w-lg space-y-4"
        onSubmit={handleSubmit}
      >
        <button
          type="button"
          className="bg-red-600 w-[10%] p-1 text-white font-medium rounded-sm"
          onClick={() => setAddProductViewActive(false)} // Corrected handler
        >
          Close
        </button>
        <h2 className="text-2xl font-bold text-gray-800 mb-4">Add New Product</h2>

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
            placeholder="Enter product title"
          />
        </div>

        <div className="flex flex-col w-full gap-2">
          <label htmlFor="productDescription" className="font-semibold text-gray-700">
            Product Description
          </label>
          <textarea
            id="productDescription"
            name="productDescription"
            rows="4"
            value={formData.productDescription}
            onChange={handleChange}
            className="p-2 border border-gray-300 rounded focus:outline-none focus:ring-2 focus:ring-green-400"
            placeholder="Enter product description..."
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
          <label htmlFor="categories" className="font-semibold text-gray-700">
            Categories
          </label>
          <input
            type="text"
            id="categories"
            value={formData.categories.join(', ')} // Display categories as a comma-separated string
            onChange={handleCategoryChange}
            className="p-2 border border-gray-300 rounded focus:outline-none focus:ring-2 focus:ring-green-400"
            placeholder="Enter product categories separated by commas"
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
            type="text"
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
          Add Product
        </button>
      </form>
    </div>
  );
};

export default AddProductsForm;
