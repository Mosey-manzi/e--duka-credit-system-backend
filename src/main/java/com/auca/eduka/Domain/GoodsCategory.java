package com.auca.eduka.Domain;

import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "goods_category")
public class GoodsCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String name;

    @ManyToMany(mappedBy = "goodsCategories")
    @JsonIgnore
    private List<Shopkeeper> shopkeepers;

    public GoodsCategory(){

    }

    public GoodsCategory(String name, List<Shopkeeper> shopkeepers) {
        this.name = name;
        this.shopkeepers = shopkeepers;
    }
    public UUID getId(){ return id;}
    public void setId(UUID id){this.id = id;}
    public String getName(){return name;}
    public void setName(String name){this.name = name;}
    public List<Shopkeeper> getShopkeepers(){return shopkeepers;}
    public void setShopkeepers(List<Shopkeeper> shopkeepers){ this.shopkeepers = shopkeepers;}

}
