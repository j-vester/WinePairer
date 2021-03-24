package winepairer.domain;

import java.util.*;

public class Recipe {
    String name;
    List<String> ingredients = new ArrayList<String>();
    String instructions;

    public Recipe(String name, ArrayList<String> ingredients, String instructions) {
        this.name = name;
        this.ingredients = ingredients;
        this.instructions = instructions;
    }
    
}
