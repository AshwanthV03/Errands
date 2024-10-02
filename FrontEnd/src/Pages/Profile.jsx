import React, { useEffect, useState } from 'react';
import ShoppingBagIcon from '@mui/icons-material/ShoppingBag';
import PersonIcon from '@mui/icons-material/Person';
import BusinessIcon from '@mui/icons-material/Business';
import SupportAgentIcon from '@mui/icons-material/SupportAgent';
import AddCardIcon from '@mui/icons-material/AddCard';
import StoreIcon from '@mui/icons-material/Store';

import NavigationBar from '../Components/NavigationBar'
import EditUserForm from '../Components/EditUserForm';
import Addresssettings from '../Components/Addresssettings';
import { useDispatch, useSelector } from 'react-redux';
import { logout } from '../Redux/userSlice';
const Profile = () => {
  const [editActive,setEditActive] = useState(false)
  const [editAddressActive, setEditAddressActive] = useState(false)
  const user = useSelector((state)=>state.user.User)
  console.log(user)
  const profileOptions = [
    { icon: <ShoppingBagIcon sx={{ fontSize: 40 }} className="text-blue-950" />, label: 'Your orders', handle:()=>{setEditActive(true)} },
    { icon: <PersonIcon sx={{ fontSize: 40 }} className="text-blue-950" />, label: 'Edit User Details',handle:()=>{setEditActive(true) }},
    { icon: <BusinessIcon sx={{ fontSize: 40 }} className="text-blue-950" />, label: 'Add address', handle:()=>{setEditAddressActive(true) }},
    { icon: <AddCardIcon sx={{ fontSize: 40 }} className="text-blue-950" />, label: 'Your subscription', handle:()=>{setEditActive(true) }},
    { icon: <SupportAgentIcon sx={{ fontSize: 40 }} className="text-blue-950" />, label: 'Customer care', handle:()=>{setEditActive(true) }},
    { icon: <StoreIcon sx={{ fontSize: 40 }} className="text-blue-950" />, label: 'Become Seller', handle:()=>{setEditActive(true) }},

  ];


  return (
    <>
    <NavigationBar/>
    <div className=" relative flex flex-col w-[80%] mx-[10%] gap-6">
      <div className="flex flex-col">
        <span>Welcome,</span>
        <span className="text-2xl font-medium">{user.userName}</span>
      </div>

      <div className="flex flex-wrap gap-12">
        {profileOptions.map((option, index) => (
          <div
            key={index}
            onClick={option.handle}
            className="w-[30vh] h-[10vh] rounded-md shadow-mg border border-gray-300 hover:shadow-2xl cursor-pointer flex flex-col items-center justify-center"
          >
            {option.icon}
            <span className="text-lg font-medium">{option.label}</span>
          </div>
        ))}
      </div>
      {editActive&& <div className=" absolute w-full">
        <EditUserForm setEditActive={setEditActive}/>
      </div>}
      {editAddressActive&&
      <div className=" absolute w-full">
        <Addresssettings editAddressActive={editAddressActive} setEditAddressActive={setEditAddressActive}/>
      </div>
      }
    </div>

    </>
  );
};

export default Profile;
