package org.example.presentation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.domain.Animal;
import org.example.domain.Enclosure;
import org.example.domain.service.IAnimalService;
import org.example.domain.service.IEnclosureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/enclosures")
@Tag(name = "Вольеры")
public class EnclosureController {

    private final IEnclosureService enclosureService;

    @Autowired
    public EnclosureController(
            IEnclosureService enclosureService,
            IAnimalService animalService) {
        this.enclosureService = enclosureService;
    }

    @Operation(summary = "Получить список всех вольеров")
    @GetMapping
    public ResponseEntity<List<Enclosure>> getAllEnclosures() {
        List<Enclosure> enclosures = enclosureService.getAllEnclosures();
        return ResponseEntity.ok(enclosures);
    }

    @Operation(summary = "Получить вольер по ID")
    @GetMapping("/{id}")
    public ResponseEntity<Enclosure> getEnclosureById(
            @Parameter(description = "ID вольера") @PathVariable long id) {
        return enclosureService.findEnclosureById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Создать новый вольер")
    @PostMapping
    public ResponseEntity<Enclosure> createEnclosure(@RequestBody Enclosure enclosure) {
        Enclosure savedEnclosure = enclosureService.createEnclosure(enclosure);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedEnclosure);
    }

    @Operation(summary = "Обновить информацию о вольере")
    @PutMapping("/{id}")
    public ResponseEntity<Enclosure> updateEnclosure(
            @Parameter(description = "ID вольера") @PathVariable long id,
            @RequestBody Enclosure enclosureDetails) {
        try {
            Enclosure updatedEnclosure = enclosureService.updateEnclosure(id, enclosureDetails);
            return ResponseEntity.ok(updatedEnclosure);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Удалить вольер")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEnclosure(
            @Parameter(description = "ID вольера") @PathVariable long id) {
        try {
            enclosureService.deleteEnclosure(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            if (e.getMessage().contains("не найден")) {
                return ResponseEntity.notFound().build();
            } else {
                return ResponseEntity.badRequest().body(e.getMessage());
            }
        }
    }

    @Operation(summary = "Получить животных в вольере")
    @GetMapping("/{id}/animals")
    public ResponseEntity<List<Animal>> getAnimalsInEnclosure(
            @Parameter(description = "ID вольера") @PathVariable long id) {
        Optional<Enclosure> enclosureOpt = enclosureService.findEnclosureById(id);

        if (enclosureOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(enclosureOpt.get().getAnimals());
    }

    @Operation(summary = "Провести уборку")
    @PostMapping("/{id}/clean")
    public ResponseEntity<String> cleanEnclosure(
            @Parameter(description = "ID вольера") @PathVariable long id) {
        try {
            String result = enclosureService.cleanEnclosure(id);
            return ResponseEntity.ok(result);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
} 