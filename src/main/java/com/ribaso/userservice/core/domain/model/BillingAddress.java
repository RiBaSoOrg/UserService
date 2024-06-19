package com.ribaso.userservice.core.domain.model;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@Embeddable
public class BillingAddress {

    private String firstname;

    private String lastname;

    private String street;

    private String houseNumber;

    private String postalCode;

    private String state;

    private String city;
}
