package com.auca.eduka.service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.auca.eduka.Domain.CreditRequest;
import com.auca.eduka.Domain.CreditStatus;
import com.auca.eduka.Domain.Repayment;
import com.auca.eduka.repository.CreditRequestRepository;
import com.auca.eduka.repository.RepaymentRepository;

@Service 
public class RepaymentService {

    @Autowired private RepaymentRepository repaymentRepo;
    @Autowired  private CreditRequestRepository creditRequestRepo;

    public String saveRepayment(UUID creditRequestid, Repayment repayment){
        Optional<CreditRequest> found = creditRequestRepo.findById(creditRequestid);
        if(found.isEmpty()){
            return "The credit request with that id does not exist";
        }
        if (found.get().getStatus() != CreditStatus.DISBURSED) {
            return "Repayments are only for DISBURSED credit requests";
        }
        
        repayment.setPaymentDate(LocalDate.now());
        repayment.setCreditRequest(found.get());
        repaymentRepo.save(repayment);
        return "Repayment saved successfully";
    }

    public List<Repayment> getAllRepayments(){
        return repaymentRepo.findAll();
    }

    public List<Repayment> getRepaymentsOfCustomer(String nationalId){
        return repaymentRepo.findByCustomerNationalId(nationalId);
        
    }

    public String deleteRepayment(UUID id){
        if (!repaymentRepo.existsById(id)){
            return "The repayment with that id does not exist";
        }
        repaymentRepo.deleteById(id);
        return "Repayment deleted successfully";
    }
}
