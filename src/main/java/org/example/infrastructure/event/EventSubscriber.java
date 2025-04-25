package org.example.infrastructure.event;

import org.example.domain.event.DomainEvent;

public interface EventSubscriber {
    void onEvent(DomainEvent event);

    boolean isInterestedIn(DomainEvent event);
} 