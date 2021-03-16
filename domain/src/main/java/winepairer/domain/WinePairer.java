package winepairer.domain;

public class WinePairer {
    private String wine;
    private String meal;

    public WinePairer(String mealToPairWith) {
        this.wine = "Chardonnay";
        this.meal = mealToPairWith;
    }

    public String getWine() {
        return this.wine;
    }

    public String getMeal() {
        return this.meal;
    }
}