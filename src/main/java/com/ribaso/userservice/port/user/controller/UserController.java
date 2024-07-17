package com.ribaso.userservice.port.user.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ribaso.userservice.core.domain.model.BillingAddress;
import com.ribaso.userservice.core.domain.model.ShippingAddress;
import com.ribaso.userservice.core.domain.model.User;
import com.ribaso.userservice.core.domain.service.exceptions.UnknownUserException;
import com.ribaso.userservice.core.domain.service.exceptions.UserAlreadyExistingException;
import com.ribaso.userservice.core.domain.service.interfaces.UserService;
import com.ribaso.userservice.port.helpers.UUIDExtractor;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public void login() {
        return;
    }

    @GetMapping
    public User getUser(Authentication authentication) throws UnknownUserException {
        UUID uuid = UUIDExtractor.extractFromAuthentication(authentication);
        User user = userService.getUser(uuid);
        return user;
    }

    @PostMapping
    public @ResponseBody void addUser(Authentication authentication, @RequestBody User user) throws UserAlreadyExistingException {
        UUID uuid = UUIDExtractor.extractFromAuthentication(authentication);
        user.setId(uuid);
        userService.addUser(user);
    }

    @DeleteMapping("/{userID}")
    public @ResponseBody void removeUser(@PathVariable UUID userID) throws UnknownUserException {
        userService.removeUser(userID);
    }

    @DeleteMapping
    public @ResponseBody void removeUser(Authentication authentication) throws UnknownUserException {
        UUID uuid = UUIDExtractor.extractFromAuthentication(authentication);
        userService.removeUser(uuid);
    }

    @PutMapping("/billingData")
    public @ResponseBody void updateBillingAddress(
            Authentication authentication,
            @RequestBody BillingAddress address) throws UnknownUserException {

        UUID uuid = UUIDExtractor.extractFromAuthentication(authentication);
        userService.updateBillingAddress(uuid, address);
    }

    @PutMapping("/shippingData")
    public @ResponseBody void updateShippingAddress(
            Authentication authentication,
            @RequestBody ShippingAddress address) throws UnknownUserException {
                
        UUID uuid = UUIDExtractor.extractFromAuthentication(authentication);
        userService.updateShippingAddress(uuid, address);
    }

    @PutMapping("/userData")
    public @ResponseBody void updateUserData(
            Authentication authentication,
            @RequestParam String firstname,
            @RequestParam String lastname) throws UnknownUserException {
        
        UUID uuid = UUIDExtractor.extractFromAuthentication(authentication);   
        userService.updatePersonData(uuid, firstname, lastname);
    }
}
