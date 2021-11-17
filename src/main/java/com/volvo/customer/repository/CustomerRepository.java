package com.volvo.customer.repository;

import com.volvo.customer.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer,Long> {

    List<Customer> findByName(String name);


    List<Customer> findByAddressList_ActualZip(String ActualZip);


}
