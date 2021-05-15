package crawler;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class ManipulateFile {
    static void ReadUrlSeeds(ArrayList<String> arrUrls) {
        File filesToBeCrawled = new File("./urlSeeds.txt");
        if (!filesToBeCrawled.exists()) {
            System.out.println("Seeds files don't exist!!!");
            return;
        }
        try {
            Scanner myReader = new Scanner(filesToBeCrawled);
            while (myReader.hasNextLine()) {
                arrUrls.add(myReader.nextLine());
            }
        } catch (FileNotFoundException ex) {}
    }
}
