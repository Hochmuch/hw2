package org.example.domain.valueobject;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public final class Species {
    private final String value;

    private Species(String value) {
        this.value = validateAndNormalize(value);
    }

    private String validateAndNormalize(String value) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException("Вид животного не может быть пустой строкой");
        }
        return value.substring(0, 1).toUpperCase() + value.substring(1).toLowerCase();
    }

    @JsonCreator
    public static Species of(String value) {
        return new Species(value);
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Species species = (Species) o;
        return Objects.equals(value, species.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return value;
    }
} 