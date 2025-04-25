package org.example.presentation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.domain.Animal;
import org.example.domain.service.IAnimalService;
import org.example.domain.valueobject.FoodType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/animals")
@Tag(name = "Животные")
public class AnimalController {

    private final IAnimalService animalService;

    @Autowired
    public AnimalController(IAnimalService animalService) {
        this.animalService = animalService;
    }

    @Operation(summary = "Получить список всех животных")
    @GetMapping
    public ResponseEntity<List<Animal>> getAllAnimals() {
        List<Animal> animals = animalService.getAllAnimals();
        return ResponseEntity.ok(animals);
    }

    @Operation(summary = "Получить животное по ID")
    @GetMapping("/{id}")
    public ResponseEntity<Animal> getAnimalById(
            @Parameter(description = "ID животного") @PathVariable long id) {
        return animalService.findAnimalById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Создать новое животное")
    @PostMapping
    public ResponseEntity<Animal> createAnimal(@RequestBody Animal animal) {
        Animal savedAnimal = animalService.createAnimal(animal);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedAnimal);
    }

    @Operation(summary = "Обновить информацию о животном")
    @PutMapping("/{id}")
    public ResponseEntity<Animal> updateAnimal(
            @Parameter(description = "ID животного") @PathVariable long id,
            @RequestBody Animal animalDetails) {
        try {
            Animal updatedAnimal = animalService.updateAnimal(id, animalDetails);
            return ResponseEntity.ok(updatedAnimal);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Удалить животное")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAnimal(
            @Parameter(description = "ID животного") @PathVariable long id) {
        try {
            animalService.deleteAnimal(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Покормить животное")
    @PostMapping("/{id}/feed")
    public ResponseEntity<String> feedAnimal(
            @Parameter(description = "ID животного") @PathVariable long id,
            @Parameter(description = "Тип пищи") @RequestParam String food) {
        try {
            String result = animalService.feedAnimal(id, FoodType.of(food));
            return ResponseEntity.ok(result);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Вылечить животное")
    @PostMapping("/{id}/treat")
    public ResponseEntity<String> treatAnimal(
            @Parameter(description = "ID животного") @PathVariable long id) {
        try {
            String result = animalService.treatAnimal(id);
            return ResponseEntity.ok(result);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Переместить животное в вольер")
    @PostMapping("/{id}/move/{enclosureId}")
    public ResponseEntity<String> moveAnimal(
            @Parameter(description = "ID животного") @PathVariable long id,
            @Parameter(description = "ID вольера") @PathVariable long enclosureId) {
        try {
            String result = animalService.moveAnimalToEnclosure(id, enclosureId);
            return ResponseEntity.ok(result);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
} 