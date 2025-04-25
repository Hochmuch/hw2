package org.example.presentation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.domain.FeedingSchedule;
import org.example.domain.service.IFeedingScheduleService;
import org.example.domain.valueobject.FeedingTime;
import org.example.domain.valueobject.FoodType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/feeding-schedules")
@Tag(name = "Расписания кормления")
public class FeedingScheduleController {

    private final IFeedingScheduleService feedingScheduleService;

    @Autowired
    public FeedingScheduleController(IFeedingScheduleService feedingScheduleService) {
        this.feedingScheduleService = feedingScheduleService;
    }

    @GetMapping
    @Operation(summary = "Получить все расписания кормления")
    public ResponseEntity<List<FeedingSchedule>> getAllSchedules() {
        List<FeedingSchedule> schedules = feedingScheduleService.getAllSchedules();
        return new ResponseEntity<>(schedules, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получить расписание кормления по ID")
    public ResponseEntity<FeedingSchedule> getScheduleById(
            @Parameter(description = "ID расписания", required = true)
            @PathVariable long id) {
        Optional<FeedingSchedule> scheduleOpt = feedingScheduleService.findScheduleById(id);

        return scheduleOpt.map(schedule -> new ResponseEntity<>(schedule, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/animal/{animalId}")
    @Operation(summary = "Получить расписания кормления по ID животного")
    public ResponseEntity<List<FeedingSchedule>> getSchedulesByAnimalId(
            @Parameter(description = "ID животного", required = true)
            @PathVariable long animalId) {
        List<FeedingSchedule> schedules = feedingScheduleService.getSchedulesByAnimalId(animalId);
        return new ResponseEntity<>(schedules, HttpStatus.OK);
    }

    @PostMapping
    @Operation(summary = "Создать новое расписание кормления")
    public ResponseEntity<FeedingSchedule> createSchedule(
            @Parameter(description = "ID животного", required = true)
            @RequestParam long animalId,
            @Parameter(description = "Время кормления (HH:MM)", required = true)
            @RequestParam String feedingTime,
            @Parameter(description = "Тип пищи", required = true)
            @RequestParam String food) {
        try {
            FeedingSchedule schedule = feedingScheduleService.createSchedule(
                    animalId,
                    FeedingTime.fromString(feedingTime),
                    FoodType.of(food));
            return new ResponseEntity<>(schedule, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Обновить расписание кормления")
    public ResponseEntity<String> updateSchedule(
            @Parameter(description = "ID расписания", required = true)
            @PathVariable long id,
            @Parameter(description = "Новое время кормления (HH:MM)", required = true)
            @RequestParam String newTime,
            @Parameter(description = "Новый тип пищи", required = true)
            @RequestParam String newFood) {
        try {
            String result = feedingScheduleService.updateSchedule(
                    id,
                    FeedingTime.fromString(newTime),
                    FoodType.of(newFood));
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить расписание кормления")
    public ResponseEntity<Void> deleteSchedule(
            @Parameter(description = "ID расписания", required = true)
            @PathVariable long id) {
        try {
            boolean deleted = feedingScheduleService.deleteSchedule(id);
            return deleted ?
                    new ResponseEntity<>(HttpStatus.NO_CONTENT) :
                    new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/{id}/complete")
    @Operation(summary = "Отметить кормление как выполненное")
    public ResponseEntity<String> markScheduleCompleted(
            @Parameter(description = "ID расписания", required = true)
            @PathVariable long id) {
        try {
            String result = feedingScheduleService.markScheduleCompleted(id);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
} 