package winepairer.domain;

public enum WineColour {
    White("wit"), Red("rood"), Rose("rosé"), undefined("onbekend");

    private String colourDutch;

    private WineColour(String colourDutch) {
        this.colourDutch = colourDutch;
    }

    public String getDutchName() {
        return this.colourDutch;
    }
}
