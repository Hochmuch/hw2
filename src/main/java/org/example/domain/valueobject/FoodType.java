package org.example.domain.valueobject;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public final class FoodType {
    private final String value;

    private FoodType(String value) {
        this.value = validate(value);
    }

    private String validate(String value) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException("Тип пищи не может быть пустым");
        }
        return value.trim();
    }

    @JsonCreator
    public static FoodType of(String value) {
        return new FoodType(value);
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FoodType foodType = (FoodType) o;
        return Objects.equals(value, foodType.value);
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