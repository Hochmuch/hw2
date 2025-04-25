package org.example.application;

import org.example.domain.event.AnimalMovedEvent;
import org.example.domain.event.DomainEvent;
import org.example.domain.event.FeedingTimeEvent;
import org.example.domain.valueobject.EnclosureType;
import org.example.infrastructure.event.EventSubscriber;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ZooStatisticsService implements EventSubscriber {

    private int totalAnimals = 0;
    private final Map<String, Integer> animalsBySpecies = new HashMap<>();
    private final Map<Boolean, Integer> animalsByGender = new HashMap<>();
    private final Map<EnclosureType, Integer> enclosureUtilization = new HashMap<>();
    private final List<DomainEvent> recentEvents = new ArrayList<>();
    private int animalMovementCount = 0;
    private int feedingCount = 0;

    public ZooStatisticsService() {
        animalsByGender.put(true, 0);
        animalsByGender.put(false, 0);
    }

    @Override
    public void onEvent(DomainEvent event) {
        recentEvents.add(0, event);
        if (recentEvents.size() > 100) {
            recentEvents.remove(recentEvents.size() - 1);
        }
        
        if (event instanceof AnimalMovedEvent) {
            handleAnimalMovedEvent((AnimalMovedEvent) event);
        } else if (event instanceof FeedingTimeEvent) {
            handleFeedingTimeEvent((FeedingTimeEvent) event);
        }
    }
    
    private void handleAnimalMovedEvent(AnimalMovedEvent event) {
        animalMovementCount++;

        EnclosureType sourceType = event.getSourceEnclosureType();
        EnclosureType targetType = event.getTargetEnclosureType();
        
        if (sourceType != null) {
            enclosureUtilization.put(sourceType, enclosureUtilization.getOrDefault(sourceType, 0) - 1);
        }
        
        if (targetType != null) {
            enclosureUtilization.put(targetType, enclosureUtilization.getOrDefault(targetType, 0) + 1);
        }
    }
    
    private void handleFeedingTimeEvent(FeedingTimeEvent event) {
        feedingCount++;
    }

    @Override
    public boolean isInterestedIn(DomainEvent event) {
        return event instanceof AnimalMovedEvent || event instanceof FeedingTimeEvent;
    }
} 