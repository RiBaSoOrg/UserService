package com.ribaso.userservice.core.domain.service.impl;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ribaso.userservice.core.domain.model.BillingAddress;
import com.ribaso.userservice.core.domain.model.ShippingAddress;
import com.ribaso.userservice.core.domain.model.User;
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
    public void addUser(@NonNull User user) throws UserAlreadyExistingException {
        Optional<User> userOpt = userRepository.findById(user.getId());
        if (userOpt.isPresent()) {
            throw new UserAlreadyExistingException();
        }
        userRepository.save(user);
    }

    @Override
    public void removeUser(@NonNull UUID userID) throws UnknownUserException {
        Optional<User> userOpt = userRepository.findById(userID);
        if (!userOpt.isPresent()) {
            throw new UnknownUserException();
        }
        
        userRepository.deleteById(userID);
    }

    @Override
    public void updateBillingAddress(@NonNull UUID userID, @NonNull BillingAddress billingAddress)
            throws UnknownUserException {

        User user = getUser(userID);
        user.setBillingAddress(billingAddress);

        userRepository.save(user);
    }

    @Override
    public void updateShippingAddress(@NonNull UUID userID, @NonNull ShippingAddress shippingAddress)
            throws UnknownUserException {

        User user = getUser(userID);
        user.setShippingAddress(shippingAddress);

        userRepository.save(user);
    }

    @Override
    public void updatePersonData(@NonNull UUID userID, String name, String lastname)
            throws UnknownUserException, IllegalArgumentException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updatePersonData'");
    }

}
