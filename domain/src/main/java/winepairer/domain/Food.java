package winepairer.domain;

import java.util.*;

public class Food {
    private String name;
    private Recipe recipe;
    private List<WineFood> pairings = new ArrayList<>();
    
    public Food(String name) {
        this.name = name;
    }

    public void addRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    public void addPairing(WineFood pairing) {
        this.pairings.add(pairing);
    }
    
    public String getName() {
        return this.name;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public List<WineFood> getPairings() {
        return pairings;
    }
} 
