package com.auca.eduka.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import com.auca.eduka.Domain.Shop;
import com.auca.eduka.service.ShopService;

@RestController
@RequestMapping("/api/shop")
public class ShopController {

    @Autowired
    private ShopService shopServ;

    @PostMapping("/save")
    public ResponseEntity<String> save(@RequestBody Shop shop){
        String message = shopServ.saveShop(shop);
        return new ResponseEntity<>(message, HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public List<Shop> getAll(){
        return shopServ.getAllShops();
    }

    @PutMapping("/update/{id}")
    public ResponseEntity <String> update(@PathVariable UUID id, @RequestBody Shop shop){
        String message = shopServ.updateShop(id, shop);
        if(message.equals("The shop with that id does not exist")){
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable UUID id){
        String message = shopServ.deleteShop(id);
        if(message.equals("The shop with that id does not exist")){
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

}
