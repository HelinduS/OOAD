package com.example.demo.Controller;

import com.example.demo.Entity.Customer;
import com.example.demo.Service.CustomerService;
import com.example.demo.DTO.CustomerDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @PostMapping("/create")
    public ResponseEntity<String> createCustomer(@RequestBody CustomerDTO customerDTO) {
        Customer savedCustomer = customerService.saveCustomer(customerDTO);
        int customerId = savedCustomer.getId(); // Get the generated ID
        return ResponseEntity.ok("Customer created successfully. Your User ID is: " + customerId);
    }

    @GetMapping("/find")
    public ResponseEntity<?> getCustomerById(@RequestParam int id) {
        try {
            CustomerDTO customerDTO = customerService.getCustomerById(id);
            if (customerDTO != null) {
                return ResponseEntity.ok(customerDTO);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error retrieving customer: " + e.getMessage());
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<CustomerDTO>> getAllCustomers() {
        List<CustomerDTO> customers = customerService.getAllCustomers();
        return ResponseEntity.ok(customers);
    }

    @GetMapping("/count")
    public ResponseEntity<Long> getProductCount() {
        long count = customerService.getTotalUserCount();
        return ResponseEntity.ok(count);
    }
}
