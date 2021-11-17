package com.volvo.customer.model;

import java.util.ArrayList;
import java.util.List;

public class AddressList {
    private List<Address> addressList;

    public List<Address> getAddressList() {
        if(addressList == null) {
            addressList = new ArrayList<>();
        }
        return addressList;
    }

    public void setAddressList(List<Address> addressList) {
        this.addressList = addressList;
    }
}
