package winepairer.domain;

import java.util.*;
public class Wine {
    private WineColour colour;
    private String name;
    private boolean hasDescription = false;
    private String description;
    private List<Wine> referrals = new ArrayList<>();
    private List<WineFood> pairings = new ArrayList<>();
    private List<Recipe> recipes = new ArrayList<>();

    public Wine(WineColour colour, String name) {
        this.colour = colour;
        this.name = name;
    }

    public void addDescription(String description) {
        this.description = description;
        this.hasDescription = true;
    }

    public void addPairing(WineFood pairing) {
        this.pairings.add(pairing);
    }

    public void addReferral(Wine wine) {
        this.referrals.add(wine);
    }
    
    public void addRecipes(List<Recipe> recipes) {
        this.recipes.addAll(recipes);
    }

    public WineColour getColour() {
        return this.colour;
    }
    
    public String getName() {
        return this.name;
    }

    public boolean hasDescription() {
        return hasDescription;
    }

    public String getDescription() {
        return this.description;
    }

    public List<Recipe> getRecipes() {
        return recipes;
    }

    public List<WineFood> getPairings() {
        return pairings;
    }
}