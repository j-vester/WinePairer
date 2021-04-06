package winepairer.domain;

public class WineFood {
    private Wine wine;
    private Food food;
    private boolean wineSpecs = false;
    private String wineSpecification = " ";
    private boolean foodSpecs = false;
    private String foodSpecification = " ";

    public WineFood(Wine wine, Food food) {
        this.wine = wine;
        this.food = food;
        wine.addPairing(this);
        food.addPairing(this);
    }

    public void addWineSpecification(String specs) {
        this.wineSpecification += specs;
        this.wineSpecs = true;
    }

    public void addFoodSpecification(String specs) {
        this.foodSpecification += specs;
        this.foodSpecs = true;
    }

    public Wine getWine() {
        return this.wine;
    }

    public String getWineName() {
        if (this.wineSpecs) {
            return this.wine.getName()+this.wineSpecification;
        } else {
            return this.wine.getName();
        }
    }

    public Food getFood() {
        return this.food;
    }

    public String getFoodName() {
        if (this.foodSpecs) {
            return this.food.getName()+this.foodSpecification;
        } else {
            return this.food.getName();
        }
    }
}
