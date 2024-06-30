package com.ribaso.userservice.core.domain.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;

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
            "alice@gmail.com",
            new BillingAddress(),
            new ShippingAddress()
        );

        Mockito.when(userRepository.findById(aliceUUID)).thenReturn(Optional.of(alice));
    }

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
}
