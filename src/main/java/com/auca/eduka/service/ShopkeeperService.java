package com.auca.eduka.service;

import java.util.List;
import java.util.UUID;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import com.auca.eduka.repository.ShopkeeperRepository;
import com.auca.eduka.Domain.Shopkeeper;

@Service
public class ShopkeeperService {

    @Autowired
    private ShopkeeperRepository shopkeeperRepo;

    public String saveShopkeeper(Shopkeeper shopkeeper){
        shopkeeperRepo.save(shopkeeper);
        return "Shopkeeper saved successfully";
    }

    public List<Shopkeeper> getAllShopkeepers(){
        return shopkeeperRepo.findAll();
    }

    public Optional<Shopkeeper> getShopkeeperById(UUID id){
        return shopkeeperRepo.findById(id);
    }

    public String updateShopkeeper(UUID id, Shopkeeper newData){
        Optional<Shopkeeper> found = shopkeeperRepo.findById(id);
        if (found.isEmpty()) {
            return "The shopkeeper with that id does not exist";
        }
        Shopkeeper shopkeeper = found.get();
        shopkeeper.setFirstName(newData.getFirstName());
        shopkeeper.setLastName(newData.getLastName());
        shopkeeper.setPhone(newData.getPhone());
        shopkeeper.setShop(newData.getShop());
        shopkeeper.setGoodsCategories(newData.getGoodsCategories());
        shopkeeperRepo.save(shopkeeper);

        return "Shopkeeper updated successfully";

    }
    public String deleteShopkeeper(UUID id){
        if(!shopkeeperRepo.existsById(id)){
            return "The shopkeeper with that id does not exist";
        }
        shopkeeperRepo.deleteById(id);
        return "Shopkeeper deleted successfully";
    }
}
