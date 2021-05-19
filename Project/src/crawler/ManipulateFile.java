package crawler;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Scanner;
import java.io.IOException;

public class ManipulateFile {
    static String[] ReadFile(String filePath, int compareWith) {
        String websiteURL = "";
        int counter = 0;
        String[] arr = new String[2];
        File filesToBeCrawled = new File(filePath);
        if (!filesToBeCrawled.exists()) {
            System.out.println("Seeds files don't exist!!!");
            arr[0] = "";
            arr[1] = "0";
            return arr;
        }
        //
        try {
            Scanner myReader = new Scanner(filesToBeCrawled);
            while (myReader.hasNextLine()) {
                if (compareWith == counter) {
                    websiteURL = myReader.nextLine();
                    break;
                }
                myReader.nextLine();
                counter++;
            }
            compareWith = counter + 1;
        } catch (FileNotFoundException ex) {
        }
        arr[0] = websiteURL;
        arr[1] = String.valueOf(compareWith);
        return arr;
    }

    static void WriteNumberInFile(String filePath, int pc) {
        try {
            FileWriter myWriter = new FileWriter(filePath);
            String pcString = String.valueOf(pc);
            myWriter.write(pcString);
            myWriter.close();
        } catch (IOException e) {
            System.out.println("Could not write in the program counter file");
        }
    }

    static int ReadNumberFromFile(String filePath) {
        int pcCounter = -1;
        try {
            File pcFile = new File(filePath);
            Scanner myReader = new Scanner(pcFile);
            if (myReader.hasNextLine()) {
                pcCounter = Integer.parseInt(myReader.nextLine());
            }
        } catch (FileNotFoundException ex) {
        }
        return pcCounter;
    }

    static boolean IsStringPresent(String filePath, String searchedForString) {
        File filesToBeCrawled = new File(filePath);
        try {
            String tempSearchedFor = searchedForString + "/";
            Scanner myReader = new Scanner(filesToBeCrawled);
            while (myReader.hasNextLine()) {
                String readLine = myReader.nextLine();
                if (readLine.equals(searchedForString) || readLine.equals(tempSearchedFor))
                    return true;
            }
        } catch (FileNotFoundException ex) {
        }
        return false;
    }

    static void AppendOnFile(String filePath, String toBeAddedString) {
        toBeAddedString += "\n";
        try {
            Files.write(Paths.get(filePath), toBeAddedString.getBytes(), StandardOpenOption.APPEND);
        } catch (IOException e) {
            System.out.println("FAILURE TO APPEND ON FILE");
        }
    }
}
