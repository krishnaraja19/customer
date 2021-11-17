package com.volvo.customer.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIdentityInfo(generator= ObjectIdGenerators.IntSequenceGenerator.class)
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Document_Id")
    private Long id;

    private String name;
    private int age;

    private LocalDateTime registrationDate;

    private LocalDateTime lastUpdateDate;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable( name= "customers_address_mapping",
        joinColumns = @JoinColumn( name = "customer_document_id", referencedColumnName = "Document_Id"),
        inverseJoinColumns = @JoinColumn( name = "customer_address_id", referencedColumnName = "addressId")
        )
    private List<Address> addressList;


}
