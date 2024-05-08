package com.java.csncl.nutritracker.Service;

import java.util.ArrayList;
import java.util.List;

import com.java.csncl.nutritracker.Model.Meals;

public class MealsServices {
    private List<Meals> mealsList = new ArrayList<>(){
        {
            add(new Meals(1L, "breakfast", "bread", 200));
            add(new Meals(2L, "lunch", "egg benny", 300));
            add(new Meals(3L, "dinner", "steak", 500));
        }

    };

    public List<Meals> getAllMeals() {
        return mealsList;
    }

    public int addMeals(Meals meal) {

        if (mealsList.contains(meal)) {
            System.out.println("found a duplicate");
            return -1;
        }
        mealsList.add(meal);
        return 0;
    }
}
