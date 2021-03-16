package winepairer.domain;

public class WinePairer {
    private String wine;
    private String meal;

    public WinePairer() {
        this.wine = "Chardonnay";
        this.meal = "Zalm";
    }

    public String getWine() {
        return this.wine;
    }

    public String getMeal() {
        return this.meal;
    }
}