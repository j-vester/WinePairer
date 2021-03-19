package winepairer.domain;

import nl.siegmann.epublib.epub.*;
import nl.siegmann.epublib.domain.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import java.util.List;

public class WineDineReader {
    public static void main(String[] args) throws FileNotFoundException, IOException {
        EpubReader eReader = new EpubReader();
        FileOutputStream fos = null;
        try {
            File eBook = new File("./domain/Victoria_Moore.epub");
            Book wineDine = eReader.readEpub(new FileInputStream(eBook));
            new File("./domain/winedine_contents").mkdir();
            List<Resource> contents = wineDine.getContents();
            for (int i=0; i<contents.size(); i++) {
                File contentFile = new File("./domain/winedine_contents/resource_"+i+".html");
                contentFile.createNewFile();
                fos = new FileOutputStream(contentFile);
                fos.write(contents.get(i).getData());
                fos.close();
            }
            System.out.println("Aantal resources in dit book: "+contents.size());
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
