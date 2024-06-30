package com.ribaso.userservice.core.domain.service.interfaces;

import java.util.UUID;

import com.ribaso.userservice.core.domain.model.BillingAddress;
import com.ribaso.userservice.core.domain.model.ShippingAddress;
import com.ribaso.userservice.core.domain.model.User;
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
     * @param user can not be registered before.
     * @throws UserAlreadyExistingException
     */
    public void addUser(@NonNull User user) throws UserAlreadyExistingException;

    /**
     * Remove an existing user.
     * @param userID unique UUID of user to be removed.
     * @throws UnknownUserException
     */
    public void removeUser(@NonNull UUID userID) throws UnknownUserException;

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
     * @return true if personal data successfully updated, false otherwise.
     * @throws UnknownUserException
     * @throws IllegalArgumentException when the names had illegal characters.
     */
    public boolean updatePersonData(
            @NonNull UUID userID,
            String name,
            String lastname) throws UnknownUserException, IllegalArgumentException;

}
