import React, { useRef } from 'react';
import ArrowLeftIcon from '@mui/icons-material/ArrowLeft';
import ArrowRightIcon from '@mui/icons-material/ArrowRight';
import ShoppingBagIcon from '@mui/icons-material/ShoppingBag';

const ProductCarousel = (prop) => {
  console.log(prop.products)
  const carouselRef = useRef(null);
  const title = "Mens Tshirt Clasic polo"
  const products = prop.products
  const scrollLeft = () => {
    if (carouselRef.current) {
      carouselRef.current.scrollLeft -= 600; // Adjust the scroll distance as needed
    }
  };

  const scrollRight = () => {
    if (carouselRef.current) {
      carouselRef.current.scrollLeft += 600; // Adjust the scroll distance as needed
    }
  };

  // const products = [
  //   {
  //     id: 1,
  //     img1: 'https://m.media-amazon.com/images/I/71DDQfS1SfL._SX569_.jpg',
  //     img2: 'https://m.media-amazon.com/images/I/713n+TxyfCL._SX569_.jpg',
  //     name: 'Product 1',
  //     price: '$54',
  //     desc: 'Description for Product 1',
  //   },
  //   {
  //     id: 1,
  //     img1: 'https://m.media-amazon.com/images/I/71DDQfS1SfL._SX569_.jpg',
  //     img2: 'https://m.media-amazon.com/images/I/713n+TxyfCL._SX569_.jpg',
  //     name: 'Product 1',
  //     price: '$54',
  //     desc: 'Description for Product 1',
  //   },
  //   {
  //     id: 1,
  //     img1: 'https://m.media-amazon.com/images/I/71DDQfS1SfL._SX569_.jpg',
  //     img2: 'https://m.media-amazon.com/images/I/713n+TxyfCL._SX569_.jpg',
  //     name: 'Product 1',
  //     price: '$54',
  //     desc: 'Description for Product 1',
  //   },
  //   {
  //     id: 1,
  //     img1: 'https://m.media-amazon.com/images/I/71DDQfS1SfL._SX569_.jpg',
  //     img2: 'https://m.media-amazon.com/images/I/713n+TxyfCL._SX569_.jpg',
  //     name: 'Product 1',
  //     price: '$54',
  //     desc: 'Description for Product 1',
  //   },
  //   {
  //     id: 1,
  //     img1: 'https://m.media-amazon.com/images/I/71DDQfS1SfL._SX569_.jpg',
  //     img2: 'https://m.media-amazon.com/images/I/713n+TxyfCL._SX569_.jpg',
  //     name: 'Product 1',
  //     price: '$54',
  //     desc: 'Description for Product 1',
  //   },
  //   {
  //     id: 1,
  //     img1: 'https://m.media-amazon.com/images/I/71DDQfS1SfL._SX569_.jpg',
  //     img2: 'https://m.media-amazon.com/images/I/713n+TxyfCL._SX569_.jpg',
  //     name: 'Product 1',
  //     price: '$54',
  //     desc: 'Description for Product 1',
  //   },
  //   {
  //     id: 1,
  //     img1: 'https://m.media-amazon.com/images/I/71DDQfS1SfL._SX569_.jpg',
  //     img2: 'https://m.media-amazon.com/images/I/713n+TxyfCL._SX569_.jpg',
  //     name: 'Product 1',
  //     price: '$54',
  //     desc: 'Description for Product 1',
  //   },
  //   {
  //     id: 1,
  //     img1: 'https://m.media-amazon.com/images/I/71DDQfS1SfL._SX569_.jpg',
  //     img2: 'https://m.media-amazon.com/images/I/713n+TxyfCL._SX569_.jpg',
  //     name: 'Product 1',
  //     price: '$54',
  //     desc: 'Description for Product 1',
  //   },
  //   // Add more products as needed
  // ];

  return (
    <div className='relative w-full flex flex-col gap-4 mt-4'>
      <div className="flex flex-row w-[9vh] bg-lite rounded-full">
        <div className="rounded-full w-[42px] h-[42px] flex items-center justify-center cursor-pointer hover:bg-contrast hover:text-white" onClick={scrollLeft}>
          <ArrowLeftIcon />
        </div>
        <div className="rounded-full w-[42px] h-[42px] flex items-center justify-center cursor-pointer hover:bg-contrast hover:text-white" onClick={scrollRight}>
          <ArrowRightIcon />
        </div>
      </div>

      <div className="flex flex-row justify-between items-center gap-8" style={{ overflowX: 'scroll', scrollBehavior: 'smooth', scrollbarWidth: 'none', '-ms-overflow-style': 'none' }} ref={carouselRef}>
        {products.map((product) => (
          <a key={product.variantId} href={`/product/${product.variantId}`} 
            className=" flex flex-col gap-4 min-w-[35vh] lg:p-4 border border-gray-200 shadow-md rounded-lg">
            <div className="relative w-full h-[30vh] bg-gray-300 rounded-lg">
              <img 
                src={product.images.length >0?product.images[1].imageUrl : "/woman.png"} 
                alt="" 
                className="absolute inset-0 object-contain w-full h-full rounded-md z-10 hover:opacity transition-opacity ease-out duration-500" 
              />
            </div>

            <div className='flex flex-col gap-2'>
              <span className="flex font-medium">{ product.name?product.name: title}</span>
              <span className="font-semibold">Rs. {product.price}</span>
            </div>
            <button className=" rounded-full w-[42px] h-[42px] flex items-center justify-center bg-contrast text-white hover:bg-white hover:text-contrast">
              <ShoppingBagIcon/>
            </button>
          </a>
        ))}
      </div>


    </div>
  );
};

export default ProductCarousel;
