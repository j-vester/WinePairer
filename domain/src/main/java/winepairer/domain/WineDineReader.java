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
    private static final String RECIPE_TEXT_ID = "<p class=\"Kader_ff-kadertekst\"";
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
            findAllDiscussedWines(findWineChapters(contents));
            // Then find the chapter with references to other wines for the wines that are not discussed in the book
            findAllReferencedWines(findWineOverview(contents));
            System.out.println("Aantal resources in dit book: "+contents.size());
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        
    }
    
    private List<String> findWineChapters(List<Resource> contents) throws IOException {
        List<String> wineChapters = new ArrayList<>();
        for (Resource content:contents) {
            String readableContent = content.getData().toString();
            if (readableContent.contains(WINECHAPTER_ID)) {
                wineChapters.add(readableContent);
            }
        }
        return wineChapters;
    }

    private String findWineOverview(List<Resource> contents) throws IOException {
        String overviewChapter = "";
        for (Resource content:contents) {
            String readableContent = content.getData().toString();
            if (readableContent.contains(WINEOVERVIEW_ID)) {
                overviewChapter = readableContent;
                break;
            }
        }
        return overviewChapter;
    }

    private void findAllDiscussedWines(List<String> wineChapters) {
        for (String chapter:wineChapters) {
            int startOfParagraph = chapter.indexOf(WINEPARAGRAPH_ID);
            while (startOfParagraph >= 0) {
                startOfParagraph += WINEPARAGRAPH_ID.length();
                int endOfParagraph = chapter.indexOf(WINEPARAGRAPH_ID, startOfParagraph);
                String paragraph;
                if (endOfParagraph >= 0) {
                    paragraph = chapter.substring(startOfParagraph, endOfParagraph);
                } else {
                    paragraph = chapter.substring(startOfParagraph);
                }
                paragraph = removeIrrelevantParts(paragraph);
                Wine wine = makeWineObject(paragraph);
                wines.put(wine.getName(), wine);
                wineTexts.put(wine.getName(), paragraph);
                startOfParagraph = chapter.indexOf(WINEPARAGRAPH_ID, endOfParagraph);
            }
        }
    }

    private void findAllReferencedWines(String overviewChapter) {
        int startOfGrape = overviewChapter.indexOf(GRAPE_ID);
        while (startOfGrape >= 0) {
            startOfGrape += GRAPE_ID.length();
            int endOfGrape = overviewChapter.indexOf("</p>", startOfGrape);
            String grape = overviewChapter.substring(startOfGrape, endOfGrape);
            if (!grape.contains(GRAPE_DISCUSSED_ID)) {
                String[] grapeSplitted = grape.split(", zie ");
                String grapeName = grapeSplitted[0];
                String[] grapesReferredTo = grapeSplitted[1].split(",\\s|\\sen\\s");
                // only refer to the first wine for now
                Wine wineReferredTo = wines.get(grapesReferredTo[0]);
                Wine wine = new Wine(wineReferredTo.getColour(), grapeName);
                wine.addReferral(wineReferredTo);
                wines.put(wine.getName(), wine);
            }
            startOfGrape = overviewChapter.indexOf(GRAPE_ID, endOfGrape);
        }
    }

    private String removeIrrelevantParts(String par) {
        StringBuilder newPar = new StringBuilder(par);
        int irrelevantStartInd = newPar.indexOf(REMOVE_PARAGRAPH_ID);
        while (irrelevantStartInd >= 0) {
            int irrelevantEndInd = newPar.indexOf("</p>", irrelevantStartInd);
            newPar.delete(irrelevantStartInd, irrelevantEndInd);
            irrelevantStartInd = newPar.indexOf(REMOVE_PARAGRAPH_ID);
        }
        int endOfFile = newPar.indexOf("</div>");
        if (endOfFile >= 0) {
            newPar.delete(endOfFile, newPar.length());
        }
        return newPar.toString();
    }

    private Wine makeWineObject(String par) {
        WineColour colour = WineColour.undefined;
        String name = "";
        if (par.contains(WHITEWINEPARAGRAPH_ID)) {
            colour = WineColour.White;
            int startName = par.indexOf(WHITEWINEPARAGRAPH_ID)+WHITEWINEPARAGRAPH_ID.length();
            int endName = par.indexOf("</span>",startName);
            name = par.substring(startName, endName);
        } else if (par.contains(REDWINEPARAGRAPH_ID)) {
            colour = WineColour.Red;
            int startName = par.indexOf(REDWINEPARAGRAPH_ID)+REDWINEPARAGRAPH_ID.length();
            int endName = par.indexOf("</span>",startName);
            name = par.substring(startName, endName);
        } else if (par.contains(ROSEWINEPARAGRAPH_ID)) {
            colour = WineColour.Rose;
            int startName = par.indexOf(ROSEWINEPARAGRAPH_ID)+ROSEWINEPARAGRAPH_ID.length();
            int endName = par.indexOf("</span>",startName);
            name = par.substring(startName, endName);
        }
        if (par.contains(WINE_DESCRIPTION_ID)) {
            int startDescription = par.indexOf(WINE_DESCRIPTION_ID)+WINE_DESCRIPTION_ID.length();
            int endDescription = par.indexOf("</p>",startDescription);
            String description = par.substring(startDescription, endDescription);
            return new Wine(colour, name, description);
        } else {
            return new Wine(colour, name);
        }
    }
}
