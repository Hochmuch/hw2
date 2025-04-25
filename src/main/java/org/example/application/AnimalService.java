package org.example.application;

import org.example.domain.Animal;
import org.example.domain.Enclosure;
import org.example.domain.repository.IAnimalRepository;
import org.example.domain.service.IAnimalService;
import org.example.domain.service.IEnclosureService;
import org.example.domain.valueobject.FoodType;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AnimalService implements IAnimalService {
    
    private final IAnimalRepository animalRepository;
    private final IEnclosureService enclosureService;
    
    public AnimalService(IAnimalRepository animalRepository, 
                         @Lazy IEnclosureService enclosureService) {
        this.animalRepository = animalRepository;
        this.enclosureService = enclosureService;
    }

    @Override
    public List<Animal> getAllAnimals() {
        return animalRepository.findAll();
    }

    @Override
    public Optional<Animal> findAnimalById(long id) {
        return animalRepository.findById(id);
    }

    @Override
    public Animal createAnimal(Animal animal) {
        if (animalRepository.existsByName(animal.getName())) {
            throw new IllegalArgumentException("Животное с именем " + animal.getName() + " уже существует");
        }
        
        return animalRepository.save(animal);
    }

    @Override
    public Animal updateAnimal(long id, Animal animalDetails) {
        Optional<Animal> animalOptional = animalRepository.findById(id);
        
        if (animalOptional.isEmpty()) {
            throw new IllegalArgumentException("Животное с ID " + id + " не найдено");
        }
        
        Animal existingAnimal = animalOptional.get();

        if (animalDetails.getName() != null && !animalDetails.getName().isEmpty()) {
            if (!existingAnimal.getName().equals(animalDetails.getName()) && 
                    animalRepository.existsByName(animalDetails.getName())) {
                throw new IllegalArgumentException("Животное с именем " + animalDetails.getName() + " уже существует");
            }
            existingAnimal.setName(animalDetails.getName());
        }
        if (animalDetails.getSpecies() != null) {
            existingAnimal.setSpecies(animalDetails.getSpecies());
        }
        if (animalDetails.getGender() != null) {
            existingAnimal.setGender(animalDetails.getGender());
        }
        if (animalDetails.getBirthDate() != null) {
            existingAnimal.setBirthDate(animalDetails.getBirthDate());
        }
        if (animalDetails.getFavoriteFood() != null) {
            existingAnimal.setFavoriteFood(animalDetails.getFavoriteFood());
        }
        if (animalDetails.getIsHealthy() != null) {
            existingAnimal.setIsHealthy(animalDetails.getIsHealthy());
        }
        
        return animalRepository.save(existingAnimal);
    }

    @Override
    public void deleteAnimal(long id) {
        if (!animalRepository.existsById(id)) {
            throw new IllegalArgumentException("Животное с ID " + id + " не найдено");
        }
        animalRepository.deleteById(id);
    }

    @Override
    public String feedAnimal(long id, FoodType food) {
        Optional<Animal> animalOpt = animalRepository.findById(id);
        
        if (animalOpt.isEmpty()) {
            throw new IllegalArgumentException("Животное с ID " + id + " не найдено");
        }
        
        Animal animal = animalOpt.get();
        animal.feed(food);
        animalRepository.save(animal);
        
        return "Животное " + animal.getName() + " успешно покормлено едой: " + food;
    }

    @Override
    public String treatAnimal(long id) {
        Optional<Animal> animalOpt = animalRepository.findById(id);
        
        if (animalOpt.isEmpty()) {
            throw new IllegalArgumentException("Животное с ID " + id + " не найдено");
        }
        
        Animal animal = animalOpt.get();
        animal.treat();
        animalRepository.save(animal);
        
        return "Животное " + animal.getName() + " успешно вылечено";
    }

    @Override
    public String moveAnimalToEnclosure(long animalId, long enclosureId) {
        Optional<Animal> animalOpt = animalRepository.findById(animalId);
        Optional<Enclosure> enclosureOpt = enclosureService.findEnclosureById(enclosureId);
        
        if (animalOpt.isEmpty()) {
            throw new IllegalArgumentException("Животное с ID " + animalId + " не найдено");
        }
        
        if (enclosureOpt.isEmpty()) {
            throw new IllegalArgumentException("Вольер с ID " + enclosureId + " не найден");
        }
        
        Animal animal = animalOpt.get();
        Enclosure targetEnclosure = enclosureOpt.get();

        boolean animalRemoved = false;
        List<Enclosure> allEnclosures = enclosureService.getAllEnclosures();
        for (Enclosure enclosure : allEnclosures) {
            if (enclosure.getAnimals().stream().anyMatch(a -> a.getId() == animal.getId())) {
                enclosure.removeAnimal(animal);
                enclosureService.updateEnclosure(enclosure.getId(), enclosure);
                animalRemoved = true;
                break;
            }
        }

        if (targetEnclosure.getAnimals().size() < targetEnclosure.getCapacity()) {
            if (targetEnclosure.addAnimal(animal)) {
                enclosureService.updateEnclosure(targetEnclosure.getId(), targetEnclosure);
                animalRepository.save(animal);
                
                String message = "Животное " + animal.getName() + " успешно перемещено в вольер типа: " + targetEnclosure.getType();
                if (animalRemoved) {
                    message += " (удалено из предыдущего вольера)";
                } else {
                    message += " (размещено впервые)";
                }
                return message;
            } else {
                throw new IllegalArgumentException("Не удалось добавить животное в вольер.");
            }
        } else {
            throw new IllegalArgumentException("Не удалось переместить животное. Вольер полон.");
        }
    }
} 