import React from 'react'
import {Colours} from '../Colours'
const CustomizeProducts = (prop) => {
  const {sizes,product} = {...prop}

  return (
    sizes ? (<div className='flex flex-col gap-6'>
      <h4 className="">Choose a colour</h4>
      <ul className="flex gap-4">
        {
          product.productVariant.map((item,index)=>(
            <a href={`/product/${item.variantId}`} className="">
              <li  className={`w-8 h-8 rounded-full ring-1 ring-gray-300 cursor-pointer relative ${Colours[item.colour]}`}>
                <div className=" absolute w-10 h-10 rounded-full ring-2 top-1/2 left-1/2 transform -translate-x-1/2 -translate-y-1/2"></div>
              </li>
            </a>
          ))
        }

       
      </ul>
      <div className=""></div>
      <h4 className="font-medium">Choose Size</h4>
      <ul className="flex gap-4">
        {
          sizes.map((item,index)=>(
            <a href={`/product/${item.id}`} className="">
              <li key={index} className="ring-1 ring-contrast text-contrast rounded-md py-1 px-4 text-sm cursor-pointer">
              {item.size}
              </li>
            </a>
          ))
        }

      </ul>
    </div>):<></>
  )
}

export default CustomizeProducts