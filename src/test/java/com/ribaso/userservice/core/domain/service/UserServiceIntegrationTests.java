package com.ribaso.userservice.core.domain.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;

import com.ribaso.userservice.core.domain.model.BillingAddress;
import com.ribaso.userservice.core.domain.model.ShippingAddress;
import com.ribaso.userservice.core.domain.model.User;
import com.ribaso.userservice.core.domain.service.exceptions.UnknownUserException;
import com.ribaso.userservice.core.domain.service.exceptions.UserAlreadyExistingException;
import com.ribaso.userservice.core.domain.service.impl.UserServiceImpl;
import com.ribaso.userservice.core.domain.service.interfaces.UserService;
import com.ribaso.userservice.core.domain.service.interfaces.UserRepository;

@SpringBootTest
public class UserServiceIntegrationTests {

    @TestConfiguration
    static class UserServiceTestContextConfiguration {
        
        @Bean
        public UserService userService() {
            return new UserServiceImpl();
        }
    }

    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    private UUID aliceUUID = UUID.randomUUID();
    private User alice;

    @BeforeEach
    public void setUp() {

        this.alice = new User(
            this.aliceUUID,
            "Alice",
            "Astar",
            new BillingAddress(),
            new ShippingAddress()
        );

        Mockito.when(userRepository.findById(aliceUUID)).thenReturn(Optional.of(alice));
    }

    //#region getUser

    ///////////////////////////////////////////////////////////
    ////    GET USER    ///////////////////////////////////////
    ///////////////////////////////////////////////////////////

    @Test
    public void getUser_validUUID_returnsCorrectUser() throws UnknownUserException {
        User expected = this.alice;
        User actual = userService.getUser(aliceUUID);

        assertEquals(expected, actual);
    }

    @Test
    public void getUser_invalidUUID_throwsUnkownUserException() {
        Mockito.when(userRepository.findById(any())).thenReturn(Optional.empty());

        UUID invalidUUID = UUID.randomUUID();

        assertNotEquals(aliceUUID, invalidUUID);
        assertThrows(UnknownUserException.class, () -> userService.getUser(invalidUUID));
    }

    //#endregion

    //#region addUser

    ///////////////////////////////////////////////////////////
    ////    ADD USER    ///////////////////////////////////////
    ///////////////////////////////////////////////////////////

    @Test
    public void addUser_userUnregistered_userAdded() throws UserAlreadyExistingException {
        User bob = new User(UUID.randomUUID());
        Mockito.when(userRepository.findById(bob.getId())).thenReturn(Optional.empty());

        assertDoesNotThrow(() -> userService.addUser(bob));
        Mockito.verify(userRepository, times(1)).save(bob);
    }

    @Test
    public void addUser_userRegistered_throwsUserAlreadyExistingException() {
        assertThrows(UserAlreadyExistingException.class, () -> userService.addUser(alice));
    }

    //#endregion

    //#region removeUser

    ///////////////////////////////////////////////////////////
    ////    REMOVE USER    ////////////////////////////////////
    ///////////////////////////////////////////////////////////

    @Test
    public void removeUser_userRegistered_userRemoved() {
        assertDoesNotThrow(() -> userService.removeUser(aliceUUID));
        Mockito.verify(userRepository, times(1)).deleteById(aliceUUID);
    }

    @Test
    public void removeUser_userUnregistered_throwsUnknownUserException() {
        User bob = new User(UUID.randomUUID());

        assertThrows(UnknownUserException.class, () -> userService.removeUser(bob.getId()));
    }

    //#endregion

    //#region updateBillingAddress

    ///////////////////////////////////////////////////////////
    ////    UPDATE BILLING ADDRESS    /////////////////////////
    ///////////////////////////////////////////////////////////

    @Test
    public void updateBillingAddress_userRegistered_addressUpdated() throws UnknownUserException {
        BillingAddress address = new BillingAddress();
        String firstname = "Alice_Test";
        address.setFirstname(firstname);

        userService.updateBillingAddress(aliceUUID, address);

        Mockito.verify(userRepository, times(1)).save(any());
    }

    @Test
    public void updateBillingAddress_userUnregistered_throwsUnknownUserException() {
        UUID invalidUser = UUID.randomUUID();
        BillingAddress address = new BillingAddress();
        address.setFirstname("Bob");

        assertThrows(UnknownUserException.class, () -> userService.updateBillingAddress(invalidUser, address));
    }

    //#endregion

    //#region updateShippingAddress

    ///////////////////////////////////////////////////////////
    ////    UPDATE SHIPPING ADDRESS    ////////////////////////
    ///////////////////////////////////////////////////////////

    @Test
    public void updateShippingAddress_userRegistered_addressUpdated() throws UnknownUserException {
        ShippingAddress address = new ShippingAddress();
        String firstname = "Alice_Test";
        address.setFirstname(firstname);

        userService.updateShippingAddress(aliceUUID, address);

        Mockito.verify(userRepository, times(1)).save(any());
    }

    @Test
    public void updateShippingAddress_userUnregistered_throwsUnknownUserException() {
        UUID invalidUser = UUID.randomUUID();
        ShippingAddress address = new ShippingAddress();
        address.setFirstname("Bob");

        assertThrows(UnknownUserException.class, () -> userService.updateShippingAddress(invalidUser, address));
    }

    //#endregion

    //#region updatePersonData

    ///////////////////////////////////////////////////////////
    ////    UPDATE PERSON DATA    /////////////////////////////
    ///////////////////////////////////////////////////////////

    @Test
    public void updatePersonData_userRegistered_addressUpdated() throws UnknownUserException {
        String firstname = "Alice_Test";
        String lastname = "AStar_Test";

        userService.updatePersonData(aliceUUID, firstname, lastname);

        Mockito.verify(userRepository, times(1)).save(any());
    }

    @Test
    public void updatePersonData_userUnregistered_throwsUnknownUserException() {
        UUID invalidUser = UUID.randomUUID();
        String firstname = "Bob_Test";
        String lastname = "Bibar_Test";

        assertThrows(UnknownUserException.class, () -> userService.updatePersonData(invalidUser, firstname, lastname));
    }

    //#endregion
}
