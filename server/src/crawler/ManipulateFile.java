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
            System.out.println("Could not read a url seed from " + filePath);
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
            System.out.println("Could not write in number in file");
        }
    }

    static void WriteInFile(String filePath, String toBeAddedString) {
        try {
            toBeAddedString += "\n";
            FileWriter myWriter = new FileWriter(filePath);
            myWriter.write(toBeAddedString);
            myWriter.close();
        } catch (IOException e) {
            System.out.println("Could not write a string in file");
        }
    }

    public static int ReadNumberFromFile(String filePath) {
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
                if (searchedForString.equals(readLine) || tempSearchedFor.equals(readLine)) {
                    return true;
                }
            }
        } catch (FileNotFoundException ex) {
            System.out.println("An Error happened while searching for a string in the url seeds file");
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

    static void CreateWebsiteFile(int websiteIndex, String websiteContent, int threadInst) {
        String myPath = "./out/websites/" + "Crawler" + String.valueOf(threadInst);
        String myPath2 = "./out/websites/" + "Crawler" + String.valueOf(threadInst) + "/" + String.valueOf(websiteIndex)
                + ".txt";
        try {
            File f1 = new File(myPath);
            f1.mkdir();
            FileWriter myWriter = new FileWriter(myPath2);
            myWriter.write(websiteContent);
            myWriter.close();
        } catch (IOException ex) {
            System.out.println("Failed to Write the generated HTML String");
        }
    }

    static void CreateHyperlinksFile(int websiteIndex, String websiteContent, int threadInst) {
        String myPath = "./out/hyperlinks/" + "Crawler" + String.valueOf(threadInst);
        String myPath2 = "./out/hyperlinks/" + "Crawler" + String.valueOf(threadInst) + "/"
                + String.valueOf(websiteIndex) + ".txt";
        try {
            File f1 = new File(myPath);
            f1.mkdir();
            FileWriter myWriter = new FileWriter(myPath2);
            myWriter.write(websiteContent);
            myWriter.close();
        } catch (IOException ex) {
            System.out.println("Failed to Write the hyperlinks");
        }
    }

    static int GetNumberOfUrlSeeds(String urlSeedsPath) {
        int num = 0;
        try {
            File fileToBeCrawled = new File(urlSeedsPath);
            Scanner myReader = new Scanner(fileToBeCrawled);
            while (myReader.hasNextLine()) {
                if (myReader.nextLine().length() <= 2)
                    break;
                num++;
            }
        } catch (Exception ex) {
            System.out.println("File Could not br opened");
        }
        return num;
    }
}
