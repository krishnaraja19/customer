package com.volvo.customer;

import com.volvo.customer.model.Address;
import com.volvo.customer.repository.CustomerRepository;
import com.volvo.customer.utility.Constants;
import com.volvo.customer.dto.CustomerDTO;
import com.volvo.customer.model.Customer;
import com.volvo.customer.model.CustomersList;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CustomerApplicationTests {
	private static final String API_1_0_CUSTOMERS = "/api/1.0/customers";
	private static final String API_1_0_UPDATE_CUSTOMER = "/api/1.0/updateCustomer";
	private static final String API_1_0_DELETE_CUSTOMER = "/api/1.0/customer";
	private static final String API_1_0_FIND_CUSTOMER_BY_NAME = "/api/1.0/findCustomerByName";
	private static final String API_1_0_FIND_ALL_CUSTOMER = "/api/1.0/findAllCustomer";
	private static final String API_1_0_FIND_CUSTOMER_BY_ZIPCODE = "/api/1.0/findCustomerByZipCode";

	@Autowired
	TestRestTemplate testRestTemplate;

	@Autowired
	CustomerRepository customerRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Test
	@Order(1)
	public void postCustomer_whenCustomerIsValid_saveCustomerToDB(){
		var customer = createCustomer();
		ResponseEntity<Object> response = testRestTemplate.postForEntity(API_1_0_CUSTOMERS, customer, Object.class);
		assertThat(customerRepository.count()).isEqualTo(1);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);

	}

	@Test
	@Order(2)
	public void postCustomer_whenCustomerIsValid_updateCustomerInfoToDB(){
		Long customer_id = 1L;
		var customerDTO = modelMapper.map(getCustomerWithChangeInfo(),CustomerDTO.class);
		testRestTemplate.put(API_1_0_UPDATE_CUSTOMER+"/"+customer_id, customerDTO);
		assertNotEquals(34,customerRepository.getById(1L).getAge());
	}


	@Test
	@Order(3)
	public void findCustomerByName_whenIsAvailableInDB(){
		String name= "Ram";
		ResponseEntity<CustomersList> customerDetails =
				testRestTemplate.getForEntity(API_1_0_FIND_CUSTOMER_BY_NAME+"/"+name,
						CustomersList.class);
		assertThat(customerDetails.getStatusCode()).isEqualTo(HttpStatus.OK);

		CustomersList customerList = customerDetails.getBody();
		List<Customer> customers = customerList.getCustomersList();
		customers.stream().forEach(customer -> {
			assertEquals("Ram",customer.getName());
		});

	}

	@Test
	@Order(4)
	public void findAllCustomerFromDB(){
		ResponseEntity<CustomersList> customerDetails =
				testRestTemplate.getForEntity(API_1_0_FIND_ALL_CUSTOMER,
						CustomersList.class);
		assertThat(customerDetails.getStatusCode()).isEqualTo(HttpStatus.OK);
		CustomersList customerList = customerDetails.getBody();
		List<Customer> customers = customerList.getCustomersList();
		assertEquals(1,customers.size());
	}

	@Test
	@Order(5)
	public void findAllCustomerByZipCodeFromDB(){
		String zipCode = "41830";
		ResponseEntity<CustomersList> customerDetails =
				testRestTemplate.getForEntity(API_1_0_FIND_CUSTOMER_BY_ZIPCODE+"/"+zipCode,
						CustomersList.class);
		assertThat(customerDetails.getStatusCode()).isEqualTo(HttpStatus.OK);

		CustomersList customerList = customerDetails.getBody();
		List<Customer> customers = customerList.getCustomersList();
		customers.stream().forEach(customer -> {
			customer.getAddressList().stream().forEach(address -> {
				assertEquals("99999",address.getZipCode());
			});
		});

	}

	@Test
	@Order(6)
	public void deleteCustomer_whenCustomerIsValid_DeleteCustomerInDB(){
		Long customer_id = 1L;
		testRestTemplate.delete(API_1_0_DELETE_CUSTOMER+"/"+customer_id);
		assertEquals(true,customerRepository.findById(1L).isEmpty());
	}

	private Customer getCustomerWithChangeInfo(){
		var address = Address.builder().houseNumber("LGH 12342102").streetNumber("56")
				.streetName("Blidvadersgatan").city("Göteborg").state("Vastra Gotaland")
				.country("Sweden").zipCode("41830").build();
		List<Address> addressesList = new ArrayList<>();
		addressesList.add(address);
		return Customer.builder()
				.id(1L)
				.name("Ram")
				.age(55)
				.registrationDate(Constants.getLocalDateTime())
				.lastUpdateDate(Constants.getLocalDateTime())
				.addressList(addressesList)
				.build();
	}

	private Customer createCustomer(){
		var address = Address.builder().houseNumber("LGH 12342102").streetNumber("56")
				.streetName("Blidvadersgatan").city("Göteborg").state("Vastra Gotaland")
				.country("Sweden").zipCode("41830").build();
		List<Address> addressesList = new ArrayList<>();
		addressesList.add(address);

		return Customer.builder()
				.id(1L)
				.name("Ram")
				.age(34)
				.registrationDate(Constants.getLocalDateTime())
				.lastUpdateDate(Constants.getLocalDateTime())
				.addressList(addressesList)
				.build();
	}
}
