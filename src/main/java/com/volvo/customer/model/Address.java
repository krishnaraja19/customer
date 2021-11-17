package com.volvo.customer.model;

import com.volvo.customer.mask.MaskUtil;
import com.volvo.customer.mask.MaskZipCode;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIdentityInfo(generator= ObjectIdGenerators.IntSequenceGenerator.class)
public class Address {

    @Id
    @GeneratedValue( strategy = GenerationType.AUTO)
    private Long addressId;
    private String houseNumber;
    private String streetNumber;
    private String streetName;
    private String city;
    private String state;
    private String country;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String actualZip;
    @MaskZipCode
    private String zipCode;

    @ManyToMany( mappedBy = "addressList")
    private List<Customer> customerList;


    public void setZipCode(String zipCode){
        this.zipCode = MaskUtil.maskStringField(Address.class,"zipCode",zipCode);
    }
}
