package winepairer.domain;

import java.util.*;

public class Recipe {
    String name;
    String size;
    List<String> ingredients;
    List<String> instructions;

    public Recipe(String name, String size, List<String> ingredients, List<String> instructions) {
        this.name = name;
        this.size = size;
        this.ingredients = ingredients;
        this.instructions = instructions;
    }
    
    public String getName() {
        return this.name;
    }

    public List<String> getIngredients() {
        return this.ingredients;
    }

    public List<String> getInstructions() {
        return this.instructions;
    }
}
