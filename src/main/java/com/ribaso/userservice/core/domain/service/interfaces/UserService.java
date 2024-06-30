package com.ribaso.userservice.core.domain.service.interfaces;

import java.util.UUID;

import com.ribaso.userservice.core.domain.model.BillingAddress;
import com.ribaso.userservice.core.domain.model.ShippingAddress;
import com.ribaso.userservice.core.domain.model.User;
import com.ribaso.userservice.core.domain.service.exceptions.InvalidEmailException;
import com.ribaso.userservice.core.domain.service.exceptions.UnknownUserException;
import com.ribaso.userservice.core.domain.service.exceptions.UserAlreadyExistingException;

import lombok.NonNull;

/**
 * This services contains and manages user data, except login credentials.
 */
public interface UserService {

    /**
     * Get the user.
     * @param userID unique UUID.
     * @return user object containing user data.
     * @throws UnknownUserException
     */
    public User getUser(@NonNull UUID userID) throws UnknownUserException;

    /**
     * Add/Register a new user.
     * @param userID unique UUID for new user.
     * @param mail email address of the user.
     * @return true if user successfully added, false otherwise.
     * @throws UserAlreadyExistingException
     * @throws InvalidEmailException
     */
    public boolean addUser(
            @NonNull UUID userID,
            @NonNull String mail) throws UserAlreadyExistingException, InvalidEmailException;

    /**
     * Remove an existing user.
     * @param userID unique UUID of user to be removed.
     * @return true if user successfully removed, false otherwise.
     * @throws UnknownUserException
     */
    public boolean removeUser(@NonNull UUID userID) throws UnknownUserException;

    /**
     * Update the billing address data.
     * @param userID unique UUID to identify user.
     * @param billingAddress data object containing the data to update.
     * @return true if billing address successfully updated, false otherwise.
     * @throws UnknownUserException
     */
    public boolean updateBillingAddress(
            @NonNull UUID userID,
            @NonNull BillingAddress billingAddress) throws UnknownUserException;

    /**
     * Update the shipping address data.
     * @param userID unique UUID to identify user.
     * @param shippingAddress data object containing the data to update.
     * @return true if shipping address successfully updated, false otherwise.
     * @throws UnknownUserException
     */
    public boolean updateShippingAddress(
            @NonNull UUID userID, 
            @NonNull ShippingAddress shippingAddress) throws UnknownUserException;

    /**
     * Update the personal information of a user.
     * Arguments with null value will not be updated.
     * @param userID unique UUID to identify user.
     * @param name a valid name.
     * @param lastname a valid name.
     * @param mail email address of person.
     * @return true if personal data successfully updated, false otherwise.
     * @throws UnknownUserException
     * @throws IllegalArgumentException when the names had illegal characters.
     */
    public boolean updatePersonData(
            @NonNull UUID userID,
            String name,
            String lastname,
            String mail) throws UnknownUserException, IllegalArgumentException, InvalidEmailException;

}
