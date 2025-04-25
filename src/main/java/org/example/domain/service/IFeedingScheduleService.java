package org.example.domain.service;

import org.example.domain.FeedingSchedule;
import org.example.domain.valueobject.FeedingTime;
import org.example.domain.valueobject.FoodType;

import java.util.List;
import java.util.Optional;

public interface IFeedingScheduleService {

    List<FeedingSchedule> getAllSchedules();

    Optional<FeedingSchedule> findScheduleById(long id);

    List<FeedingSchedule> getSchedulesByAnimalId(long animalId);

    FeedingSchedule createSchedule(long animalId, FeedingTime feedingTime, FoodType food);

    String updateSchedule(long scheduleId, FeedingTime newTime, FoodType newFood);

    boolean deleteSchedule(long scheduleId);

    String markScheduleCompleted(long scheduleId);

    Optional<FeedingSchedule> findSchedule(long animalId, FeedingTime feedingTime);
}