package com.auca.eduka.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.auca.eduka.service.RepaymentService;
import com.auca.eduka.Domain.Repayment;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/api/repayments")
public class RepaymentController {
    @Autowired private RepaymentService repaymentServ;

    @PostMapping ("/save")
    public ResponseEntity<String> save(@RequestParam UUID creditRequestId, @RequestBody Repayment repayment){
        String message = repaymentServ.saveRepayment(creditRequestId, repayment);
        if (message.equals("The credit request with that id does not exist")){
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        }
        if (message.equals("Repayment are only for DISBURSED credit requests")){
            return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(message, HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public List<Repayment> getAll(){
        return repaymentServ.getAllRepayments();
    }
    

    @GetMapping("/customers/{nationalId}")
    public List<Repayment> getByCustomer(@PathVariable  String nationalId){
        return repaymentServ.getRepaymentsOfCustomer(nationalId);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable UUID id) {
        String message = repaymentServ.deleteRepayment(id);
        if(message.equals("The repayment with that id does not exist")){
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(message, HttpStatus.OK);
    }
    
}
