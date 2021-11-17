package com.volvo.customer.model;

import java.util.ArrayList;
import java.util.List;

public class CustomersList {
    private List<Customer> customersList;

    public List<Customer> getCustomersList() {
        if(customersList == null) {
            customersList = new ArrayList<>();
        }
        return customersList;
    }

    public void setCustomersList(List<Customer> customersList) {
        this.customersList = customersList;
    }
}
