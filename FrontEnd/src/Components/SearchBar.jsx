import axios from 'axios';
import React, { useState, useCallback } from 'react';
import { getCookie } from '../Handlers/RouteHandlers';
import { useNavigate } from 'react-router-dom';
import debounce from 'lodash/debounce';

const SearchBar = () => {
  const navigate = useNavigate();
  const [searchResult, setSearchResult] = useState([]);
  const [keyword, setKeyword] = useState("");

  const fetchSearchResults = async (searchTerm) => {
    try {
      const response = await axios.get(`http://localhost:8080/seller/product/search?search=${searchTerm}`, {
        headers: {
          Authorization: `Bearer ${getCookie("token")}`,
        },
      });
      if (response.data !== searchResult) {
        setSearchResult(response.data); // Only update if results are different to avoid unnecessary renders
      }
    } catch (error) {
      console.error("Error fetching search results", error);
      setSearchResult([]); // Reset results on error
    }
  };

  const debouncedSearch = useCallback(
    debounce((searchTerm) => {
      if (searchTerm) {
        fetchSearchResults(searchTerm);
      } else {
        setSearchResult([]); 
      }
    }, 300),
    []
  );

  const onChangeHandler = (e) => {
    const searchTerm = e.target.value;
    setKeyword(searchTerm);
    debouncedSearch(searchTerm);
  };

  const handleSearch = (e) => {
    e.preventDefault();
    navigate(`/products?search=${keyword}`);
  };

  return (
    <form className="flex relative items-center justify-between gap-4 bg-gray-100 p-2 rounded-md flex-1" onSubmit={handleSearch}>
      <input
        type="text"
        placeholder="Search"
        onChange={onChangeHandler}
        className="bg-transparent outline-none"
        value={keyword}
      />
      <button type="submit" className="cursor-pointer">
        <img src="/search.png" alt="search" width={16} />
      </button>
      {searchResult.length > 0 && (
        <div className="absolute flex flex-col gap-2 bg-gray-100 p-6 rounded-md shadow-lg w-[100%] top-12">
          {searchResult.map((item, index) => (
            <a key={index} href={`/product/${item.variantId}`}>
              <span className="text-[17px] p-1 rounded-sm cursor-pointer hover:bg-gray-300">{item.productTitle}</span>
            </a>
          ))}
        </div>
      )}
    </form>
  );
};

export default SearchBar;
