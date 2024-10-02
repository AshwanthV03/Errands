import React from 'react'

const CartModel = () => {
    const cartItems = true;

  return (
    <div className=' w-max absolute p-4 rounded-md shadow-[0_3px_10px_rgb(0,0,0,0.2)] bg-white top-12 right-0 flex flex-col gap-6 z-20'>
        {
            !cartItems ? (
                <div className="">Cart is empty</div>
            )
            :(
                <>
                <h2 className=" text-xl cursor-default">Shoping Cart</h2>
                <div className=" flex gap-4"> 
                <img src="https://m.media-amazon.com/images/I/71DDQfS1SfL._SX569_.jpg" alt="" width={72} height={96} className='object-cover rounded-md' />
                    <div className=" flex flex-col justify-between w-full">
                        {/* TOP */}
                        <div className="">
                            {/* TITLE */}
                            <div className="flex items-center justify-between gap-8">
                                <h3 className=' font-semibold'>Product Name</h3>
                                <div className=" p-1 bg-gray-50 rounded-sm  cursor-default">$49</div>
                            </div>
                            {/* DECS */}
                            <div className=" text-sm text-gray-500 cursor-default">
                                Available
                            </div>
                        </div>
                        {/* BOTTOM */}
                        <div className=" flex justify-between text-sm">
                            <span className="text-gray-500 cursor-default"> QYT 2</span>
                            <span className="text-blue-500 cursor-pointer"> remove</span>
                        </div>
                    </div>
                </div>
                
                {/* // bottom */}
                <div className="">
                    <div className="flex items-center justify-between font-semibold">
                        <span className="">subtotal</span>
                        <span className="">$54</span>
                    </div>
                    <p className='text-gray-500 text-sm mt-2 mb-4'>
                        Shipping and taxes calculated on checkout
                    </p>
                    <div className="flex justify-between text-sm">
                        <button className='rounded-md py-3 px-4 ring-1 ring-gray-300'>View Cart</button>
                        <button className="rounded-md py-3 px-4 bg-black text-white ">Checkout</button>
                    </div>
                </div>
                </>
            )}
    </div>
  )
}

export default CartModel