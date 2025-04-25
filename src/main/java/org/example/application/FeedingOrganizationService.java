package org.example.application;

import org.example.domain.Animal;
import org.example.domain.FeedingSchedule;
import org.example.domain.event.FeedingTimeEvent;
import org.example.domain.valueobject.FeedingTime;
import org.example.domain.valueobject.FoodType;
import org.example.infrastructure.event.EventPublisher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class FeedingOrganizationService {
    
    private final List<FeedingSchedule> schedules = new ArrayList<>();
    private final EventPublisher eventPublisher;

    public FeedingOrganizationService(EventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    public FeedingSchedule addFeedingSchedule(Animal animal, FeedingTime feedingTime, FoodType food) {
        FeedingSchedule schedule = new FeedingSchedule(animal, feedingTime, food);
        schedules.add(schedule);
        return schedule;
    }

    public Optional<FeedingSchedule> findById(long id) {
        return schedules.stream()
                .filter(schedule -> schedule.getId() == id)
                .findFirst();
    }

    public void updateFeedingSchedule(FeedingSchedule schedule, FeedingTime newTime, FoodType newFood) {
        schedule.updateSchedule(newTime, newFood);
    }

    public boolean removeFeedingSchedule(FeedingSchedule schedule) {
        return schedules.remove(schedule);
    }

    public void markFeedingCompleted(FeedingSchedule schedule) {
        schedule.markDone();
        schedule.getAnimal().feed(schedule.getFood());
    }

    @Scheduled(fixedRate = 60000)
    public void checkSchedules() {
        LocalTime now = LocalTime.now();
        
        for (FeedingSchedule schedule : schedules) {
            if (!schedule.isDone() && isTimeToFeed(schedule.getFeedingTime().getValue(), now)) {
                eventPublisher.publish(new FeedingTimeEvent(schedule));
            }
        }
    }
    
    private boolean isTimeToFeed(LocalTime scheduleTime, LocalTime currentTime) {
        return Math.abs(scheduleTime.toSecondOfDay() - currentTime.toSecondOfDay()) < 300; // 5 minutes in seconds
    }

    public List<FeedingSchedule> getAllSchedules() {
        return new ArrayList<>(schedules);
    }
} 