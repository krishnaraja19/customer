package com.volvo.customer.service;

import com.volvo.customer.repository.CustomerRepository;
import com.volvo.customer.utility.Constants;
import com.volvo.customer.dto.AddressDTO;
import com.volvo.customer.dto.CustomerDTO;
import com.volvo.customer.model.Address;
import com.volvo.customer.model.Customer;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ModelMapper modelMapper;

    public CustomerDTO saveCustomer(CustomerDTO customer){

        List<AddressDTO> addressList = customer.getAddressList();
        addressList.forEach(address -> address.setActualZip(address.getZipCode()));
        List<Address> addresses= addressList.stream().map(
                address -> modelMapper.map(address,Address.class)).collect(Collectors.toList());


        var customerDetails = Customer.builder()
                        .name(customer.getName())
                        .age(customer.getAge()).registrationDate(Constants.getLocalDateTime())
                        .lastUpdateDate(Constants.getLocalDateTime())
                        .addressList(addresses).build();

        customerRepository.save(customerDetails);

        return customer;
    }

    public CustomerDTO updateCustomer(CustomerDTO customer){
        List<AddressDTO> addressList = customer.getAddressList();
        addressList.forEach(address -> address.setActualZip(address.getZipCode()));
        List<Address> addresses= addressList.stream().map(
                address -> modelMapper.map(address,Address.class)).collect(Collectors.toList());


        var customerDetails = Customer.builder()
                .id(customer.getId())
                .name(customer.getName())
                .age(customer.getAge()).registrationDate(Constants.getLocalDateTime())
                .lastUpdateDate(Constants.getLocalDateTime())
                .addressList(addresses).build();

        customerRepository.save(customerDetails);

        return customer;
    }

    public ResponseEntity deleteCustomer(Long id){
        Optional<Customer> customer = customerRepository.findById(id);
        if(customer.isPresent()) {
            customerRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }else
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
