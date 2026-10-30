package com.auca.eduka.controller;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.auca.eduka.Domain.Customer;
import com.auca.eduka.service.CustomerService;



@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @PostMapping("/save")
    public ResponseEntity<String> save(@RequestBody Customer customer){
        String message = customerService.saveCustomer(customer);
        return new ResponseEntity<>(message, HttpStatus.CREATED);
    }
    //getting customer by last name

    @GetMapping("/by-last-name")
    public ResponseEntity<?> getCustomerByLastName(@RequestParam String lastName) {
        return new ResponseEntity<>(
            customerService.findCustomerByLastName(lastName), HttpStatus.OK
        );
    }
    

    @GetMapping("/all")
    public List<Customer> getAll(){
        return customerService.getAllCustomers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?>getById(@PathVariable UUID id){
        Optional<Customer> customer = customerService.getCustomerById(id);
        if (customer.isEmpty()) {
            return new ResponseEntity<>("The customer with that id does not exist", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(customer.get(), HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> update(@PathVariable UUID id, @RequestBody Customer customer){
        String message = customerService.updateCustomer(id, customer);
        if(message.equals("The customer with that id does not exist")){
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable UUID id){
        String message = customerService.deleteCustomer(id);
        if (message.equals("The customer with that id does not exist")){
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(message, HttpStatus.OK);
    }


}
