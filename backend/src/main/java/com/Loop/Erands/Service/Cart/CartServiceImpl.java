package com.Loop.Erands.Service.Cart;

import com.Loop.Erands.DTO.CartDto.CartDto;
import com.Loop.Erands.DTO.CartDto.CartItemDto;
import com.Loop.Erands.DTO.CartDto.CartUpdateDto;
import com.Loop.Erands.Models.Cart;
import com.Loop.Erands.Models.CartItem;
import com.Loop.Erands.Models.ProductVariant;
import com.Loop.Erands.Repository.CartItemRepository;
import com.Loop.Erands.Repository.CartRepository;
import com.Loop.Erands.Service.ProductServices.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CartServiceImpl implements CartService {
    @Autowired
    CartRepository cartRepository;
    @Autowired
    CartItemRepository cartItemRepository;
    @Autowired
    ProductService productService;

    @Override
    public List<CartItemDto> getCart(UUID userId) throws Exception {
        // Fetch the cart associated with the user ID
        Cart fetchedCart = cartRepository.findCartByUserId(userId)
                .orElseThrow(() -> new Exception("Cart does not exist for this ID"));

        // Map the CartItem entities to CartItemDto objects
        List<CartItemDto> cartItemDtos = fetchedCart.getCartItems()
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());

        return cartItemDtos;
    }


    @Override
    public String addItemToCart(CartDto cartDto) throws Exception {
        // Retrieve the cart associated with the user ID
        Cart cart = cartRepository.findCartByUserId(cartDto.getUserId())
                .orElseThrow(() -> new Exception("Cart not found for user: " + cartDto.getUserId()));

        // Retrieve the product variant to ensure it exists
        ProductVariant productVariant = productService.findProductVariantById(cartDto.getProductVariantId());
        if (productVariant == null) {
            throw new Exception("Product variant not found for ID: " + cartDto.getProductVariantId());
        }
        // Create a new CartItem and set its properties
        CartItem cartItem = new CartItem();
        cartItem.setProductVariant(productVariant);
        cartItem.setItemCount(cartDto.getProductCount());
        // Add the item to the cart
        cart.addCartItem(cartItem);
        // Save the updated cart to the repository
        cartRepository.save(cart);

        // Return a success message
        return "Item added successfully";
    }


    @Override
    public String removeItemfromCart(UUID cartItemId) throws Exception {
        // Check if the cart item exists before attempting to delete it
        if (!cartItemRepository.existsById(cartItemId)) {
            throw new Exception("Cart item not found for ID: " + cartItemId);
        }

        // Perform the deletion of the cart item
        cartItemRepository.deleteByCartItemID(cartItemId);

        // Return success message after deletion
        return "Cart item deleted!";
    }


    @Override
    public String updateCartItem(CartUpdateDto cartUpdateDto) throws Exception {
        Cart cart = cartRepository.findCartByUserId(cartUpdateDto.getUserId())
                .orElseThrow(() -> new Exception("Cart not found for user: " + cartUpdateDto.getUserId()));

        // Find and update the cart item
        CartItem cartItem = cart.getCartItems().stream()
                .filter(item -> item.getCartItemID().equals(cartUpdateDto.getCartItemId()))
                .findFirst()
                .orElseThrow(() -> new Exception("Cart item not found for ID: " + cartUpdateDto.getCartItemId()));

        cartItem.setItemCount(cartUpdateDto.getItemUpdatedCount());
        cartRepository.save(cart); // Save the updated cart

        return "Cart item updated successfully";
    }


    @Override
    public String clearCart(UUID cartId) throws Exception {
        // Find cart By cartId
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new Exception("Cart not found for ID: " + cartId));

        cart.clearCart();
        cartRepository.save(cart);

        return "Cart cleared successfully";
    }

    // Helper method to convert CartItem to CartItemDto
    private CartItemDto convertToDto(CartItem ci) {
        return new CartItemDto(
                ci.getCartItemID(),
                ci.getProductVariant().getVariantId(),
                ci.getProductVariant().getProduct().getProductId(),
                ci.getProductVariant().getImages().stream().toList(),
                ci.getProductVariant().getProduct().getProductTitle(),
                ci.getProductVariant().getStockCount(),
                ci.getItemCount(),
                ci.getProductVariant().getPrice(),
                ci.getProductVariant().getSize()
        );
    }

}
