package winepairer.domain;

import java.io.*;
public class WinePairer {
    private String wine;
    private String meal;

    public WinePairer(String mealToPairWith) {
        this.wine = getPairing(mealToPairWith);
        this.meal = mealToPairWith;
    }

    private String getPairing(String meal) {
        String pairing;
        if (meal.equals("Zalm")) {
            pairing = readChapterZ(meal);
        } else pairing = "Onbekend";
        return pairing;
    }

    private String readChapterZ(String meal) {
        String whiteWineSpanStart = "<span class=\"fft-romeinwit\">";
        String wine = "Niet gevonden";
        System.out.println("Printtest");
        File chapterZ = new File("../domain/winedine_contents/resource_29.html");
        if (!chapterZ.exists()) System.out.println(chapterZ.getAbsolutePath());
        try {
            BufferedReader br = new BufferedReader(new FileReader(chapterZ));
            String line;
            while ((line=br.readLine())!=null) {
                if (line.contains(meal.toLowerCase())) {
                    System.out.println("Meal gevonden");
                    break;
                }
            }
            br.close();
            int startFirstWhiteWineInd = line.indexOf(whiteWineSpanStart);
            int endFirstWhiteWineInd = line.indexOf("</span>", startFirstWhiteWineInd);
            System.out.println("startindex: "+startFirstWhiteWineInd+" endindex: "+endFirstWhiteWineInd);
            wine = line.substring(startFirstWhiteWineInd+whiteWineSpanStart.length(), endFirstWhiteWineInd);
            wine = wine.substring(0, 1).toUpperCase()+wine.substring(1);
        } catch (FileNotFoundException e) {
            System.out.println(e.toString());
        } catch (IOException e) {
            System.out.println(e.toString());
        }
        return wine;
    }

    public String getWine() {
        return this.wine;
    }

    public String getMeal() {
        return this.meal;
    }
}