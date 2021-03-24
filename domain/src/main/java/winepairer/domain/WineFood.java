package winepairer.domain;

public class WineFood {
    private Wine wine;
    private Food food;
    private String wineSpecification = " ";
    private String foodSpecification = " ";

    public WineFood(Wine wine, Food food) {
        this.wine = wine;
        this.food = food;
        wine.addPairing(this);
        food.addPairing(this);
    }

    public void addWineSpecification(String specs) {
        this.wineSpecification += specs;
    }

    public void addFoodSpecification(String specs) {
        this.foodSpecification += specs;
    }

    public Wine getWine() {
        return this.wine;
    }

    public String getWineName() {
        return this.wine.getName()+this.wineSpecification;
    }

    public Food getFood() {
        return this.food;
    }

    public String getFoodName() {
        return this.food.getName()+this.foodSpecification;
    }
}
