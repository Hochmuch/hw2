package org.example.domain.event;

import java.time.LocalDateTime;

public abstract class AbstractDomainEvent implements DomainEvent {
    private final LocalDateTime timestamp;

    protected AbstractDomainEvent() {
        this.timestamp = LocalDateTime.now();
    }

    @Override
    public LocalDateTime getTimestamp() {
        return timestamp;
    }
} 