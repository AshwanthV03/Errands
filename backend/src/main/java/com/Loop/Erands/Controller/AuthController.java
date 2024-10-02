package com.Loop.Erands.Controller;

import com.Loop.Erands.Authentication.AuthHandler;
import com.Loop.Erands.DTO.UserDto.LoginDto;
import com.Loop.Erands.DTO.UserDto.LoginResponseDto;
import com.Loop.Erands.DTO.UserDto.NewUserDto;
import com.Loop.Erands.Models.User;
import com.Loop.Erands.Service.JwtServices.JwtService;
import com.Loop.Erands.Service.UserService.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.UUID;

@Controller
@RequestMapping("/authenticate")
public class AuthController {
    @Autowired
    UserService userService;
    @Autowired
    AuthHandler authHandler;
    @Autowired
    JwtService jwtService;

    @PostMapping(path = "/login")
    public ResponseEntity<?> logIn(@RequestBody LoginDto loginDto, HttpServletResponse response) {
        try {
            // Attempt to authenticate the user
            String token = authHandler.loginUser(loginDto.getEmail(), loginDto.getPassword(),response);
            UUID userId = UUID.fromString(jwtService.getUserIdFromToken(token));

            // Fetch the user details
            User user = userService.findUserById(userId);
            if (user == null) {
                return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
            }

            // Prepare the response DTO
            LoginResponseDto loginResponseDto = new LoginResponseDto();
            loginResponseDto.setUserId(user.getUserId());
            loginResponseDto.setUserName(user.getFirstName() + " " + user.getLastName());
            loginResponseDto.setUserCart(user.getCarts().getCartItems()); // Corrected field name to `cart`
            loginResponseDto.setUserOrder(user.getOrders());
            loginResponseDto.setToken(token);

            // Create a cookie with the authentication token
            Cookie cookie = new Cookie("authToken", token);
            cookie.setPath("/");
            cookie.setMaxAge(3600 * 24 * 2); // 2 days

            // Add the cookie to the response
            response.addCookie(cookie);

            // Return the response with the DTO and OK status
            return new ResponseEntity<>(loginResponseDto, HttpStatus.OK);

        } catch (BadCredentialsException error) {
            // Handle authentication failure (invalid credentials)
            return new ResponseEntity<>("Invalid email or password", HttpStatus.UNAUTHORIZED);
        } catch (Exception ex) {
            // Handle other exceptions (e.g., database issues, unexpected errors)
            return new ResponseEntity<>("An error occurred during login. Please try again later." + ex.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(path = "/register")
    public ResponseEntity<String> Register(@RequestBody NewUserDto newUserDto) throws Exception {
        String response = userService.createNewUser(newUserDto);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
