package org.example.domain.event;

import java.time.LocalDateTime;


public interface DomainEvent {
    LocalDateTime getTimestamp();
} 