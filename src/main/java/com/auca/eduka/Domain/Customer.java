package com.auca.eduka.Domain;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;

@Entity 
@Table(name = "customer")
public class Customer {
    @Id 
    @GeneratedValue (strategy= GenerationType.UUID)
    private UUID id;

    private String firstName;
    private String lastName;

    @Column(unique = true)
    private String nationalId;

    private String phone;

    public Customer(){

    }
    public UUID getId(){
        return id;
    }
    public void setId(UUID id){
        this.id = id;
    }
    public String getFirstName(){
        return firstName;
    }
    public String getLastName(){
        return lastName;
    }
    public String getPhone(){
        return phone;
    }
    public void setFirstName(String firstName){
        this.firstName = firstName;
    }
    public void setLastName(String lastName){
        this.lastName = lastName;
    }
    public void setPhone(String phone){
        this.phone = phone;
    }
    public String getNationalId(){
        return nationalId;
    }
    public void setNationalId(String nationalId){
        this.nationalId = nationalId;
    }


}