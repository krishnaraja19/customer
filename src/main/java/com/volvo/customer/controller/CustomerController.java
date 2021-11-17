package com.volvo.customer.controller;

import com.volvo.customer.repository.CustomerRepository;
import com.volvo.customer.service.CustomerService;
import com.volvo.customer.utility.Constants;
import com.volvo.customer.dto.CustomerDTO;
import com.volvo.customer.model.Customer;
import com.volvo.customer.model.CustomersList;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/1.0")
public class CustomerController {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    CustomerService customerService;

    @Autowired
    CustomerRepository customerRepository;

    @PostMapping("/customers")
    public ResponseEntity<CustomerDTO> createCustomer(@RequestBody CustomerDTO customerDTO){
        try{
            CustomerDTO customer = customerService.saveCustomer(customerDTO);
            return new ResponseEntity( customerRepository.findByName(customer.getName()), HttpStatus.CREATED);
        }catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
    @PutMapping("/updateCustomer/{id}")
    public ResponseEntity<CustomerDTO> updateCustomer(@PathVariable("id") long id, @RequestBody CustomerDTO customerDTO){
        Optional<Customer> customerData = customerRepository.findById(id);
        if(customerData.isPresent()){
            CustomerDTO customer = modelMapper.map(customerData,CustomerDTO.class);
            customer.setId(customerData.get().getId());
            customer.setName(customerDTO.getName());
            customer.setAge(customerDTO.getAge());
            customer.setLastUpdateDate(Constants.getLocalDateTime());
            customer.setAddressList(customerDTO.getAddressList());
            customerService.updateCustomer(customer);
            return new ResponseEntity( customerRepository.findById(customer.getId()).get(), HttpStatus.OK);

        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/findCustomerById/{id}")
    public ResponseEntity<CustomerDTO> findCustomerById(@PathVariable("id") long id){
        Optional<Customer> customerData = customerRepository.findById(id);
        if(customerData.isPresent()){
            return new ResponseEntity(customerData.get(), HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/findCustomerByName/{name}")
    public ResponseEntity<CustomersList> findCustomerByName(@PathVariable("name") String name){
        List<Customer> customerList = customerRepository.findByName(name);
        CustomersList customersList = new CustomersList();
        customersList.setCustomersList(customerList);
        if(customerList.size()>0){
            return new ResponseEntity(customersList, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/findCustomerByZipCode/{zipCode}")
    public ResponseEntity<CustomersList> findCustomerByZipCode(@PathVariable("zipCode") String zipCode){
        List<Customer> customerList = customerRepository.findByAddressList_ActualZip(zipCode);
        CustomersList customersList = new CustomersList();
        customersList.setCustomersList(customerList);
        if(customerList.size()>0){
            return new ResponseEntity(customersList, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/findAllCustomer")
    public ResponseEntity<CustomersList> findCustomerById(){
        List<Customer> customerList = customerRepository.findAll();
        CustomersList customersList = new CustomersList();
        customersList.setCustomersList(customerList);
        if(customerList.size()>0){
            return new ResponseEntity(customersList, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @DeleteMapping("/customer/{id}")
    public ResponseEntity<HttpStatus> deleteCustomer(@PathVariable("id") long id) {

            return customerService.deleteCustomer(id);

    }



}
