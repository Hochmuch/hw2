package org.example.domain.service;

import org.example.domain.event.DomainEvent;

public interface IEventPublisher {

    void publish(DomainEvent event);

} 