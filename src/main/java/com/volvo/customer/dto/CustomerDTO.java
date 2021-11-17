package com.volvo.customer.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class CustomerDTO {

    private Long id;
    private String name;
    private int age;
    private LocalDateTime registrationDate;
    private LocalDateTime lastUpdateDate;
    private List<AddressDTO> addressList;

}
