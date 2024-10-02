import React, { useEffect, useState } from 'react'
import Slider from '../Components/Slider'
import NavigationBar from '../Components/NavigationBar'
import ProductCarousel from '../Components/ProductCarousel'
import ProductList from '../Components/ProductList'
import Category from '../Components/Category'
import Footer from '../Components/Footer'
import axios from 'axios'
import { useDispatch, useSelector } from 'react-redux'
import { getCookie } from '../Handlers/RouteHandlers'
import { Navigate, useNavigate } from 'react-router-dom'
import { loadCart } from '../Redux/CartSlice'

const HomePage = () => {
  const user = useSelector(state=>state.user.User)
  const navigate = useNavigate();
  const [newProducts, setNewProducts] = useState([])
  const [featuredProduct, setFeaturedProduct] = useState([])
  const dispatch = useDispatch()
  useEffect(() => {
    const fetchData = async () => {
      try {
        const newProductResponse = await axios.get("http://localhost:8080/seller/product/check?dateSort=desc", {
          headers: {
            Authorization: `Bearer ${getCookie("token")}`, 
          },
        });
        setNewProducts(newProductResponse.data);
        const featuredProductResponse = await axios.get("http://localhost:8080/seller/product/check?dateSort=asc", {
          headers: {
            Authorization: `Bearer ${getCookie("token")}`, 
          },
        });
        setFeaturedProduct(featuredProductResponse.data)


      } catch (error) {
        alert("Token Expired, Login again")
        navigate("/")
      }
    };
  
    fetchData();
  }, []);
  // console.log(newProducts)
  useEffect(() => {
    const fetchData = async () => {
      try {
        const response = await axios.get(`http://localhost:8080/cart/${user.userId}`, {
          headers: {
            Authorization: `Bearer ${getCookie('token')}`,
          },
        });
        dispatch(loadCart(response.data));
      } catch (error) {
        console.error(error);
      }
    };
    fetchData();
  }, []);
  return (
    <div className=''>
        <NavigationBar/>
        <div className="">
          <Slider/>
        </div>
        <div className=" mt-24 px-4 md:px-8 lg:px-16 xl:px-32 2xl:px-64">
          <h1 className="text-2xl ">Featured Products</h1>
            <ProductCarousel products={newProducts}/>
        </div>
        <div className="mt-24">
          <h1 className="text-2xl px-4 md:px-8 lg:px-16 xl:px-32 2xl:px-64 mb-8">Categories</h1>
          <Category/>
        </div>
        <div className="mt-24 px-4 md:px-8 lg:px-16 xl:px-32 2xl:px-64">
          <h1 className="text-2xl">New Products</h1>
          <ProductCarousel products={featuredProduct}/>
        </div>
        <Footer/>

    </div>
  )
}

export default HomePage