package com.Loop.Erands.Controller;

import com.Loop.Erands.Authentication.AuthHandler;

import com.Loop.Erands.DTO.AddressDto.AddAddressDto;
import com.Loop.Erands.DTO.UserDto.UpdateDto;
import com.Loop.Erands.Models.Address;
import com.Loop.Erands.Models.User;
import com.Loop.Erands.Service.JwtServices.JwtService;
import com.Loop.Erands.Service.UserService.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    AuthHandler authHandler;
    @Autowired
    JwtService jwtService;

    @GetMapping(path="/getUser/{userId}")
    public ResponseEntity<?> findUserById(@PathVariable UUID userId) throws Exception {
        User fetchedUser;
        try{
            fetchedUser = userService.findUserById(userId);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(fetchedUser,HttpStatus.OK);
    }
    @PutMapping(path = "/updateUser/{userId}")
    public ResponseEntity<String> updateUserById(@PathVariable("userId") UUID requestingUser, @RequestBody UpdateDto updateReq) {
        try {
            if (!requestingUser.equals(updateReq.getToUpdateUserId())) {
                return new ResponseEntity<>("You cannot update this user.", HttpStatus.BAD_REQUEST);
            }
            String response = userService.updateUserById(requestingUser, updateReq);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/addAddress")
    public ResponseEntity<String> addAddress(@RequestBody AddAddressDto addAddressDto){
        String response;
        try{
            response = userService.addUserAddress(addAddressDto);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response,HttpStatus.OK);
    }
    @GetMapping("/userAddresses/{userId}")
    public ResponseEntity<?> getUserAddresses(@PathVariable UUID userId) throws Exception {
        List<Address> response;
        try{
            response = userService.getAllUserAddress(userId);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response,HttpStatus.OK);
    }
    @PutMapping("/updateAddress/{addressId}")
    public ResponseEntity<String> updateAddress(@PathVariable UUID addressId, @RequestBody AddAddressDto addressDto) {
        try {
            String response = userService.updateAddress(addressId, addressDto);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
    @DeleteMapping("/deleteAddress/{addressId}")
    public ResponseEntity<String> deleteAddress(@PathVariable UUID addressId) {
        try {
            String response = userService.deleteAddress(addressId);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }



}

