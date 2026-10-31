package com.auca.eduka.service;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.auca.eduka.Domain.CreditStatus;
import com.auca.eduka.repository.CreditRequestRepository;
import com.auca.eduka.repository.CustomerRepository;
import com.auca.eduka.repository.RepaymentRepository;
import com.auca.eduka.repository.ShopRepository;

import com.auca.eduka.repository.ShopkeeperRepository;

@Service 
public class DashboardService {
    
    @Autowired private CustomerRepository customerRepo;
    @Autowired private ShopkeeperRepository shopkeeperRepo;
    @Autowired private ShopRepository shopRepo;
    @Autowired private CreditRequestRepository creditRequestRepo;
    @Autowired private RepaymentRepository repaymentRepo;

    public Map<String, Long> getSummary(){
        Map<String, Long> summary = new LinkedHashMap<>();
        summary.put("customers", customerRepo.count());
        summary.put("shopkeepers", shopkeeperRepo.count());
        summary.put("freeShops", (long) shopRepo.findFreeShops().size());
        summary.put("requestsToday", creditRequestRepo.countByRequestDate(LocalDate.now()));
        summary.put("requested", creditRequestRepo.countByStatus(CreditStatus.REQUESTED));
        summary.put("repayments", repaymentRepo.count());
        return summary;
    }   
}
