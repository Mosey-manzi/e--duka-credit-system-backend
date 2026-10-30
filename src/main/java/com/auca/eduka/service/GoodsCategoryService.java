package com.auca.eduka.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.auca.eduka.Domain.GoodsCategory;
import com.auca.eduka.repository.GoodsCategoryRepository;

@Service
public class GoodsCategoryService {

    @Autowired
    private GoodsCategoryRepository goodsCategoryRepo;

    public String saveGoodsCategory(GoodsCategory goodsCategory){
        goodsCategoryRepo.save(goodsCategory);
        return "Goods category saved successfully";
    }

    public List<GoodsCategory> getAllGoodsCategories(){
        return goodsCategoryRepo.findAll();
    }

    public Optional<GoodsCategory> getGoodsCategoryById(UUID id){
        return goodsCategoryRepo.findById(id);
    }

    public String updateGoodsCategory(UUID id, GoodsCategory newData){
        Optional<GoodsCategory> found = goodsCategoryRepo.findById(id);
        if (found.isEmpty()){
            return "The goods category with that id does not exist";
        }
        GoodsCategory goodsCategory = found.get();
        goodsCategory.setName(newData.getName());
        goodsCategoryRepo.save(goodsCategory);
        return "Goods category updated successfully";

    }
    public String deleteGoodsCategory(UUID id){
        if(!goodsCategoryRepo.existsById(id)){
            return "The goods category with that id does not exist";

        }
        goodsCategoryRepo.deleteById(id);
        return "Goods category deleted successfully";
    }

}
