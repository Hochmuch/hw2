package org.example.domain;

import org.example.domain.valueobject.FoodType;
import org.example.domain.valueobject.Species;

import java.time.LocalDate;

public class Animal {
    private long id;
    private Species species;
    private String name;
    private LocalDate birthDate;
    private Boolean isMale;
    private FoodType favoriteFood;
    private Boolean isHealthy;

    public Animal(Species species, String name, LocalDate birthDate, Boolean isMale, FoodType favoriteFood, Boolean isHealthy) {
        this.species = species;
        this.name = name;
        this.birthDate = birthDate;
        this.isMale = isMale;
        this.favoriteFood = favoriteFood;
        this.isHealthy = isHealthy;
    }

    public Animal() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void feed(FoodType food) {
        System.out.println(this.name + " накормлено  " + food);
    }

    public void treat() {
        this.isHealthy = true;
        System.out.println(name + " вылечено.");
    }


    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Species getSpecies() {
        return this.species;
    }

    public void setSpecies(Species species) {
        this.species = species;
    }

    public Boolean getGender() {
        return this.isMale;
    }

    public void setGender(Boolean gender) {
        this.isMale = gender;
    }

    public LocalDate getBirthDate() {
        return this.birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public FoodType getFavoriteFood() {
        return this.favoriteFood;
    }

    public void setFavoriteFood(FoodType favoriteFood) {
        this.favoriteFood = favoriteFood;
    }

    public Boolean getIsHealthy() {
        return this.isHealthy;
    }

    public void setIsHealthy(Boolean healthy) {
        this.isHealthy = healthy;
    }

    @Override
    public String toString() {
        return "Animal{id=" + id + ", species='" + species + "', name='" + name + "'}";
    }
}
