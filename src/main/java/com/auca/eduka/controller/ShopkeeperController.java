package com.auca.eduka.controller;

import java.util.UUID;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import com.auca.eduka.Domain.Shopkeeper;
import com.auca.eduka.service.ShopkeeperService;

@RestController
@RequestMapping("/api/shopkeeper")
public class ShopkeeperController {

    @Autowired
    private ShopkeeperService keeperServ;

    @PostMapping("/save")
    public ResponseEntity<String> save(@RequestBody Shopkeeper keeper){
        String message = keeperServ.saveShopkeeper(keeper);
        return new ResponseEntity<>(message, HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public List<Shopkeeper> getAll(){
        return keeperServ.getAllShopkeepers();
    }

    @PutMapping("/update/{id}")
    public ResponseEntity <String> update(@PathVariable UUID id, @RequestBody Shopkeeper keeper){
        String message = keeperServ.updateShopkeeper(id, keeper);
        if(message.equals("The shop with that id does not exist")){
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable UUID id){
        String message = keeperServ.deleteShopkeeper(id);
        if(message.equals("The shopkeeper with that id does not exist")){
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(message, HttpStatus.OK);
    }
}
