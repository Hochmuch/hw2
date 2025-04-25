package org.example.infrastructure.event;

import org.example.domain.event.DomainEvent;
import org.example.domain.service.IEventPublisher;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Component
public class EventPublisher implements IEventPublisher {
    
    private final SimpleEventStore eventStore;
    private final List<EventSubscriber> subscribers = new CopyOnWriteArrayList<>();
    
    public EventPublisher(SimpleEventStore eventStore) {
        this.eventStore = eventStore;
    }

    @Override
    public void publish(DomainEvent event) {
        eventStore.add(event);

        System.out.println("Publishing event: " + event);

        for (EventSubscriber subscriber : subscribers) {
            if (subscriber.isInterestedIn(event)) {
                subscriber.onEvent(event);
            }
        }
    }
} 