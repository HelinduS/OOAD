package com.example.demo.Service;

import com.example.demo.DTO.CustomerDTO;
import com.example.demo.Entity.Customer;
import com.example.demo.Entity.LoginInfo;
import com.example.demo.Repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    public Customer saveCustomer(CustomerDTO customerDTO) {

        Customer customer = new Customer();
        customer.setFirstName(customerDTO.getFirstName());
        customer.setLastName(customerDTO.getLastName());
        customer.setUserName(customerDTO.getUserName());
        customer.setAddress(customerDTO.getAddress());
        customer.setEmail(customerDTO.getEmail());
        customer.setPassword(customerDTO.getPassword());


        LoginInfo loginInfo = new LoginInfo();
        loginInfo.setUserName(customerDTO.getUserName());
        loginInfo.setEmail(customerDTO.getEmail());
        loginInfo.setPassword(customerDTO.getPassword());


        customer.setLoginInfo(loginInfo);


        return customerRepository.save(customer);
    }

    public CustomerDTO getCustomerById(int id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found with ID: " + id));

        return new CustomerDTO(
                customer.getId(),
                customer.getFirstName(),
                customer.getLastName(),
                customer.getUserName(),
                customer.getAddress(),
                customer.getEmail(),
                customer.getPassword()
        );
    }

    public List<CustomerDTO> getAllCustomers() {
        List<Customer> customers = customerRepository.findAll();


        return customers.stream()
                .map(customer -> new CustomerDTO(
                        customer.getId(),
                        customer.getFirstName(),
                        customer.getLastName(),
                        customer.getUserName(),
                        customer.getAddress(),
                        customer.getEmail(),
                        null // Don't include the password
                ))
                .collect(Collectors.toList());
    }

    public long getTotalUserCount() {
        return customerRepository.count();
    }
}
