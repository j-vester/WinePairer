package winepairer.domain;

public class WinePairer {
    private String wine;
    private String meal;

    public WinePairer(String mealToPairWith) {
        this.wine = getPairing(mealToPairWith);
        this.meal = mealToPairWith;
    }

    private String getPairing(String meal) {
        String pairing;
        if (meal.equals("Zalm")) pairing = "Chardonnay";
        else pairing = "Onbekend";
        return pairing;
    }

    public String getWine() {
        return this.wine;
    }

    public String getMeal() {
        return this.meal;
    }
}