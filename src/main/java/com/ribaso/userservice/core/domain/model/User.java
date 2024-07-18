package com.ribaso.userservice.core.domain.model;

import java.util.UUID;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {
    
    @Id
    @Column(name = "USER_ID")
    private UUID id;

    private String firstname;

    private String lastname;

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "firstname", column = @Column(name = "billing_first_name")),
        @AttributeOverride(name = "lastname", column = @Column(name = "billing_last_name")),
        @AttributeOverride(name = "street", column = @Column(name = "billing_street")),
        @AttributeOverride(name = "houseNumber", column = @Column(name = "billing_house_number")),
        @AttributeOverride(name = "postalCode", column = @Column(name = "billing_postal_code")),
        @AttributeOverride(name = "state", column = @Column(name = "billing_state")),
        @AttributeOverride(name = "city", column = @Column(name = "billing_city"))
    })
    private BillingAddress billingAddress;

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "firstname", column = @Column(name = "shipping_first_name")),
        @AttributeOverride(name = "lastname", column = @Column(name = "shipping_last_name")),
        @AttributeOverride(name = "street", column = @Column(name = "shipping_street")),
        @AttributeOverride(name = "houseNumber", column = @Column(name = "shipping_house_number")),
        @AttributeOverride(name = "postalCode", column = @Column(name = "shipping_postal_code")),
        @AttributeOverride(name = "state", column = @Column(name = "shipping_state")),
        @AttributeOverride(name = "city", column = @Column(name = "shipping_city"))
    })
    private ShippingAddress shippingAddress;

    public User(@NonNull UUID id) {
        this.id = id;
    }
}
