import React from 'react';
import ShoppingBagIcon from '@mui/icons-material/ShoppingBag';

const ProductList = ({ products }) => {
  console.log(products); // For debugging

  return (
    <div className='flex flex-wrap gap-6 items-center justify-center'>
      <div className="flex flex-wrap gap-6">
      {products.length > 0 ? (
        products.map((item,index) => {
          // Check if images array exists and has at least one element
          return (
            <a key={item.variantId} href={`/product/${item.variantId}`} 
            className=" mt-4 flex flex-col gap-4 w-[35vh] lg:p-4 border border-gray-200 shadow-md hover:shadow-2xl rounded-lg">
            <div className="relative w-full h-[30vh] bg-gray-300 rounded-lg">
              <img 
                src={item.images.length >0?item.images[1].imageUrl : "/woman.png"} 
                alt="" 
                className="absolute inset-0 object-contain w-full h-full rounded-md z-10 hover:opacity transition-opacity ease-out duration-500" 
              />
            </div>

            <div className='flex flex-col gap-2'>
              <span className="flex font-medium">{ item.productTitle}</span>
              <span className="font-semibold">Rs. {item.price}</span>
            </div>
            <button className=" rounded-full w-[42px] h-[42px] flex items-center justify-center bg-contrast text-white hover:bg-white hover:text-contrast">
              <ShoppingBagIcon/>
            </button>
          </a>
          );
        })
      ) : (
        <div className="text-center">Not available</div>
      )}
      </div>
    </div>
  );
};

// Wrap the component with React.memo for performance optimization
export default React.memo(ProductList);
