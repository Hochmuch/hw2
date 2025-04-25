package org.example.application;

import org.example.domain.Animal;
import org.example.domain.FeedingSchedule;
import org.example.domain.service.IAnimalService;
import org.example.domain.service.IFeedingScheduleService;
import org.example.domain.valueobject.FeedingTime;
import org.example.domain.valueobject.FoodType;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FeedingScheduleService implements IFeedingScheduleService {

    private final FeedingOrganizationService feedingOrganizationService;
    private final IAnimalService animalService;

    public FeedingScheduleService(FeedingOrganizationService feedingOrganizationService,
                                IAnimalService animalService) {
        this.feedingOrganizationService = feedingOrganizationService;
        this.animalService = animalService;
    }

    @Override
    public List<FeedingSchedule> getAllSchedules() {
        return feedingOrganizationService.getAllSchedules();
    }

    @Override
    public Optional<FeedingSchedule> findScheduleById(long id) {
        return feedingOrganizationService.findById(id);
    }

    @Override
    public List<FeedingSchedule> getSchedulesByAnimalId(long animalId) {
        return feedingOrganizationService.getAllSchedules().stream()
                .filter(schedule -> schedule.getAnimal().getId() == animalId)
                .collect(Collectors.toList());
    }

    @Override
    public FeedingSchedule createSchedule(long animalId, FeedingTime feedingTime, FoodType food) {
        Optional<Animal> animalOpt = animalService.findAnimalById(animalId);
        
        if (animalOpt.isEmpty()) {
            throw new IllegalArgumentException("Животное с ID " + animalId + " не найдено");
        }
        
        return feedingOrganizationService.addFeedingSchedule(animalOpt.get(), feedingTime, food);
    }

    @Override
    public String updateSchedule(long scheduleId, FeedingTime newTime, FoodType newFood) {
        Optional<FeedingSchedule> scheduleOpt = feedingOrganizationService.findById(scheduleId);
        
        if (scheduleOpt.isEmpty()) {
            throw new IllegalArgumentException("Расписание кормления с ID " + scheduleId + " не найдено");
        }
        
        FeedingSchedule schedule = scheduleOpt.get();
        feedingOrganizationService.updateFeedingSchedule(schedule, newTime, newFood);
        
        return "Расписание для животного " + schedule.getAnimal().getName() + 
               " успешно обновлено. Новое время: " + newTime + ", новая еда: " + newFood;
    }

    public void updateSchedule(FeedingSchedule schedule, FeedingTime newTime, FoodType newFood) {
        if (!feedingOrganizationService.getAllSchedules().contains(schedule)) {
            throw new IllegalArgumentException("Расписание кормления не найдено");
        }
        
        feedingOrganizationService.updateFeedingSchedule(schedule, newTime, newFood);
    }

    @Override
    public boolean deleteSchedule(long scheduleId) {
        Optional<FeedingSchedule> scheduleOpt = feedingOrganizationService.findById(scheduleId);
        
        if (scheduleOpt.isEmpty()) {
            throw new IllegalArgumentException("Расписание кормления с ID " + scheduleId + " не найдено");
        }
        
        return feedingOrganizationService.removeFeedingSchedule(scheduleOpt.get());
    }

    @Override
    public String markScheduleCompleted(long scheduleId) {
        Optional<FeedingSchedule> scheduleOpt = feedingOrganizationService.findById(scheduleId);
        
        if (scheduleOpt.isEmpty()) {
            throw new IllegalArgumentException("Расписание кормления с ID " + scheduleId + " не найдено");
        }
        
        FeedingSchedule schedule = scheduleOpt.get();
        feedingOrganizationService.markFeedingCompleted(schedule);
        
        return "Расписание для животного " + schedule.getAnimal().getName() + 
               " отмечено как выполненное. Животное было покормлено едой: " + schedule.getFood();
    }

    @Override
    public Optional<FeedingSchedule> findSchedule(long animalId, FeedingTime feedingTime) {
        return feedingOrganizationService.getAllSchedules().stream()
                .filter(schedule -> schedule.getAnimal().getId() == animalId && 
                                  schedule.getFeedingTime().equals(feedingTime))
                .findFirst();
    }
} 