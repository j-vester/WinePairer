package winepairer.domain;

import java.util.*;

public class Food {
    private String name;
    private List<WineFood> pairings = new ArrayList<>();
    private List<Recipe> recipes = new ArrayList<>();
    
    public Food(String name) {
        this.name = name;
    }

    public void addRecipes(List<Recipe> recipes) {
        this.recipes.addAll(recipes);
    }

    public void addPairing(WineFood pairing) {
        this.pairings.add(pairing);
    }
    
    public String getName() {
        return this.name;
    }

    public List<Recipe> getRecipes() {
        return recipes;
    }

    public List<WineFood> getPairings() {
        return pairings;
    }
} 
