package com.auca.eduka.controller;

import java.util.List;
import java.util.Optional;
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

import com.auca.eduka.Domain.Shopkeeper;
import com.auca.eduka.service.ShopkeeperService;


@RestController
@RequestMapping("/api/shopkeepers")
public class ShopkeeperController {

    @Autowired
    private ShopkeeperService keeperServ;
    @PostMapping("/save")
    public ResponseEntity<String> save(@RequestBody Shopkeeper keeper){
        String message = keeperServ.saveShopkeeper(keeper);
        return new ResponseEntity<>(message, HttpStatus.CREATED);
    }

    @GetMapping("/by-goods-category")
    public ResponseEntity<List<Shopkeeper>> getByGoodsCategory(@RequestParam String name){
        return new ResponseEntity<>(keeperServ.getShopKeepersByGoodsCategory(name), HttpStatus.OK);
    }
    

    @GetMapping("/all")
    public List<Shopkeeper> getAll(){
        return keeperServ.getAllShopkeepers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable UUID id){
        Optional <Shopkeeper> keeper = keeperServ.getShopkeeperById(id);
        if (keeper.isEmpty()){
            return new ResponseEntity<>("The shopkeeper with that id does not exist", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(keeper.get(), HttpStatus.OK);
    }
    

    @PutMapping("/update/{id}")
    public ResponseEntity <String> update(@PathVariable UUID id, @RequestBody Shopkeeper keeper){
        String message = keeperServ.updateShopkeeper(id, keeper);
        if(message.equals("The shopkeeper with that id does not exist")){
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @PutMapping("/{shopkeeperId}/goods-categories/{goodsCategoryId}")
    public ResponseEntity<String> addGoodsCategory(@PathVariable UUID shopkeeperId, @PathVariable UUID goodsCategoryId){
        String message = keeperServ.addGoodsCategory(shopkeeperId, goodsCategoryId);
        if (message.equals("The shopkeeper with that id does not exist")
        || message.equals("That goods category does not exist")){
            return  new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        }
        if (message.equals("The shopkeeper already has that goods category")){
            return new ResponseEntity<>(message, HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @PatchMapping ("/{shopkeeperId}/shop")
    public ResponseEntity<String> changeShop(@PathVariable UUID shopkeeperId, @RequestParam  int shopNumber){
        String message = keeperServ.changeShop(shopkeeperId, shopNumber);
        if (message.equals("The shopkeeper with that id does not exist")
            || message.equals("The shop with that shopNumber does not exist")) {
        return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
    }
    if (message.equals("That shop is taken by another shopkeeper")) {
        return new ResponseEntity<>(message, HttpStatus.CONFLICT);
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
