package org.example.domain;

import org.example.domain.valueobject.EnclosureType;

import java.util.ArrayList;
import java.util.List;

public class Enclosure {
    private long id;
    private EnclosureType type;
    private int size;
    private int capacity;
    
    private List<Animal> animals = new ArrayList<>();

    public Enclosure(EnclosureType type, int size, int capacity) {
        this.type = type;
        this.size = size;
        this.capacity = capacity;
    }

    public Enclosure() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean addAnimal(Animal animal) {
        if (animals.size() >= capacity) {
            System.out.println("Не удалось добавить " + animal + " в вольер, он полон.");
            return false;
        }
        animals.add(animal);
        return true;
    }

    public boolean removeAnimal(Animal animal) {
        return animals.remove(animal);
    }

    public void clean() {
        System.out.println("Уборка в вольере произведена.");
    }

    public EnclosureType getType() {
        return type;
    }
    
    public void setType(EnclosureType type) {
        this.type = type;
    }
    
    public int getSize() {
        return size;
    }
    
    public void setSize(int size) {
        this.size = size;
    }
    
    public int getCapacity() {
        return capacity;
    }
    
    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }
    
    public List<Animal> getAnimals() {
        return new ArrayList<>(animals);
    }

    @Override
    public String toString() {
        return "Enclosure{id=" + id + ", type='" + type + "', capacity=" + capacity + ", animals=" + animals.size() + "}";
    }
}
