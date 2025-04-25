package org.example.domain;

import org.example.domain.valueobject.FeedingTime;
import org.example.domain.valueobject.FoodType;

public class FeedingSchedule {
    private long id;
    private Animal animal;
    private FeedingTime feedingTime;
    private FoodType food;
    private boolean done;

    public FeedingSchedule(Animal animal, FeedingTime feedingTime, FoodType food) {
        this.animal = animal;
        this.feedingTime = feedingTime;
        this.food = food;
        this.done = false;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void updateSchedule(FeedingTime newTime, FoodType newFood) {
        this.feedingTime = newTime;
        this.food = newFood;
        System.out.println("Расписание обновлено.");
    }

    public void markDone() {
        this.done = true;
        System.out.println("Кормление зверя " + animal + " произведено.");
    }

    public Animal getAnimal() {
        return animal;
    }

    public FeedingTime getFeedingTime() {
        return feedingTime;
    }

    public FoodType getFood() {
        return food;
    }

    public boolean isDone() {
        return done;
    }

    @Override
    public String toString() {
        return "FeedingSchedule{id=" + id + ", animal=" + animal.getName() + 
               ", feedingTime=" + feedingTime + ", food='" + food + "', done=" + done + "}";
    }
}
