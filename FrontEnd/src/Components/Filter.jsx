import React, { useState } from 'react';
import FilterAltIcon from '@mui/icons-material/FilterAlt';
import { Colours } from '../Colours';

const Filter = (props) => {
  const { handleFilterClick, filterData, handleFilterData } = props;
  const [showFilters, setShowFilters] = useState(false); // State to toggle filter visibility

  const handleFilterChange = (e) => {
    const { name, value } = e.target;
    handleFilterData({ [name]: value });
  };

  const handleColorClick = (color) => {
    const updatedColors = filterData.selectedColors.includes(color)
      ? filterData.selectedColors.filter(c => c !== color)
      : [...filterData.selectedColors, color];

    handleFilterData({ selectedColors: updatedColors });
  };

  const handleTagRemove = (color) => {
    const updatedColors = filterData.selectedColors.filter(c => c !== color);
    handleFilterData({ selectedColors: updatedColors });
  };

  const toggleFilterVisibility = () => {
    setShowFilters(!showFilters); // Toggle the visibility of the filter section
  };

  return (
    <div className="mt-12 flex flex-col gap-6">
      {/* More Filters Button */}

      <button
        type="button"
        className=" flex flex-row gap-2 w-[20vh] py-2 px-4 rounded-md bg-gray-200 text-black font-medium"
        onClick={toggleFilterVisibility}
      >
        <FilterAltIcon className='text-red-500'/> More Filter
      </button>

      {/* Filter Options - Visible only when 'More Filters' is clicked */}
        <form onSubmit={handleFilterClick} className="flex flex-col gap-6 mt-4">
      {showFilters && (
          <div className="flex gap-6 flex-wrap p-4 rounded-lg">
            <div className="flex flex-col gap-2 p-4 rounded-lg">
              {/* Price Range Filter */}
              <div className="flex flex-row gap-4">
                <label htmlFor="min">From</label>
                <input
                  type="text"
                  name="minPrice"
                  placeholder="min price"
                  className="text-xs rounded-2xl p-2 w-24 ring-1 ring-gray-400"
                  onChange={handleFilterChange}
                  value={filterData.minPrice || ''}
                />
                <label htmlFor="max">To</label>
                <input
                  type="text"
                  name="maxPrice"
                  placeholder="max price"
                  className="text-xs rounded-2xl pl-2 w-24 ring-1 ring-gray-400"
                  onChange={handleFilterChange}
                  value={filterData.maxPrice || ''}
                />
              </div>

              {/* Color Filter */}
              <div>
                <label htmlFor="color">Choose colors</label>
                <div className="flex flex-wrap gap-2 mb-4">
                  {Object.entries(Colours).map(([key, value]) => (
                    <button
                      key={key}
                      type="button"
                      className={`px-3 py-1 rounded-full text-gray-900 bg-gray-200`}
                      onClick={() => handleColorClick(key)}
                    >
                      {key.charAt(0).toUpperCase() + key.slice(1)}
                    </button>
                  ))}
                </div>
                
                {filterData.selectedColors.length > 0 && (
                  <div className="flex flex-wrap gap-2">
                    {filterData.selectedColors.map(color => (
                      <div
                        key={color}
                        className={`px-3 py-1 rounded-full text-white ${Colours[color]} flex items-center`}
                      >
                        {color.charAt(0).toUpperCase() + color.slice(1)}
                        <button
                          type="button"
                          className="ml-2 bg-gray-700 text-xs rounded-full px-2"
                          onClick={() => handleTagRemove(color)}
                        >
                          &times;
                        </button>
                      </div>
                    ))}
                  </div>
                )}
              </div>
            </div>
          </div>
          )}

          {/* Sort Filter */}
          <div className="flex gap-6">
            <select
              name="sort"
              id="sort"
              className="py-2 px-4 rounded-2xl text-xs font-medium bg-white ring-1 ring-gray-400"
              onChange={handleFilterChange}
              value={filterData.sort || ''}
            >
              <option value="">Sort By</option>
              <option value="asc">Price (low to high)</option>
              <option value="desc">Price (high to low)</option>
            </select>
            <button
              type="submit"
              className="py-2 px-4 rounded-2xl bg-blue-500 text-white font-medium"
            >
              Apply Filters
            </button>
          </div>
        </form>
    </div>
  );
};

export default Filter;
