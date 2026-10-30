package com.auca.eduka.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.auca.eduka.Domain.Shop;
import com.auca.eduka.repository.ShopRepository;

@Service
public class ShopService {

    @Autowired
    private ShopRepository shopRepo;

    public String saveShop(Shop shop){
        shopRepo.save(shop);
        return "Shop saved successfully";
    }

    public List<Shop> getAllShops(){
        return shopRepo.findAll();
    }

    public Optional<Shop> getShopById(UUID id){
        return shopRepo.findById(id);
    }

    public String updateShop(UUID id, Shop newData){
        Optional<Shop> found = shopRepo.findById(id);
        if (found.isEmpty()){
            return "The shop with that id does not exist";
        }
        Shop shop = found.get();
        shop.setShopNumber(newData.getShopNumber());
        shop.setName(newData.getName());
        return "Shop updated successfully";
    }
    public String deleteShop(UUID id){
        if(!shopRepo.existsById(id)){
            return "The goods category with that id does not exist";
        }
        shopRepo.deleteById(id);
        return "Shop deleted successfully!";
    }

}
