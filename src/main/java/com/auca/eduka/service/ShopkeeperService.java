package com.auca.eduka.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.auca.eduka.Domain.GoodsCategory;
import com.auca.eduka.Domain.Shop;
import com.auca.eduka.Domain.Shopkeeper;
import com.auca.eduka.repository.GoodsCategoryRepository;
import com.auca.eduka.repository.ShopRepository;
import com.auca.eduka.repository.ShopkeeperRepository;

@Service
public class ShopkeeperService {

    @Autowired
    private ShopkeeperRepository shopkeeperRepo;
    @Autowired 
    private ShopRepository shopRepo;
    @Autowired 
    private GoodsCategoryRepository goodsCategoryRepo;

    public String saveShopkeeper(Shopkeeper shopkeeper){
        shopkeeperRepo.save(shopkeeper);
        return "Shopkeeper saved successfully";
    }

    public List<Shopkeeper> getAllShopkeepers(){
        return shopkeeperRepo.findAll();
    }

    public List<Shopkeeper> getShopKeepersByGoodsCategory(String name){
        return shopkeeperRepo.findByGoodsCategoryName(name);
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

    public String changeShop(UUID shopkeeperId, int shopNumber){
        Optional<Shopkeeper> sk = shopkeeperRepo.findById(shopkeeperId);
        if(sk.isEmpty()){
            return "The shopkeeper with that id does not exist";
        }
        Optional<Shop> shop = shopRepo.findByShopNumber(shopNumber);
        if (shop.isEmpty()){ return "The shop with that shopNumber does not exists";}
        if (shopkeeperRepo.existsByShop(shop.get())){
            return "That shop is taken by another shopkeeper";
        }
        Shopkeeper shopkeeper = sk.get();
        shopkeeper.setShop(shop.get());
        shopkeeperRepo.save(shopkeeper);
        return "Shop changed successfully";
    }

    public String addGoodsCategory(UUID shopkeeperId, UUID goodsCategoryId){
        Optional<Shopkeeper> sk = shopkeeperRepo.findById(shopkeeperId);
        if (sk.isEmpty()) return "The shopkeeper with that id does not exist";
        Optional<GoodsCategory> gc = goodsCategoryRepo.findById(goodsCategoryId);
        if (gc.isEmpty()) return "That goods category does not exist";
        Shopkeeper shopkeeper = sk.get();
        if (shopkeeper.getGoodsCategories().contains(gc.get())) {
            return "The shopkeeper already has that goods category";
        }
        shopkeeper.getGoodsCategories().add(gc.get());
        shopkeeperRepo.save(shopkeeper);
        return "Goods category added to the shopkeeper";
    }

    public String deleteShopkeeper(UUID id){
        if(!shopkeeperRepo.existsById(id)){
            return "The shopkeeper with that id does not exist";
        }
        shopkeeperRepo.deleteById(id);
        return "Shopkeeper deleted successfully";
    }
}
