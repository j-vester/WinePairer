package winepairer.domain;

import java.util.*;
public class Wine {
    private WineColour colour;
    private String name;
    private String description;
    private List<Wine> referrals;
    private List<WineFood> pairings = new ArrayList<WineFood>();
    private Recipe recipe;

    public Wine(WineColour colour, String name) {
        this.colour = colour;
        this.name = name;
    }

    public Wine(WineColour colour, String name, String description) {
        this(colour, name);
        this.description = description;
    }

    public void addPairing(WineFood pairing) {
        this.pairings.add(pairing);
    }

    public void addReferral(Wine wine) {
        this.referrals.add(wine);
    }
    
    public void addRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    public WineColour getColour() {
        return this.colour;
    }
    
    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public List<WineFood> getPairings() {
        return pairings;
    }
}