import React, { useState } from 'react';
import StarIcon from '@mui/icons-material/Star';
import CloseIcon from '@mui/icons-material/Close';
import { useSelector } from 'react-redux';
import axios from 'axios';
import { getCookie } from '../Handlers/RouteHandlers';

const AddReview = (props) => {
    const user = useSelector((state) => state.user.User);
    const [formData, setFormData] = useState({
        rating: 0,
        reviewTitle: "Best Product",
        reviewDescription: "Best quality and premium touch",
        productId: props.productId, // Pass the productId from props
        userID:user.userId // Pass the userId from props
    });

    const handleClick = (value) => {
        setFormData((prev) => ({
            ...prev,
            rating: value // Update rating directly with the clicked value
        }));
    };

    const handleTitleChange = (e) => {
        const { value } = e.target;
        setFormData((prev) => ({
            ...prev,
            reviewTitle: value
        }));
    };

    const handleDescriptionChange = (e) => {
        const { value } = e.target;
        setFormData((prev) => ({
            ...prev,
            reviewDescription: value
        }));
    };

    const handleSubmit =async(e) => {
        e.preventDefault();
        // Call the API to submit the review with formData
        console.log(formData);
        try {
            const response = await axios.post(`http://localhost:8080/reviews/addReview/${props.productId}`,formData, {
              headers: {
                Authorization: `Bearer ${getCookie("token")}`
              }
            });
            console.log(response.data)

          } catch (error) {
            console.log(error);
          }
    };

    return (
        <form className='bg-lite p-4 rounded-md' onSubmit={handleSubmit}>
            <div className="flex flex-col gap-2">
                <label htmlFor="rating">Rating</label>
                <div className="flex flex-row gap-2">
                    {[1, 2, 3, 4, 5].map((item) => (
                        <div
                            key={item}
                            onClick={() => handleClick(item)} // Pass item to handleClick
                            className={`cursor-pointer flex flex-row gap-1 ${formData.rating >= item ? 'text-yellow-500' : 'text-gray-400'}`}
                        >
                            <StarIcon />
                        </div>
                    ))}
                </div>
            </div>
            <div className="flex flex-col gap-2">
                <label htmlFor="reviewTitle">Title</label>
                <input 
                    type="text" 
                    className="border p-2 rounded-md" 
                    value={formData.reviewTitle} 
                    onChange={handleTitleChange}
                />
            </div>
            <div className="flex flex-col gap-2">
                <label htmlFor="reviewDescription">Description</label>
                <textarea
                    placeholder='Describe your experience'
                    className='h-[15vh] px-4 py-2 rounded-md border'
                    value={formData.reviewDescription}
                    onChange={handleDescriptionChange}
                />
            </div>
            <button type="submit" className="bg-blue-500 text-white rounded-md p-2 mt-4">
                Submit Review
            </button>
        </form>
    );
};

export default AddReview;
