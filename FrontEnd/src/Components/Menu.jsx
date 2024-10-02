import React, { useState } from 'react'

const Menu = () => {
    const [open,setOpen] = useState(false)
  return (
    <div className=''>
        <img src="/menu.png" alt="" width={28} height={28} className='cursor-pointer' onClick={()=>setOpen(!open)}/>
        {
            open&&(
                <div className=" absolute bg-black text-white left-0 top-20 w-full h-[calc(100vh-80px)] flex flex-col items-center justify-center gap-8 text-xl z-10">
                    <a href="">Home</a>
                    <a href="">shop</a>
                    <a href="">about</a>
                    <a href="">contact</a>
                    <a href="">cart(1)</a>
                    <a href="">LogOut</a>

                </div>
            )
        }
    </div>
  )
}

export default Menu