package org.example.infrastructure.event;

import org.example.domain.event.DomainEvent;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Component
public class SimpleEventStore {
    
    private final List<DomainEvent> events = new CopyOnWriteArrayList<>();

    public void add(DomainEvent event) {
        events.add(event);
    }

} 