package com.ribaso.userservice.core.domain.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import com.ribaso.userservice.core.domain.model.BillingAddress;
import com.ribaso.userservice.core.domain.model.ShippingAddress;
import com.ribaso.userservice.core.domain.model.User;
import com.ribaso.userservice.core.domain.service.exceptions.UnknownUserException;
import com.ribaso.userservice.core.domain.service.impl.UserServiceImpl;
import com.ribaso.userservice.core.domain.service.interfaces.UserRepository;
import com.ribaso.userservice.core.domain.service.interfaces.UserService;

@DataJpaTest(properties = {
    "spring.datasource.url=jdbc:h2:mem:testdb",
    "spring.jpa.hibernate.ddl-auto=create-drop"
})
public class UserServiceH2IntegrationTests {

    @TestConfiguration
    static class UserServiceTestContextConfiguration {
        
        @Bean
        public UserService userService() {
            return new UserServiceImpl();
        }
    }

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    private UUID aliceUUID = UUID.randomUUID();
    private User alice;

    @BeforeEach
    public void test() throws UnknownUserException {
        alice = new User(
            aliceUUID,
            "Alice",
            "AStar",
            new BillingAddress(),
            new ShippingAddress());

        entityManager.persist(alice);
        entityManager.flush();
    }

    @Test
    public void getUser_userExists_returnsUser() throws UnknownUserException {
        User expected = alice;
        User actual = userService.getUser(aliceUUID);

        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getFirstname(), actual.getFirstname());
        assertEquals(expected.getLastname(), actual.getLastname());
    }

    @Test
    public void getUser_userUnregistered_throwsUnknownUserException() {
        UUID invalidUser = UUID.randomUUID();

        assertThrows(UnknownUserException.class, () -> userService.getUser(invalidUser));
    }
}
