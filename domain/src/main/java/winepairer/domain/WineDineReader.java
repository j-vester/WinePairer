package winepairer.domain;

import nl.siegmann.epublib.epub.*;
import nl.siegmann.epublib.domain.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import java.util.*;

public class WineDineReader {
    private static final String EPUB_LOC = "./domain/Victoria_Moore.epub";
    // Identifiers for specific information in the text.
    private static final String INGREDIENTCHAPTER_ID = "<span class=\"Schreefloos_slt-vetwijnm\"";
    private static final String WHITEWINE_ID = "<span class=\"fft-romeinwit\"";
    private static final String REDWINE_ID = "<span class=\"fft-romeinrood\"";
    private static final String ROSEWINE_ID = "<span class=\"fft-romeinrose\"";
    private static final String INGREDIENT_ID = "<span class=\"Schreefloos_slt-vet\"";
    private static final String SUBINGREDIENT_ID = "<p class=\"ff-tussenkop\"><span class=\"fft-vet\"";
    private static final String SPECIFICATION_ID = "<p class=\"ff-platmetwit\"><span class=\"Schreefloos_slt-romein";
    private static final String REFERENCE_ID = "<p class=\"ff-kaderalinea\"><span class=\"Schreefloos_slt-romein\"";
    private static final String GAMECHANGER_ID = "<span class=\"Schreefloos_slt-kkromeingamechanger\">GAMECHANGER</span";
    private static final String RECIPE_NAME_ID = "<p class=\"Kader_ff-kaderkop\"";
    private static final String RECIPE_INGR_ID = "<p class=\"Kader_ff-kaderlijst\"";
    private static final String RECIPE_TEXT_ID = "<p class=\"Kader_ff-kadertekst";
    private static final String WINEOVERVIEW_ID = "Wijndruiven en wijnen";
    private static final String GRAPE_ID = "<p class=\"ff-hangend\">";
    private static final String GRAPE_DISCUSSED_ID = "<span class=\"fft-vet\"";
    private static final String WINECHAPTER_ID = "<span class=\"Schreefloos_slt-vetdarkred\"";
    private static final String WINEPARAGRAPH_ID = "<p class=\"ff-kopklein\">";
    private static final String WHITEWINEPARAGRAPH_ID = "<span class=\"Schreefloos_slt-vetwit\">";
    private static final String REDWINEPARAGRAPH_ID = "<span class=\"Schreefloos_slt-vetrood\">";
    private static final String ROSEWINEPARAGRAPH_ID = "<span class=\"Schreefloos_slt-vetrose\">";
    private static final String WINE_DESCRIPTION_ID = "<span class=\"fft-cursief\">hoe hij smaakt: </span>";
    private static final String REMOVE_PARAGRAPH_ID = "<p class=\"ff-blok";
    private static final String DUTCH_INGREDIENTCHAPTER_ID = "producten en gerechten uit de Lage Landen";
    // HTML codes for whitespace 
    private static final String WHITESPACE_START = "&#";
    private static final String WHITESPACE_END = ";";
    // Start and end of HTML tags
    private static final String[] HTML = {"<",">"};

    private Map<String,String> wineTexts = new HashMap<String,String>();
    private Map<String,Wine> wines = new HashMap<String,Wine>();
    private Map<String,Food> foods = new HashMap<String,Food>();

