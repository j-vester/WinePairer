package winepairer.domain;

import java.util.*;

public enum WineColour {
    White("wit"), Red("rood"), Rose("rosé"), undefined("onbekend");

    private String colourDutch;
    private static final Map<String, WineColour> BY_LABEL = new HashMap<>();

    static {
        for (WineColour c: values()) {
            BY_LABEL.put(c.colourDutch, c);
        }
    }

    private WineColour(String colourDutch) {
        this.colourDutch = colourDutch;
    }

    public String getDutchName() {
        return this.colourDutch;
    }

    public static WineColour valueOfName(String name) {
        for (WineColour c : values()) {
            if (c.colourDutch.equals(name)) {
                return c;
            }
        }
        return null;
    }
}
