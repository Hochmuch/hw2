package org.example.domain.service;

import org.example.domain.Animal;
import org.example.domain.valueobject.FoodType;
import org.example.domain.valueobject.Species;

import java.util.List;
import java.util.Optional;

public interface IAnimalService {

    List<Animal> getAllAnimals();

    Optional<Animal> findAnimalById(long id);

    Animal createAnimal(Animal animal);

    Animal updateAnimal(long id, Animal animalDetails);

    void deleteAnimal(long id);

    String feedAnimal(long id, FoodType food);

    String treatAnimal(long id);

    String moveAnimalToEnclosure(long animalId, long enclosureId);
} 