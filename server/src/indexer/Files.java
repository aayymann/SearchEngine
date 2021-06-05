package indexer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

  class Files {
      static String ReadFile(String filePath){
        String retHTML="";
        File filesToBeCrawled = new File(filePath);
        if (!filesToBeCrawled.exists()) {
            System.out.println("File is not here!!!");
        }
        try {
            Scanner myReader = new Scanner(filesToBeCrawled);
            while (myReader.hasNextLine()) {
                retHTML += myReader.nextLine();
            }
        } catch (FileNotFoundException ex) {
            System.out.println("Could not read from" + filePath);
        }
        return retHTML;
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
    static ArrayList<String> FetchingFromCrawlerFiles(String[] pathToCrawlerHTML,String [] crawlersArr , int numOfCrawlers, int []fileCountArr){
        ArrayList<String> data = new ArrayList<String>();
        for(int i=0 ; i<numOfCrawlers ; i++){
            String tempPathToCrawlerHTML= pathToCrawlerHTML[i];
            for(int j=0 ;j <fileCountArr[i] ; j++){
                pathToCrawlerHTML[i]+=String.valueOf(j)+".txt";
                //
                   data.add(ReadFile(pathToCrawlerHTML[i]));
                //
                //System.out.println(pathToCrawlerHTML[i]);
                pathToCrawlerHTML[i]=tempPathToCrawlerHTML;
            }
        }
        return data;
    }
}
