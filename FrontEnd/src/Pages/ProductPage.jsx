import React, { useEffect, useState } from 'react';
import ProductImages from '../Components/ProductImages';
import CustomizeProducts from '../Components/CustomizeProducts';
import Add from '../Components/Add';
import NavigationBar from '../Components/NavigationBar';
import ProductCarousel from '../Components/ProductCarousel';
import Footer from '../Components/Footer';
import axios from 'axios';
import { useLocation } from 'react-router-dom';
import { getCookie } from '../Handlers/RouteHandlers';
import Review from '../Components/Review';

const ProductPage = () => {
  const location = useLocation();
  const variantId = location.pathname.split("/")[2];
  
  const [product, setProduct] = useState(null);
  const [color, setColor] = useState(null);
  const [sizes, setSizes] = useState([]);
  const [simlrProduct, setsimlarProducts] = useState([]);
  const [error, setError] = useState(null);

  // Fetch product and new products simultaneously
  useEffect(() => {
    const fetchProductData = async () => {
      try {
        const productPromise = axios.get(`http://localhost:8080/seller/product/variant/${variantId}`, {
          headers: {
            Authorization: `Bearer ${getCookie("token")}`,
          },
        });

        const newProductsPromise = axios.get("http://localhost:8080/seller/product/check?category=MEN", {
          headers: {
            Authorization: `Bearer ${getCookie("token")}`,
          },
        });

        const [productResponse, newProductsResponse] = await Promise.all([productPromise, newProductsPromise]);
        
        // Set product data
        setProduct(productResponse.data);
        if (productResponse.data?.productVariant?.length > 0) {
          setColor(productResponse.data.productVariant[0].colour); // Set initial color
        }

        // Set new products data
        setsimlarProducts(newProductsResponse.data);
      } catch (err) {
        console.error("Error fetching data:", err);
        setError("Failed to load product or new products data.");
      }
    };

    fetchProductData();
  }, [variantId]);

  // Set available sizes when the color changes
  useEffect(() => {
    if (color && product) {
      const availableSizes = product.productVariant
        .filter(variant => variant.colour === color)
        .map(variant => ({ id: variant.variantId, size: variant.size }));
      setSizes(availableSizes);
    }
  }, [color, product]);

  // Error rendering
  if (error) return <p>{error}</p>;

  return (
    <>
      <NavigationBar />
      {product && (
        <div className='px-4 md:px-8 lg:px-16 xl:px-32 2xl:px-64 relative flex flex-col lg:flex-row gap-16 mt-4'>
          {/* Product Images Section */}
          <div className="w-full lg:w-1/2 lg:sticky top-20 h-max">
            <ProductImages images={product.images} />
          </div>

          {/* Product Info Section */}
          <div className="w-full lg:w-1/2 flex flex-col gap-6">
            <h1 className="text-4xl font-medium text-gray-900">{product.title}</h1>
            <p className="text-gray-700">{product.description}</p>
            <div className="flex items-center gap-4">
              <h3 className="text-xl text-gray-500 line-through">$80</h3>
              <h2 className="font-medium text-2xl">{product.price}</h2>
            </div>
            <div className="h-[2px] bg-lite mb-8" />

            <div className='flex flex-col gap-4'>
              <CustomizeProducts sizes={sizes} product={product} />
              <Add product={product} variantId={variantId} stockNumber={product.stock} />
            </div>

            {/* Additional Info Sections */}
            {[...Array(3)].map((_, index) => (
              <div key={index}>
                <div className="h-[2px] bg-lite" />
                <div className="text-sm">
                  <h4 className="font-medium mb-4">Title</h4>
                  <p>Lorem ipsum dolor sit amet consectetur, adipisicing elit. Deleniti sit sequi ut autem obcaecati iure nulla corrupti architecto.</p>
                </div>
              </div>
            ))}

            <div>
              <Review productId={product.productID} />
            </div>
          </div>
        </div>
      )}

      {/* Similar Products Section */}
      <div className="flex flex-col w-[73%] mx-auto mt-20">
        <span className="font-semibold text-2xl">Similar Products</span>
        {<ProductCarousel products={simlrProduct} />}
      </div>
      
      <Footer />
    </>
  );
};

export default ProductPage;
