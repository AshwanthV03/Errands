import React, { useEffect, useState } from 'react';

const Category = () => {
  const [cats, setCats] = useState([]);

  useEffect(() => {
    // Simulating fetching categories from an API
    const fetchCategories = async () => {
      // Replace this with actual API call
      const dummyData = [
        {
          _id: '1',
          name: 'Mens Wear',
          slug: 'MEN',
          media: {
            mainMedia: {
              image: {
                url: 'https://a.media-amazon.com/images/I/51yIybqYFTL._SX679_.jpg', // Replace with your image URL
              },
            },
          },
        },
        {
          _id: '2',
          name: 'Women Wear',
          slug: 'WOMEN',
          media: {
            mainMedia: {
              image: {
                url: 'https://a.media-amazon.com/images/I/81YcqY2-FxL._SX522_.jpg', // Replace with your image URL
              },
            },
          },
        },
        {
          _id: '3',
          name: 'Bottom Wear',
          slug: 'BOTTOM_WEAR',
          media: {
            mainMedia: {
              image: {
                url: 'https://m.media-amazon.com/images/I/61-n3zi0ldL._SY741_.jpg', // Replace with your image URL
              },
            },
          },
        },
        {
          _id: '3',
          name: 'Top Wear',
          slug: 'TOP_WEAR',
          media: {
            mainMedia: {
              image: {
                url: 'https://m.media-amazon.com/images/I/71lU1bfllDL._SX569_.jpg', // Replace with your image URL
              },
            },
          },
        },
        {
          _id: '3',
          name: 'Accessories',
          slug: 'ACCESSORIES',
          media: {
            mainMedia: {
              image: {
                url: 'https://m.media-amazon.com/images/I/61L+3IM75zL._SX679_.jpg', // Replace with your image URL
              },
            },
          },
        },        
      ];

      // Simulating async delay
      await new Promise(resolve => setTimeout(resolve, 1000));

      // Set state with dummy data
      setCats(dummyData);
    };

    fetchCategories();
  }, []);

  return (
    <div className="">
      <div className="flex items-center justify-center gap-4 md:gap-8">
        {cats.map((item) => (
          <a
            href={`/products?category=${item.slug}`}
            className="flex-shrink-0 w-full sm:w-1/2 lg:w-1/4 xl:w-1/6"
            key={item._id}
          >
            <div className="relative bg-slate-200 w-full h-96">
              <img
                src={item.media?.mainMedia?.image?.url || "cat.png"}
                alt=""
                className="object-cover w-full h-full"
              />
            </div>
            <h1 className="mt-8 font-light text-xl tracking-wide">{item.name}</h1>
          </a>
        ))}
      </div>
    </div>
  );
};

export default Category;
