package com.auca.eduka.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.auca.eduka.Domain.CreditRequest;
import com.auca.eduka.Domain.CreditStatus;
import com.auca.eduka.repository.CreditRequestRepository;

@Service
public class CreditRequestService {
    @Autowired
    private CreditRequestRepository creditRequestRepo;

    public String saveCreditRequest(CreditRequest credit){
        creditRequestRepo.save(credit);
        return "Credit Request saved successfully";
    }
    
    public List<CreditRequest> getAllCreditRequests(){
        return creditRequestRepo.findAll();
    }

    //B2
    public List<CreditRequest> getCreditRequestsByStatus(CreditStatus status){
        return creditRequestRepo.findByStatusOrderByRequestDateAsc(status);
    }

    //B3
    public List<CreditRequest> getCreditRequestBetweenDates(LocalDate start, LocalDate end){
        return creditRequestRepo.findByRequestDateBetweenOrderByRequestDateAsc(start, end);

    }
    public Optional<CreditRequest> getCreditRequestById(UUID id){
        return creditRequestRepo.findById(id);
    }
    
    public String updateCreditRequest(UUID id, CreditRequest newData){
        Optional<CreditRequest> found = creditRequestRepo.findById(id);
        if(found.isEmpty()){
            return "The credit request with that id does not exit";
        }
        CreditRequest credit = found.get();
        credit.setRequestDate(newData.getRequestDate());
        credit.setGoodsDescription(newData.getGoodsDescription());
        credit.setCreditAmount(newData.getCreditAmount());
        credit.setCustomer(newData.getCustomer());
        credit.setShopkeeper(newData.getShopkeeper());
        creditRequestRepo.save(credit);
        return "Credit request updated successfully";
    }

    public String deleteCreditRequest(UUID id){
        if(!creditRequestRepo.existsById(id)){
            return "The credit request with that id does not exist";
        }
        creditRequestRepo.deleteById(id);
        return "Credit request deleted successfully";
    }

}
