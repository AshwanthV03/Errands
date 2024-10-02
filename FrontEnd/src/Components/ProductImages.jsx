import React, { useState } from 'react'

const ProductImages = ({images}) => {
    console.log()
    const [index, setIndex] = useState(0);

  return (
        <div className="">
        <div className="h-[500px] bg-gray-200 rounded-lg relative">
            <img
            src={images.length >0?images[index].imageUrl:"/woman.png"}
            alt=""
            className="object-contain w-full h-full rounded-md"
            />
        </div>

        <div className="flex gap-6 mt-8">
            {images.map((img, i) => (
            <div
                className="h-32 relative cursor-pointer border"
                key={img.productImageId}
                onClick={() => setIndex(i)}
            >
                <img
                src={img.imageUrl}
                alt=""
                className="object-contain w-full h-full rounded-md"
                />
            </div>
            ))}
        </div>
        </div>

  )
}

export default ProductImages