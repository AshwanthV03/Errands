import React from 'react'

const Footer = () => {
  return (
    <div className='py-24 px-4 md:px-8 lg:px-16 xl:px-32 2xl:px-64  bg-gray-100 text-sm mt-24'>
        {/* Top */}
        <div className="flex flex-col md:flex-row justify-between gap-24" >
            {/* LEFT */}
            <div className="w-full md:w-1/2 lg:w-1/4 flex flex-col gap-8">
                <a href="/" className="">
                    <div className=" text-2xl tracking-wider">ERANDS</div>
                </a>
                <p className="">
                    123, MG Road, Koramangala, Bangalore, Karnataka, India - 56003
                </p>
                <span className="font-semibold ">Erands@gmail.com</span>
                <span className="font-semibold ">(+040) 265 741</span>
                <div className=" flex gap-6">
                    <img src="/facebook.png" alt="" width={16} height={16} className="" />
                    <img src="/instagram.png" alt="" width={16} height={16} className="" />
                    <img src="/youtube.png" alt="" width={16} height={16} className="" />
                    <img src="/pinterest.png" alt="" width={16} height={16} className="" />
                    <img src="/x.png" alt="" width={16} height={16} className="" />
                </div>
            </div>
            {/* CENTER */}
            <div className="w-1/2 hidden lg:flex justify-between">
                {/* C1 */}
                <div className=" flex flex-col">
                    <h1 className="font-medium mb-8">COMPANY</h1>
                    <div className=" flex flex-col justify-between h-full">
                        <a href="" className="">About-Us</a>
                        <a href="" className="">Careers</a>
                        <a href="" className="">Affiliates</a>
                        <a href="" className="">Blog</a>
                        <a href="" className="">Contact-us</a>
                    </div>
                </div>

                {/* C2 */}
                <div className=" flex flex-col">
                    <h1 className="font-medium mb-8">SHOP</h1>
                    <div className=" flex flex-col justify-between h-full">
                        <a href="" className="">New arraivals</a>
                        <a href="" className="">Accessories</a>
                        <a href="" className="">Men</a>
                        <a href="" className="">Women</a>
                        <a href="" className="">more Category</a>
                    </div>
                </div>

                {/* C3 */}
                <div className=" flex flex-col">
                    <h1 className="font-medium mb-8">HELP</h1>
                    <div className=" flex flex-col justify-between h-full">
                        <a href="" className="">Customer Service</a>
                        <a href="" className="">My Account</a>
                        <a href="" className="">Find a Store</a>
                        <a href="" className="">Privacy&policy</a>
                        <a href="" className="">Gift Card</a>
                    </div>
                </div>               
            </div>
            {/* RIGHT */}
            <div className="w-full md:w-1/2 lg:w-1/4 flex flex-col gap-8">
                <h1 className="font-medium text-lg">SUBSCRIBE</h1>
                <p className="">Be the first to fet the latest news about trend</p>
                <div className="flex">
                    <input type="text" placeholder='Email' className="p-4 w-3/4" />
                    <button className="w-1/4 bg-red-500 text-white"> Join</button>
                </div>
                    <span className=" font-semibold">Secure payment</span>
                    <div className=" flex justify-between">
                    <img src="/discover.png" alt="" width={40} height={40} className="" />
                    <img src="/skrill.png" alt="" width={40} height={40} className="" />
                    <img src="/paypal.png" alt="" width={40} height={40} className="" />
                    <img src="/mastercard.png" alt="" width={40} height={40} className="" />
                    <img src="/visa.png" alt="" width={40} height={40} className="" />
                </div>
            
            </div>
        </div>
        {/* Bottom */}
        <div className="flex flex-col md:flex-row items-center justify-between gap-8 mt-16">
            <div className="">&copy; 2024 Erands</div>
            <div className=" flex flex-col gap-8 md:flex-row">
                <div className="">
                    <span className=" text-gray-500 mr-4">Language</span>
                    <span className="font-medium">United States | English</span>
                </div>
                <div className="">
                    <span className=" text-gray-500 mr-4">Currency</span>
                    <span className="font-medium">Rupees | &#8377; INR</span>
                </div>
            </div>

        </div>

    </div>
  )
}

export default Footer