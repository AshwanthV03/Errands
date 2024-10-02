package com.Loop.Erands.Service.Cart;

import com.Loop.Erands.DTO.CartDto.CartDto;
import com.Loop.Erands.DTO.CartDto.CartItemDto;
import com.Loop.Erands.DTO.CartDto.CartUpdateDto;
import com.Loop.Erands.Models.CartItem;

import java.util.List;
import java.util.UUID;

public interface CartService {
    /**
     * Retrieves the list of items in the cart for a given user.
     *
     * @param cartId The UUID of the cart to retrieve.
     * @return A list of CartItemDto objects representing the items in the cart.
     * @throws Exception if the cart does not exist or cannot be retrieved.
     */
    public List<CartItemDto> getCart(UUID cartId) throws Exception;

    /**
     * Adds an item to the cart based on the provided CartDto object.
     *
     * @param cartDto The DTO containing information about the item to add.
     * @return A success message indicating the item was added.
     * @throws Exception if the cart is not found or if there is an error adding the item.
     */
    public String addItemToCart(CartDto cartDto) throws Exception;

    /**
     * Removes an item from the cart using the cart item ID.
     *
     * @param cartItemId The UUID of the cart item to remove.
     * @return A success message indicating the item was deleted.
     * @throws Exception if the item is not found or cannot be deleted.
     */
    public String removeItemfromCart(UUID cartItemId) throws Exception;

    /**
     * Updates the quantity of a specific cart item.
     *
     * @param cartUpdateDto The DTO containing updated information for the cart item.
     * @return A success message indicating the cart item was updated.
     * @throws Exception if the cart is not found or if the update fails.
     */
    public String updateCartItem(CartUpdateDto cartUpdateDto) throws Exception;

    /**
     * Clears all items from the cart identified by the given UUID.
     *
     * @param cartId The UUID of the cart to clear.
     * @return A success message indicating the cart was cleared.
     * @throws Exception if the cart is not found or cannot be cleared.
     */
    public String clearCart(UUID cartId) throws Exception;
}
