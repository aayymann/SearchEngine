package crawler;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.Scanner;
import java.io.IOException;

public class ManipulateFile {
    static  String [] ReadFile(String filePath,int compareWith,boolean isPcFile) {
        String websiteURL="";
        int counter=0;
        String [] arr = new String[2];
        File filesToBeCrawled = new File(filePath);
        if (!filesToBeCrawled.exists()) {
            System.out.println("Seeds files don't exist!!!");
            arr[0]="";
            arr[1]="0";
            return arr;
        }
        //--Read the program counter from the programCounter file
        if(isPcFile){
            System.out.println("Hereee");
            try{
                File pcFile = new File(filePath);
                Scanner myReader = new Scanner(pcFile);
                if(myReader.hasNextLine()){
                    arr[0]="";
                    arr[1]=myReader.nextLine();
                    return arr;
                }
            }
            catch(FileNotFoundException ex){}
        }
        //
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
    static void WriteInPCFile(String filePath,int pc){
        try{
            FileWriter myWriter = new FileWriter(filePath);
            String pcString = String.valueOf(pc);
            myWriter.write(pcString);
            myWriter.close();
        }
        catch(IOException e){
            System.out.println("Could not write in the program counter file");
        }
    }
}
