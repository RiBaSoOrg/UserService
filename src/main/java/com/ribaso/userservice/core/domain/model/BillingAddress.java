package com.ribaso.userservice.core.domain.model;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
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
