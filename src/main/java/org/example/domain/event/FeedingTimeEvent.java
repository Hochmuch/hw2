package org.example.domain.event;

import org.example.domain.FeedingSchedule;

public class FeedingTimeEvent extends AbstractDomainEvent {
    private final FeedingSchedule schedule;

    public FeedingTimeEvent(FeedingSchedule schedule) {
        super();
        this.schedule = schedule;
    }

    @Override
    public String toString() {
        return "FeedingTimeEvent: It's time to feed animal with " + schedule.getFood() + 
               " at " + getTimestamp();
    }
} 