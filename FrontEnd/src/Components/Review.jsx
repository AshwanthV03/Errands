import React, { useEffect, useState } from 'react';
import StarIcon from '@mui/icons-material/Star';
import AddReview from './AddReview';
import axios from 'axios';
import { getCookie } from '../Handlers/RouteHandlers';

const Review = (prop) => {
    const [reviewActive, setReviewActive] = useState(false);
    const [reviews, setReviews] = useState([]);
    const [filteredReviews, setFilteredReviews] = useState([]);
    const [selectedRating, setSelectedRating] = useState(null); // State to track selected rating

    useEffect(() => {
        const fetchData = async () => {
            try {
                const response = await axios.get(`http://localhost:8080/reviews/product/${prop.productId}`, {
                    headers: {
                        Authorization: `Bearer ${getCookie("token")}`
                    }
                });
                setReviews(response.data);
                setFilteredReviews(response.data); // Initialize filteredReviews with all reviews
                console.log(response.data);
            } catch (error) {
                console.log(error);
            }
        };

        fetchData();
    }, [prop.productId]);

    // Method to filter reviews based on selected rating
    const filterReviewsByRating = (rating) => {
        setSelectedRating(rating); // Update selected rating
        if (rating) {
            const filtered = reviews.filter(review => review.rating === rating);
            setFilteredReviews(filtered);
        } else {
            setFilteredReviews(reviews); // Show all reviews if no rating is selected
        }
    };

    return (
        <div className='p-6 flex flex-col gap-2'>
            <button 
                onClick={() => setReviewActive(!reviewActive)} 
                className='text-contrast bg-extra_Lite hover:bg-contrast hover:text-extra_Lite w-[25%] py-2 px-2 rounded-lg'>
                {reviewActive ? "Discard review" : "Add review"}
            </button>

            {reviewActive && (
                <div className="flex flex-col gap-2">
                    <AddReview productId={prop.productId} setReviewActive={setReviewActive} />
                </div>
            )}

            <div className="flex flex-row gap-2">
                {
                    [1, 2, 3, 4, 5].map((i) => (
                        <div 
                            key={i}
                            className="flex flex-row gap-2 bg-gray-200 rounded-md p-1 px-2 cursor-pointer" 
                            onClick={() => filterReviewsByRating(i)}>
                            <StarIcon className='text-yellow-500' />
                            <span>{i}</span>
                        </div>
                    ))
                }
                <div 
                    className="flex flex-row gap-2 bg-gray-200 rounded-md p-1 px-2" 
                    onClick={() => filterReviewsByRating(null)}> {/* Clear filter */}
                    <span>All</span>
                </div>
            </div>

            <div className="flex flex-col bg-gray-100 rounded-md p-4">
                {filteredReviews.length > 0 ? filteredReviews.map(review => (
                    <div key={review.reviewId} className="flex flex-col gap-2 mb-4">
                        <div className="flex flex-row gap-2">
                            <span className="text-lg font-semibold">User Name</span>
                            <div className="flex flex-row gap-2 bg-gray-200 rounded-md p-1 px-2">
                                <StarIcon className='text-yellow-500' />
                                <span>{review.rating}</span>
                            </div>
                        </div>
                        <span>{review.reviewDescription}</span>
                    </div>
                )) : (
                    <span>No reviews found for the selected rating.</span>
                )}
            </div>
        </div>
    );
}

export default Review;
