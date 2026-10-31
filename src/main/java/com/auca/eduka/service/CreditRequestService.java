package com.auca.eduka.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.auca.eduka.Domain.CreditRequest;
import com.auca.eduka.Domain.CreditStatus;
import com.auca.eduka.repository.CreditRequestRepository;
import com.auca.eduka.repository.RepaymentRepository;

@Service
public class CreditRequestService {
    @Autowired
    private CreditRequestRepository creditRequestRepo;
    @Autowired private RepaymentRepository repaymentRepo;

    public String saveCreditRequest(CreditRequest credit){
        if (credit.getRequestDate().isBefore(LocalDate.now())){
            return "Credit request date cannot be in the past";
        }
        if (creditRequestRepo.existsByShopkeeperAndRequestDateAndStatusNot(
            credit.getShopkeeper(), credit.getRequestDate(), CreditStatus.DECLINED
        )){
            return "Shopkeeper already has an open credit request on that date";
        }
        credit.setStatus(CreditStatus.REQUESTED);
        creditRequestRepo.save(credit);
        return "Credit request saved successfully";
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

    public String changeStatus(UUID id, CreditStatus newStatus){
        Optional<CreditRequest> found = creditRequestRepo.findById(id);
        if (found.isEmpty()){
            return "The credit request with that id does not exist";
        }
        CreditRequest credit = found.get();
        CreditStatus current = credit.getStatus();
        
        Boolean allowed = 
            (current == CreditStatus.REQUESTED && (newStatus == CreditStatus.APPROVED || newStatus == CreditStatus.DECLINED))
            || (current == CreditStatus.APPROVED && (newStatus == CreditStatus.DISBURSED || newStatus == CreditStatus.DECLINED));
        if (!allowed){
            return "Cannot change status from " + current + " to " + newStatus;
        }
        credit.setStatus(newStatus);
        creditRequestRepo.save(credit);
        return "Credit request status updated successfully";
    }

    public String deleteCreditRequest(UUID id){
        Optional<CreditRequest> found = creditRequestRepo.findById(id);
        if (found.isEmpty()){
            return "The credit request with that id does not exist";
        }
        if (repaymentRepo.existsByCreditRequest(found.get())){
            return "That credit request has repayments, delete them first";
        }
        creditRequestRepo.deleteById(id);
        return "Credit request delete successfully";
    }

    public List<Object[]> getStatsByStatus(){
        return creditRequestRepo.countByStatusGroup();
    }
    
    @Transactional 
    public String cancelDay(UUID shopkeeperId, LocalDate date){
        int count = creditRequestRepo.cancelDay(shopkeeperId, date, CreditStatus.DECLINED, CreditStatus.DISBURSED);
        return count + " credit requests cancelled";
    }

}
