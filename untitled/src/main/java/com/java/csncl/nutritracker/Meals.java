package com.java.csncl.nutritracker;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.Objects;
@Data
@AllArgsConstructor
public class Meals {

    private Long mealID;
    private String mealType;
    private String ingredients;
    private double quantity;

    public Meals(long l, String breakfast, String bread, int i) {

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Meals meal = (Meals) o;
        return mealID.equals(meal.mealID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mealID);
    }
}
