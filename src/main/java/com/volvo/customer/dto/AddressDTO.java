package com.volvo.customer.dto;

import com.volvo.customer.model.Customer;
import lombok.Data;

import java.util.List;

@Data
public class AddressDTO {
    private Long addressId;
    private String houseNumber;
    private String streetNumber;
    private String streetName;
    private String city;
    private String state;
    private String country;
    private String actualZip;
    private String zipCode;

    private List<Customer> customerList;

}
