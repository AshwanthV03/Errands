import React, { useEffect, useState } from 'react';
import Filter from '../Components/Filter';
import ProductList from '../Components/ProductList';
import NavigationBar from '../Components/NavigationBar';
import Footer from '../Components/Footer';
import { getCookie } from '../Handlers/RouteHandlers';
import axios from 'axios';
import { useSearchParams } from 'react-router-dom';
import { Pagination } from '@mui/material';

const ProductsPage = () => {
  const [products, setProducts] = useState([]);
  const [searchParams] = useSearchParams();
  
  const [filterData, setFilterData] = useState({
    sort: null,
    minPrice: null,
    maxPrice: null,
    selectedColors: [],
  });

  const [currentPage, setCurrentPage] = useState(1);
  const productsPerPage = 12;

  const handleFilterData = (newFilterData) => {
    setFilterData((prev) => ({ ...prev, ...newFilterData }));
  };

  const buildApiUrl = () => {
    let url = `http://localhost:8080/seller/product/search?`;

    const search = searchParams.get("search");
    const category = searchParams.get("category");

    if (search) url += `search=${encodeURIComponent(search)}&`;
    if (category) url += `category=${encodeURIComponent(category)}&`;

    if (filterData.minPrice != null) url += `min=${filterData.minPrice}&`;
    if (filterData.maxPrice != null) url += `max=${filterData.maxPrice}&`;
    if (filterData.sort) url += `sort=${filterData.sort}&`;
    if (filterData.selectedColors.length) {
      url += `colours=${filterData.selectedColors.join(',')}&`;
    }

    return url.endsWith('&') || url.endsWith('?') ? url.slice(0, -1) : url;
  };

  const fetchProducts = async () => {
    const url = buildApiUrl();  
    try {
      const response = await axios.get(url, {
        headers: {
          Authorization: `Bearer ${getCookie('token')}`,
        },
      });
      setProducts(response.data);
      setCurrentPage(1);
    } catch (error) {
      console.error('Error fetching data', error);
    }
  };

  useEffect(() => {
    fetchProducts();
  }, [searchParams, filterData]); 

  const indexOfLastProduct = currentPage * productsPerPage;
  const currentProducts = products.slice(indexOfLastProduct - productsPerPage, indexOfLastProduct);
  const totalPages = Math.ceil(products.length / productsPerPage);

  return (
    <>
      <NavigationBar />
      <div className='px-4 md:px-8 lg:px-16 xl:px-32 2xl:px-64 relative'>
        <div className="hidden bg-pink-50 px-4 sm:flex justify-between h-64">

          <div className="w-2/3 flex flex-col items-center justify-center gap-8">
            <h1 className="text-4xl font-semibold leading-[48px] text-gray-700">
              Grab up to 50% off on
              <br /> Selected Products
            </h1>
            <button className="rounded-3xl bg-red-500 text-white w-max py-3 px-5 text-sm">
              Buy Now
            </button>
          </div>

          <div className="relative w-1/3">
            <img src="/woman.png" alt="Promotional Image" className="object-contain w-full h-full" />
          </div>
        </div>
        <span className="">More Filters</span>
        <Filter handleFilterClick={fetchProducts} handleFilterData={handleFilterData} filterData={filterData} />

        <div>
          <h2 className="font-medium text-lg mt-6 mb-6">Your searched Products</h2>
          {currentProducts.length > 0 ? (
            <div className='w-[100%]'>
              <ProductList products={currentProducts} />
              <div className="flex items-center justify-center mt-4">
                <Pagination
                  count={totalPages}
                  page={currentPage}
                  onChange={(event, value) => setCurrentPage(value)}
                  color="primary"
                />
              </div>
            </div >
          ) : <p>No products found</p>}
        </div>
      </div>
      <Footer />
    </>
  );
};

export default ProductsPage;
