package com.volvo.customer.model;

import com.volvo.customer.dto.AddressDTO;

import java.util.ArrayList;
import java.util.List;

public class AddressDTOList {
    private List<AddressDTO> addressList;

    public List<AddressDTO> getAddressList() {
        if(addressList == null) {
            addressList = new ArrayList<>();
        }
        return addressList;
    }

    public void setAddressList(List<AddressDTO> addressList) {
        this.addressList = addressList;
    }
}
