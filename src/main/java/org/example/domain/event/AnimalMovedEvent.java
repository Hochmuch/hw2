package org.example.domain.event;

import org.example.domain.Animal;
import org.example.domain.Enclosure;
import org.example.domain.valueobject.EnclosureType;

import java.time.LocalDateTime;

public class AnimalMovedEvent implements DomainEvent {
    private static long eventIds = 1;
    private final long eventId;
    private final LocalDateTime timestamp;
    private final Animal animal;
    private final Enclosure sourceEnclosure;
    private final Enclosure targetEnclosure;
    private final EnclosureType sourceEnclosureType;
    private final EnclosureType targetEnclosureType;

    public AnimalMovedEvent(Animal animal, Enclosure sourceEnclosure, Enclosure targetEnclosure) {
        this.eventId = eventIds++;
        this.timestamp = LocalDateTime.now();
        this.animal = animal;
        this.sourceEnclosure = sourceEnclosure;
        this.targetEnclosure = targetEnclosure;
        this.sourceEnclosureType = sourceEnclosure != null ? sourceEnclosure.getType() : null;
        this.targetEnclosureType = targetEnclosure != null ? targetEnclosure.getType() : null;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public Animal getAnimal() {
        return animal;
    }

    
    public EnclosureType getSourceEnclosureType() {
        return sourceEnclosureType;
    }
    
    public EnclosureType getTargetEnclosureType() {
        return targetEnclosureType;
    }

    @Override
    public String toString() {
        return "AnimalMovedEvent{" +
                "eventId=" + eventId +
                ", timestamp=" + timestamp +
                ", animal=" + animal.getName() +
                ", sourceEnclosure='" + (sourceEnclosure != null ? sourceEnclosure : sourceEnclosureType) + '\'' +
                ", targetEnclosure='" + (targetEnclosure != null ? targetEnclosure : targetEnclosureType) + '\'' +
                '}';
    }
} 