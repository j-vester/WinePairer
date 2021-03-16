package winepairer.api.dto;

public class WinePairing {
    public WinePairing(winepairer.domain.WinePairer winepairer) {
        wine = winepairer.getWine();
        meal = winepairer.getMeal();
    }

    String wine;
    public String getWine() { return wine; }

    String meal;
    public String getMeal() { return meal; }
}