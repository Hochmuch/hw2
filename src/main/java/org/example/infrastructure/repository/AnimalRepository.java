package org.example.infrastructure.repository;

import org.example.domain.Animal;
import org.example.domain.repository.IAnimalRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class AnimalRepository implements IAnimalRepository {
    private final Map<Long, Animal> animals = new ConcurrentHashMap<>();
    
    @Override
    public Animal save(Animal animal) {
        if (animal == null) {
            throw new IllegalArgumentException("Animal cannot be null");
        }
        
        animals.put(animal.getId(), animal);
        return animal;
    }
    
    public List<Animal> saveAll(List<Animal> animalList) {
        List<Animal> savedAnimals = new ArrayList<>();
        for (Animal animal : animalList) {
            savedAnimals.add(save(animal));
        }
        return savedAnimals;
    }
    
    @Override
    public Optional<Animal> findById(long id) {
        return Optional.ofNullable(animals.get(id));
    }

    
    @Override
    public List<Animal> findAll() {
        return new ArrayList<>(animals.values());
    }

    public void delete(Animal animal) {
        if (animal != null) {
            animals.remove(animal.getId());
        }
    }

    @Override
    public void deleteById(long id) {
        animals.remove(id);
    }

    @Override
    public boolean existsById(long id) {
        return animals.containsKey(id);
    }

    @Override
    public boolean existsByName(String name) {
        return animals.values().stream()
                .anyMatch(animal -> animal.getName().equals(name));
    }
} 