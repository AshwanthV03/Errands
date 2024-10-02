import React from 'react';
import Menu from './Menu';
import SearchBar from './SearchBar';
import NavbarIcons from './NavbarIcons';

const NavigationBar = () => {
  return (
    <div className='h-20 sticky top-0 px-4 md:px-8 lg:px-16 xl:px-32 2xl:px-64 z-30 bg-white bg-opacity-70 backdrop-blur'>
      {/* Mobile view */}
      <div className="h-full flex items-center justify-between md:hidden">
        <a href="/">Erands</a>
        <Menu />
      </div>

      {/* BIGGER SCREENS */}
      <div className="hidden md:flex items-center justify-between h-full">
        {/* LEFT (Logo) */}
        <div className="flex items-center">
          <a href="/" className='flex items-center gap-2'>
            <img src="/logo.png" alt="Logo" width={24} height={24} />
            <div className="text-2xl tracking-wide">Erands</div>
          </a>
        </div>

        {/* CENTER (Search Bar) */}
        <div className="w-1/2">
          <SearchBar />
        </div>

        {/* RIGHT (Icons) */}
        <div className="flex items-center gap-4">
          <NavbarIcons />
        </div>
      </div>
    </div>
  );
};

export default NavigationBar;
