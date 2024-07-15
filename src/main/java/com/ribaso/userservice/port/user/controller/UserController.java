package com.ribaso.userservice.port.user.controller;

import java.util.HashMap;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.ribaso.userservice.core.domain.model.BillingAddress;
import com.ribaso.userservice.core.domain.model.ShippingAddress;
import com.ribaso.userservice.core.domain.model.User;
import com.ribaso.userservice.core.domain.service.exceptions.UnknownUserException;
import com.ribaso.userservice.core.domain.service.exceptions.UserAlreadyExistingException;
import com.ribaso.userservice.core.domain.service.interfaces.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping()
    public User getUser(Authentication authentication) throws UnknownUserException {
        if (authentication != null && authentication.getPrincipal() instanceof DefaultOidcUser) {
            DefaultOidcUser oidcUser = (DefaultOidcUser) authentication.getPrincipal();
            String userID = oidcUser.getClaim("sub");
            UUID uuid = UUID.fromString(userID);
            User user = userService.getUser(uuid);
            return user;
        } 

        return null;
    }

    @PostMapping
    public @ResponseBody void addUser(@RequestBody User user) throws UserAlreadyExistingException {
        userService.addUser(user);
    }

    @DeleteMapping("/{userID}")
    public @ResponseBody void removeUser(@PathVariable UUID userID) throws UnknownUserException {
        userService.removeUser(userID);
    }

    @PutMapping("/{userID}/billingData")
    public @ResponseBody void updateBillingAddress(
            @PathVariable UUID userID,
            @RequestBody BillingAddress address) throws UnknownUserException {

        userService.updateBillingAddress(userID, address);
    }

    @PutMapping("/{userID}/shippingData")
    public @ResponseBody void updateShippingAddress(
            @PathVariable UUID userID,
            @RequestBody ShippingAddress address) throws UnknownUserException {
        
                userService.updateShippingAddress(userID, address);
    }

    @PutMapping("/{userID}/userData")
    public @ResponseBody void updateUserData(
            @PathVariable UUID userID,
            @RequestParam String firstname,
            @RequestParam String lastname) throws UnknownUserException {
                
        userService.updatePersonData(userID, firstname, lastname);
    }
}
