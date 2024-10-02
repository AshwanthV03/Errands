package com.Loop.Erands.Service.UserService;

import com.Loop.Erands.DTO.AddressDto.AddAddressDto;
import com.Loop.Erands.DTO.UserDto.NewUserDto;
import com.Loop.Erands.DTO.UserDto.UpdateDto;
import com.Loop.Erands.Models.Address;
import com.Loop.Erands.Models.Roles;
import com.Loop.Erands.Models.User;
import com.Loop.Erands.Repository.AddressRepository;
import com.Loop.Erands.Repository.UserRepository;
import com.Loop.Erands.config.SecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private SecurityConfig securityConfig;

    @Override
    @Transactional
    public String createNewUser(NewUserDto newUserDto) {
        String hashedPassword = securityConfig.passwordEncoder().encode(newUserDto.getPassword());

        User user = mapNewUserDtoToUser(newUserDto, hashedPassword);

        try {
            userRepository.save(user);
        } catch (Exception e) {
            logger.error("Error creating user:", e.getMessage());
            throw new RuntimeException("Error creating user", e);
        }

        return "User created successfully";
    }

    @Override
    @Transactional
    public String deleteUserById(UUID userId) {
        try {
            userRepository.deleteById(userId);
        } catch (Exception e) {
            logger.error("Error deleting user with ID {}: {}", userId, e.getMessage());
            throw new RuntimeException("Error deleting user", e);
        }
        return "User deleted successfully";
    }

    @Override
    @Transactional(readOnly = true)
    public User findUserById(UUID userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));
    }

    @Override
    @Transactional
    public String updateUserById(UUID userId, UpdateDto updateReq) {
        User existingUser = findUserById(userId);

        updateUserFields(existingUser, updateReq);

        try {
            userRepository.save(existingUser);
        } catch (Exception e) {
            logger.error("Error updating user with ID {}: {}", userId, e.getMessage());
            throw new RuntimeException("Error updating user", e);
        }

        return "User updated successfully";
    }

    @Override
    @Transactional(readOnly = true)
    public User findUserByEmailid(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }

    @Override
    public List<Address> getAllUserAddress(UUID userId) throws Exception {
        List<Address> userAddress = addressRepository.findAllAddressByUserId(userId);
        if(userAddress.isEmpty()) throw new Exception("User does not have an Address!");
        return userAddress;
    }

    @Override
    public String addUserAddress(AddAddressDto addAddressDto ) throws Exception {
        User user = findUserById(addAddressDto.getUserId());

        Address address = new Address(user,addAddressDto.getStreet(),addAddressDto.getCity(),addAddressDto.getState(),addAddressDto.getZipCode());
        user.addAddress(address);

        try {
            userRepository.save(user);
        }catch (Exception e){
            throw new Exception(e);
        }
        return "Address added successfully";
    }
    @Override
    public String updateAddress(UUID addressId, AddAddressDto addressDto) throws Exception {
        Address address = addressRepository.findById(addressId)
                .orElseThrow(() -> new Exception("Address not found"));

        Optional.ofNullable(addressDto.getCity()).ifPresent(address::setCity);
        Optional.ofNullable(addressDto.getStreet()).ifPresent(address::setStreet);
        Optional.ofNullable(addressDto.getState()).ifPresent(address::setState);
        Optional.ofNullable(addressDto.getZipCode()).ifPresent(address::setZipCode);

        try {
            addressRepository.save(address);
        } catch (DataAccessException e) {
            throw new Exception("Error updating address: " + e.getMessage(), e);
        }

        return "Updated address successfully";
    }
    @Override
    public String deleteAddress(UUID addressId) throws Exception {
        try {
            addressRepository.deleteAddressById(addressId);
        }catch (Exception e){
            throw new Exception(e);
        }
        return "Address deleted";
    }

    /**
     -------------------------------------------------------------------------------------------------------------------
        HELPER METHODS
     -------------------------------------------------------------------------------------------------------------------
     **/

    private User mapNewUserDtoToUser(NewUserDto newUserDto, String hashedPassword) {
        User user = new User();
        user.setFirstName(newUserDto.getFirstName());
        user.setLastName(newUserDto.getLastName());
        user.setEmail(newUserDto.getEmail());
        user.setPhoneNumber(newUserDto.getPhNo());
        user.setDob(newUserDto.getBirthDate());
        user.setPassword(hashedPassword);
        user.setRole(Roles.SELLER);
        return user;
    }

    private void updateUserFields(User user, UpdateDto updateReq) {
        if (updateReq.getFirstName() != null) user.setFirstName(updateReq.getFirstName());
        if (updateReq.getLastName() != null) user.setLastName(updateReq.getLastName());
        if (updateReq.getPassword() != null) {
            String newHashedPassword = securityConfig.passwordEncoder().encode(updateReq.getPassword());
            user.setPassword(newHashedPassword);
        }
    }

}
