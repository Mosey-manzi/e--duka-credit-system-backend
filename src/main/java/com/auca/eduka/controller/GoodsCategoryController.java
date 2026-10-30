package com.auca.eduka.controller;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.auca.eduka.Domain.GoodsCategory;
import com.auca.eduka.service.GoodsCategoryService;


@RestController
@RequestMapping("/api/goods-category")
public class GoodsCategoryController {

    @Autowired
    private GoodsCategoryService goodsCategoryServ;

    @PostMapping("/save")
    public ResponseEntity<String> save(@RequestBody GoodsCategory goodsCategory){
        String message = goodsCategoryServ.saveGoodsCategory(goodsCategory);
        return new ResponseEntity<>(message, HttpStatus.CREATED);
    }

    @GetMapping("/unused")
    public ResponseEntity<List<GoodsCategory>> getUnused(){
        return new ResponseEntity<>(goodsCategoryServ.getUnusedCategories(), HttpStatus.OK);
    }
    public String getMethodName(@RequestParam String param) {
        return new String();
    }
    

    @GetMapping("/all")
    public List<GoodsCategory> getAll(){
        return goodsCategoryServ.getAllGoodsCategories();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?>getById(@PathVariable UUID id){
        Optional<GoodsCategory> goodsCategory = goodsCategoryServ.getGoodsCategoryById(id);
        if(goodsCategory.isEmpty()) {
            return new ResponseEntity<>("The goods category with this id doesn't exist", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(goodsCategory.get(), HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity <String> update(@PathVariable UUID id, @RequestBody GoodsCategory goodsCategory){
        String message = goodsCategoryServ.updateGoodsCategory(id, goodsCategory);
        if(message.equals("The goods category with that id does not exist")){
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable UUID id){
        String message = goodsCategoryServ.deleteGoodsCategory(id);
        if (message.equals("The goods category with that id does not exist")){
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(message, HttpStatus.OK);
    }
}
