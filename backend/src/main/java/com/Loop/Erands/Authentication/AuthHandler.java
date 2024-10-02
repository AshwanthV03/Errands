package com.Loop.Erands.Authentication;

import com.Loop.Erands.Models.User;
import com.Loop.Erands.Service.JwtServices.JwtService;
import com.Loop.Erands.Service.UserService.UserService;
import com.Loop.Erands.config.SecurityConfig;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class AuthHandler {
    @Autowired
    UserService userService;
    @Autowired
    SecurityConfig securityConfig;
    @Autowired
    JwtService jwtService;

    public String loginUser(String email, String password, HttpServletResponse response){
        User fetchedUser = userService.findUserByEmailid(email);
        if(fetchedUser == null) throw new BadCredentialsException("Invalid credentials");
        if (!securityConfig.passwordEncoder().matches(password,fetchedUser.getPassword())){
            throw new BadCredentialsException("Invalid credentials");
        }
        Authentication authentication = new UsernamePasswordAuthenticationToken(fetchedUser,null);
        String token = jwtService.generateToken(fetchedUser);
        Cookie cookie = new Cookie("token",token);
        cookie.setMaxAge(60*60*24*2); //seconds * hour* hours * days
        cookie.setPath("/");
        response.addCookie(cookie);
        return token;
    }

}
