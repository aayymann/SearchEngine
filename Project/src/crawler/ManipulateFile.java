package crawler;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class ManipulateFile {
    static  String [] ReadUrlSeeds(int compareWith) {
        String websiteURL="";
        int counter=0;
        String [] arr = new String[2];
        File filesToBeCrawled = new File("./urlSeeds.txt");
        if (!filesToBeCrawled.exists()) {
            System.out.println("Seeds files don't exist!!!");
            arr[0]="";
            arr[1]="0";
            return arr;
        }
        try {
            Scanner myReader = new Scanner(filesToBeCrawled);
            while (myReader.hasNextLine()) {
                if (compareWith == counter){
                    websiteURL = myReader.nextLine();
                    break;
                }
                myReader.nextLine();
                counter++;
            }
            compareWith = counter + 1;
        } catch (FileNotFoundException ex) {}
        arr[0]=websiteURL;
        arr[1] = String.valueOf(compareWith);
        return arr;
    }
}
