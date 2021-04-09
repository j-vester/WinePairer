package winepairer.api.dto;

public class Pairing {

    public Pairing(winepairer.domain.WineFood pairing) {
        this.wineName = pairing.getWineName() + ", " + pairing.getWine().getColour().getDutchName();
        if (pairing.getWine().hasDescription()) {
            this.description = pairing.getWine().getDescription();
        }
    }

    String wineName;
    public String getWineName() { return wineName; }

    String description;
    public String getDescription() { return description; }
    
}
