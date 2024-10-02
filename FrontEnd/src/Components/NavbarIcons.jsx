import React, { useState } from 'react'
import CartModel from './CartModel';
import ShoppingBagRoundedIcon from '@mui/icons-material/ShoppingBagRounded';
import { useDispatch, useSelector } from 'react-redux';
import { logout } from '../Redux/userSlice';
const NavbarIcons = () => {
    const [isProfileActive,setIsProfileActive] = useState(false);
    const [isCartActive,setIsCartActive] = useState(false);
    const cart = useSelector((state) => state.cart);
    const dispatch = useDispatch();
    const handleLogOut =(e)=>{
      e.preventDefault()
      dispatch(logout())
    }
console.log(cart)

    const handleProfile = () =>{
        setIsProfileActive(!isProfileActive)
    }
    const logged = true
  return (
    <div className=' flex items-center gap-4 xl:gap-6 relative'>
        <img src="/profile.png" alt="" width={30} height={30} onClick={handleProfile} className=" cursor-pointer" />
        {
            isProfileActive &&(
                <div className=" absolute p-4 rounded-md top-12 left-0 text-sm bg-white z-20 flex flex-col gap-6">
                    <a href="/profile" className="">Profile</a>
                    <span onClick={handleLogOut} className=" cursor-pointer">Logout</span>
                </div>
            )
        }
        <div className="relative cursor-pointer">
            <a href="/cart" className="">
                <div className=" rounded-full bg-contrast text-white p-1 flex items-center justify-normal"><ShoppingBagRoundedIcon/></div>
                <div className=" absolute -top-3 -right-2 w-5 h-5 bg-red-500 rounded-full text-sm flex items-center justify-center text-white">{cart.Count}</div>
            </a>
        </div>
        {
            isCartActive && <CartModel/>
        }

    </div>
  )
}

export default NavbarIcons