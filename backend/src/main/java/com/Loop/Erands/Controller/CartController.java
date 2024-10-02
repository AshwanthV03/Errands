package com.Loop.Erands.Controller;

import com.Loop.Erands.DTO.CartDto.CartDto;
import com.Loop.Erands.DTO.CartDto.CartItemDto;
import com.Loop.Erands.DTO.CartDto.CartUpdateDto;
import com.Loop.Erands.Models.CartItem;
import com.Loop.Erands.Service.Cart.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/cart")
public class CartController {
    @Autowired
    CartService cartService;

    @GetMapping("/{userId}")
    public ResponseEntity<?> getCartItems(@PathVariable UUID userId){
        List<CartItemDto> cartItems;
        try {
            cartItems = cartService.getCart(userId);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
       return new ResponseEntity<>(cartItems, HttpStatus.OK);

    }

    @PostMapping("/addToCart")
    public ResponseEntity<String> addToCart(@RequestBody CartDto cartDto) throws Exception {
        String response;
        try {
            response = cartService.addItemToCart(cartDto);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @DeleteMapping("/{itemId}")
    public ResponseEntity<String> deleteItem(@PathVariable UUID itemId){
        String response;
        try{
            response = cartService.removeItemfromCart(itemId);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(response,HttpStatus.OK);
    }
    @PutMapping("/{userId}")
    public ResponseEntity<String> updateCart(@RequestBody CartUpdateDto cartUpdateDto){
        String response;

        try{
            response = cartService.updateCartItem(cartUpdateDto);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(response,HttpStatus.OK);
    }
}
