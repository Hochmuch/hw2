package org.example.domain.repository;

import org.example.domain.Animal;

import java.util.List;
import java.util.Optional;

public interface IAnimalRepository {

    List<Animal> findAll();

    Optional<Animal> findById(long id);

    boolean existsByName(String name);

    boolean existsById(long id);

    Animal save(Animal animal);

    void deleteById(long id);
} 