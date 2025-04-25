package org.example.domain.valueobject;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public final class FeedingTime {
    private final LocalTime value;
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    private FeedingTime(LocalTime value) {
        this.value = Objects.requireNonNull(value, "Время кормления не может быть null");
    }

    public static FeedingTime of(LocalTime value) {
        return new FeedingTime(value);
    }

    public static FeedingTime fromString(String value) {
        return new FeedingTime(LocalTime.parse(value, FORMATTER));
    }

    public LocalTime getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FeedingTime that = (FeedingTime) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return value.format(FORMATTER);
    }
} 