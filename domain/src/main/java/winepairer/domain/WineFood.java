package winepairer.domain;

public class WineFood {
    private Wine wine;
    private Food food;
    private boolean hasWineSpecs = false;
    private String wineSpecification = " ";
    private boolean hasFoodSpecs = false;
    private String foodSpecification = " ";

    public WineFood(Wine wine, Food food) {
        this.wine = wine;
        this.food = food;
        wine.addPairing(this);
        food.addPairing(this);
    }

    public void addWineSpecification(String specs) {
        this.wineSpecification += specs;
        this.hasWineSpecs = true;
    }

    public void addFoodSpecification(String specs) {
        this.foodSpecification += specs;
        this.hasFoodSpecs = true;
    }

    public Wine getWine() {
        return this.wine;
    }

    public String getWineName() {
        if (this.hasWineSpecs) {
            return this.wine.getName()+this.wineSpecification;
        } else {
            return this.wine.getName();
        }
    }

    public Food getFood() {
        return this.food;
    }

    public String getFoodName() {
        if (this.hasFoodSpecs) {
            return this.food.getName()+this.foodSpecification;
        } else {
            return this.food.getName();
        }
    }
}
