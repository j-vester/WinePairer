package winepairer.domain;

import java.util.*;

public class Food {
    private String name;
    private boolean gamechanger;
    private List<WineFood> pairings = new ArrayList<>();
    private List<Recipe> recipes = new ArrayList<>();
    
    public Food(String name, boolean gamechanger) {
        this.name = name;
        this.gamechanger = gamechanger;
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

    public boolean isGamechanger() {
        return gamechanger;
    }

    public List<Recipe> getRecipes() {
        return recipes;
    }

    public List<WineFood> getPairings() {
        return pairings;
    }
} 
