package winepairer.api.dto;

public class Food {
    
    public Food(winepairer.domain.Food food, boolean sameAsInput) {
        this.sameAsInput = sameAsInput;
        this.name = food.getName();
        this.gamechanger = food.isGamechanger();
        this.pairings = new Pairing[food.getPairings().size()];
        for (int i=0; i<food.getPairings().size(); i++) {
            this.pairings[i] = new Pairing(food.getPairings().get(i));
        }
    }

    String name;
    public String getName() { return name; }

    boolean gamechanger;
    public boolean isGamechanger() { return gamechanger; }

    boolean sameAsInput;
    public boolean isSameAsInput() { return sameAsInput; }

    Pairing[] pairings;
    public Pairing[] getPairings() { return pairings; }
    
}
