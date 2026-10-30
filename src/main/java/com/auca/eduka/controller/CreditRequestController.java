package com.auca.eduka.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.auca.eduka.Domain.CreditRequest;
import com.auca.eduka.Domain.CreditStatus;
import com.auca.eduka.service.CreditRequestService;



@RestController
@RequestMapping("/api/credit-requests")
public class CreditRequestController {

    @Autowired
    private CreditRequestService creditServ;

    @PostMapping("/save")
    public ResponseEntity<String> save(@RequestBody CreditRequest credit){
        String message = creditServ.saveCreditRequest(credit);
        if (message.equals("Credit request date cannot be in the past")){
            return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
        }
        if (message.equals("Shopkeeper already has an open credit request on that date")){
            return new ResponseEntity<>(message, HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(message, HttpStatus.CREATED);
    }

    @GetMapping("/by-status")
    public ResponseEntity<?> getCreditRequestsByStatus(@RequestParam String status){
        List<CreditRequest> requests = creditServ.getCreditRequestsByStatus(CreditStatus.valueOf(status.toUpperCase()));
        return new ResponseEntity<>(requests, HttpStatus.OK);
    }
    
    @GetMapping("/between")
    public ResponseEntity<?> getCreditRequestBetweenDates(@RequestParam String start, @RequestParam String end) {
        LocalDate startDate = LocalDate.parse(start);
        LocalDate endDate = LocalDate.parse(end);

        return new ResponseEntity<>(
            creditServ.getCreditRequestBetweenDates
            (startDate, endDate),
            HttpStatus.OK);
    }
    

    @GetMapping("/all")
    public List<CreditRequest> getAll(){
        return creditServ.getAllCreditRequests();
    }

    

    @PutMapping("/update/{id}")
    public ResponseEntity<String> update(@PathVariable UUID id, @RequestBody CreditRequest credit){
        String message = creditServ.updateCreditRequest(id, credit);
        if(message.equals("The credit request with that id does not exist")){
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<String> changeStatus(@PathVariable UUID id, @RequestParam CreditStatus status){
        String message = creditServ.changeStatus(id, status);
        if (message.equals("The credit request with that id does not exist")){
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        }
        if (message.startsWith("Cannot change status")){
            return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable UUID id){
        String message = creditServ.deleteCreditRequest(id);
        if(message.equals("The credit request with that id does not exist")){
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        }
        if (message.equals("That credit request has repayments, delete them first")){
            return new ResponseEntity<>(message, HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(message, HttpStatus.OK);
    }
}
