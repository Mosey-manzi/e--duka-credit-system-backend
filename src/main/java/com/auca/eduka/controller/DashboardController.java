package com.auca.eduka.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.auca.eduka.service.DashboardService;
import org.springframework.web.bind.annotation.GetMapping;

@RestController 
@RequestMapping ("/api/dashboard")
public class DashboardController {

    @Autowired private DashboardService dashboardServ;

    @GetMapping("/summary")
    public Map<String, Long> getSummary(){
        return dashboardServ.getSummary();
    }

}
