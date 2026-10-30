package com.auca.eduka.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.auca.eduka.Domain.Customer;
import com.auca.eduka.repository.CustomerRepository;
import com.auca.eduka.repository.ShopkeeperRepository;

@Service
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired 
    private ShopkeeperRepository shopkeeperRepo;

    public String saveCustomer(Customer customer){
        customerRepository.save(customer);
        return "Customer saved successfully";
    }

    public Optional<List<Customer>> getCustomerOfShopkeeper(UUID shopkeeperId){
        if (!shopkeeperRepo.existsById(shopkeeperId)){
            return Optional.empty();
        }
        return Optional.of(customerRepository.findCustomersOfShopkeeper(shopkeeperId));
    }

    public List<Customer> getAllCustomers(){
        return customerRepository.findAll();
    }

    public Optional<Customer> getCustomerById(UUID id){
        return customerRepository.findById(id);
    }
    public List<Customer> findCustomerByLastName(String lastName){
        return customerRepository.findByLastNameIgnoreCaseOrderByFirstNameAsc(lastName);
    }

    public String updateCustomer(UUID id, Customer newData){
        Optional<Customer> found =customerRepository.findById(id);
        if(found.isEmpty()){
            return "The customer with that id does not exist";
        }
        Customer customer = found.get();
        customer.setFirstName(newData.getFirstName());
        customer.setLastName(newData.getLastName());
        customer.setNationalId(newData.getNationalId());
        customer.setPhone(newData.getPhone());
        customerRepository.save(customer);
        return "Customer updated successfully";
    }

    public String deleteCustomer(UUID id){
        if (!customerRepository.existsById(id)){
            return "The customer with that id does not exist";
        }
        customerRepository.deleteById(id);
        return "Customer deleted successfully";
    }
}