    public WineDineReader() throws FileNotFoundException, IOException {
        EpubReader eReader = new EpubReader();
        try {
            File eBook = new File(EPUB_LOC);
            Book wineDine = eReader.readEpub(new FileInputStream(eBook));
            List<Resource> contents = wineDine.getContents();
            // First find all wines discussed in the book
            findAllDiscussedWines(findChapters(contents, WINECHAPTER_ID));
            // Then find the chapter with references to other wines for the wines that are not discussed in the book
            findAllReferencedWines(findChapters(contents, WINEOVERVIEW_ID));
            // Find all discussed foods and corresponding winepairings
            findAllFoodsWithWines(findChapters(contents, INGREDIENTCHAPTER_ID));
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        
    }

    private List<String> findChapters(List<Resource> contents, String identifier) throws IOException {
        List<String> chapters = new ArrayList<>();
        for (Resource content:contents) {
            String readableContent = content.getData().toString();
            if (readableContent.contains(identifier)) {
                removeIrrelevantParts(readableContent);
                chapters.add(readableContent);
            }
        }
        return chapters;
    }

    private String removeIrrelevantParts(String par) {
        // remove start of html file
        par = removeAllParts(par, "<?xml", "<div class=\"ffo-newpage\">");
        // remove paragraphs of winemakers 
        par = removeAllParts(par, REMOVE_PARAGRAPH_ID, "</p>");
        // remove end of html file
        par = removeAllParts(par, "</div>", "</html>");
        // remove html whitespaces
        par = removeAllParts(par, WHITESPACE_START, WHITESPACE_END);
        return par;
    }

    private void findAllDiscussedWines(List<String> wineChapters) {
        for (String chapter:wineChapters) {
            List<String> paragraphs = obtainAllParts(chapter, WINEPARAGRAPH_ID, WINEPARAGRAPH_ID);
            for (String paragraph:paragraphs) {
                Wine wine = makeWineObject(paragraph);
                wines.put(wine.getName(), wine);
                wineTexts.put(wine.getName(), paragraph);
            }
        }
    }

    private void findAllReferencedWines(List<String> overviewChapter) {
        for (String chapter:overviewChapter) {
            List<String> grapes = obtainAllParts(chapter, GRAPE_ID, "</p>");
            for (String grape:grapes) {
                if (!grape.contains(GRAPE_DISCUSSED_ID)) {
                    String[] grapeSplitted = grape.split(", zie ");
                    String grapeName = grapeSplitted[0];
                    String[] grapesReferredTo = grapeSplitted[1].split(",\\s|\\sen\\s");
                    // only refer to the first wine for now
                    Wine wineReferredTo;
                    // some exceptions of mistakes in the book 
                    if (grapeName == "calatayud" || grapesReferredTo[0] == "grenache noir") {
                        wineReferredTo = wines.get("grenache");
                    } else {
                        wineReferredTo = wines.get(grapesReferredTo[0]);
                    }
                    Wine wine = new Wine(wineReferredTo.getColour(), grapeName);
                    wine.addReferral(wineReferredTo);
                    wines.put(wine.getName(), wine);
                }
            }
        }
    }

    private void findAllFoodsWithWines(List<String> foodChapters) {
        for (String chapter:foodChapters) {

        }
    }

    private Wine makeWineObject(String par) {
        WineColour colour;
        String name;
        List<String> white = obtainAllParts(par, WHITEWINEPARAGRAPH_ID, "</span>", HTML);
        List<String> red = obtainAllParts(par, REDWINEPARAGRAPH_ID, "</span>", HTML);
        List<String> rose = obtainAllParts(par, ROSEWINEPARAGRAPH_ID, "</span>", HTML);
        if (!white.isEmpty()) {
            colour = WineColour.White;
            name = white.get(0);
            // exception for the book name "sauternes en andere zoete wijnen van sauvignong blanc en/of sémillon"
            if (name.contains("sauternes")) name = "sauternes";
        } else if (!red.isEmpty()) {
            colour = WineColour.Red;
            name = red.get(0);
        } else {
            colour = WineColour.Rose;
            name = rose.get(0);
        }
        Wine wine = new Wine(colour, name);;
        List<String> description = obtainAllParts(par, WINE_DESCRIPTION_ID, "</p>", HTML);
        if (!description.isEmpty()) {
            wine.addDescription(description.get(0));
        }
        List<Recipe> recipes = obtainAllRecipes(par);
        if (!recipes.isEmpty()){
            wine.addRecipes(recipes);
        }
        return wine;
    }

    private List<Recipe> obtainAllRecipes(String str) {
        List<Recipe> recipes = new ArrayList<>();
        // Split the string such that at most one recipe is written in each part
        // Use the lookahead regex code to include the recipe name identifier in the splitted Strings
        String[] splitAtRecipes = str.split("(?="+RECIPE_NAME_ID+")");
        if (splitAtRecipes.length >= 2) {
            for (int i=1; i<splitAtRecipes.length; i++) {
                String name = obtainAllParts(splitAtRecipes[i], RECIPE_NAME_ID, "</p>", HTML).get(0);
                List<String> text = obtainAllParts(splitAtRecipes[i], RECIPE_TEXT_ID, "</p>", HTML);
                String recipeSize = text.remove(0);
                List<String> ingredients = obtainAllParts(splitAtRecipes[i], RECIPE_INGR_ID, "</p>", HTML);
                recipes.add(new Recipe(name, recipeSize, ingredients, text));
            }
        }
        return recipes;
    }

    private List<String> obtainAllParts(String str, String startOfPart, String endOfPart, String ... removeArgs) {
        List<String> parts = new ArrayList<>();
        int startInd = str.indexOf(startOfPart);
        while (startInd >= 0) {
            int endInd = str.indexOf(endOfPart, startInd+1);
            String part;
            if (endInd >= 0) {
                part = str.substring(startInd, endInd+endOfPart.length());
                startInd = str.indexOf(startOfPart, endInd);
            } else {
                part = str.substring(startInd);
                startInd = -1;
            }
            if (removeArgs.length > 0) {
                part = removeAllParts(part, removeArgs[0], removeArgs[1]);
            }
            parts.add(part);
        }
        return parts;
    }

    private String removeAllParts(String str, String startOfPart, String endOfPart) {
        StringBuilder remover = new StringBuilder(str);
        int startInd = remover.indexOf(startOfPart);
        while (startInd >= 0) {
            int endInd = remover.indexOf(endOfPart)+endOfPart.length();
            remover.delete(startInd, endInd);
            startInd = remover.indexOf(startOfPart);
        }
        return remover.toString();
    }
}
