package com.Loop.Erands.Service.UserService;

import com.Loop.Erands.DTO.AddressDto.AddAddressDto;
import com.Loop.Erands.DTO.UserDto.NewUserDto;
import com.Loop.Erands.DTO.UserDto.UpdateDto;
import com.Loop.Erands.Models.Address;
import com.Loop.Erands.Models.User;

import java.util.List;
import java.util.UUID;

public interface UserService {

    /**
     * Creates a new user based on the provided NewUserDto.
     * @param newUserDto The DTO containing user details.
     * @return A string indicating the result of the user creation.
     * @throws Exception If there is an issue during user creation.
     */
    public String createNewUser(NewUserDto newUserDto) throws Exception;

    /**
     * Deletes a user identified by the provided user ID.
     * @param userId The UUID of the user to delete.
     * @return A string indicating the result of the deletion.
     */
    public String deleteUserById(UUID userId);

    /**
     * Finds a user by their unique user ID.
     * @param userId The UUID of the user to find.
     * @return The User object if found.
     * @throws Exception If the user is not found or if there is an error.
     */
    public User findUserById(UUID userId) throws Exception;

    /**
     * Updates the user information based on the provided user ID and UpdateDto.
     * @param userId The UUID of the user to update.
     * @param updateReq The DTO containing the updated user information.
     * @return A string indicating the result of the update operation.
     * @throws Exception If there is an issue during the update.
     */
    public String updateUserById(UUID userId, UpdateDto updateReq) throws Exception;

    /**
     * Finds a user by their email address.
     * @param email The email address of the user to find.
     * @return The User object if found, or null if not found.
     */
    public User findUserByEmailid(String email);

    /**
     * Retrieves all addresses associated with a user identified by their user ID.
     * @param userId The UUID of the user whose addresses are to be retrieved.
     * @return A list of Address objects associated with the user.
     * @throws Exception If there is an issue during retrieval.
     */
    public List<Address> getAllUserAddress(UUID userId) throws Exception;

    /**
     * Adds a new address for a user based on the provided AddAddressDto.
     * @param addAddressDto The DTO containing the address details to be added.
     * @return A string indicating the result of the address addition.
     * @throws Exception If there is an issue during address addition.
     */
    String addUserAddress(AddAddressDto addAddressDto) throws Exception;

    /**
     * Updates an existing address identified by the address ID.
     * @param addressId The UUID of the address to update.
     * @param addressDto The DTO containing the updated address information.
     * @return A string indicating the result of the update operation.
     * @throws Exception If there is an issue during the update.
     */
    String updateAddress(UUID addressId, AddAddressDto addressDto) throws Exception;

    /**
     * Deletes an address identified by the address ID.
     * @param addressId The UUID of the address to delete.
     * @return A string indicating the result of the deletion operation.
     * @throws Exception If there is an issue during deletion.
     */
    String deleteAddress(UUID addressId) throws Exception;
}
