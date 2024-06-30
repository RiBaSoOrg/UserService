package com.ribaso.userservice.core.domain.service.impl;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ribaso.userservice.core.domain.model.BillingAddress;
import com.ribaso.userservice.core.domain.model.ShippingAddress;
import com.ribaso.userservice.core.domain.model.User;
import com.ribaso.userservice.core.domain.service.exceptions.InvalidEmailException;
import com.ribaso.userservice.core.domain.service.exceptions.UnknownUserException;
import com.ribaso.userservice.core.domain.service.exceptions.UserAlreadyExistingException;
import com.ribaso.userservice.core.domain.service.interfaces.UserRepository;
import com.ribaso.userservice.core.domain.service.interfaces.UserService;

import lombok.NonNull;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User getUser(@NonNull UUID userID) throws UnknownUserException {
        Optional<User> userOpt = userRepository.findById(userID);
        if (!userOpt.isPresent()) {
            throw new UnknownUserException();
        }
        return userOpt.get();
    }

    @Override
    public boolean addUser(@NonNull UUID userID, @NonNull String mail)
            throws UserAlreadyExistingException, InvalidEmailException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'addUser'");
    }

    @Override
    public boolean removeUser(@NonNull UUID userID) throws UnknownUserException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'removeUser'");
    }

    @Override
    public boolean updateBillingAddress(@NonNull UUID userID, @NonNull BillingAddress billingAddress)
            throws UnknownUserException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateBillingAddress'");
    }

    @Override
    public boolean updateShippingAddress(@NonNull UUID userID, @NonNull ShippingAddress shippingAddress)
            throws UnknownUserException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateShippingAddress'");
    }

    @Override
    public boolean updatePersonData(@NonNull UUID userID, String name, String lastname, String mail)
            throws UnknownUserException, IllegalArgumentException, InvalidEmailException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updatePersonData'");
    }

}
