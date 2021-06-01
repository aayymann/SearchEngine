package crawler;

import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main (String[] args)  throws Exception  {
        int numOfCrawlers=1;
        Scanner scanner = new Scanner(System.in);
        System.out.println("How many Crawler threads do you want to generate? (Default is 1)");
        try{
            numOfCrawlers=scanner.nextInt();
            //--Num of crawlers has to be at most equal to the number of url links in the url seeds file
            int numOfOriginalSeeds = ManipulateFile.GetNumberOfUrlSeeds("./urlSeeds.txt");
            while(numOfCrawlers> numOfOriginalSeeds)
                numOfCrawlers--;
            //Create numOfCrawlers seeds files and numOfCrawlers pc files for each crawler thread to read from
            String seedsFilePath = "./urlSeeds";
            String pcPath = "./pc";
            String []pcPathArr = new String[numOfCrawlers];
            String []seedsFilePathArr= new String[numOfCrawlers];
            int incrementer = numOfOriginalSeeds/numOfCrawlers;
            int [][] arrOfPC =  new int [numOfCrawlers][2];
            int begin =0 ;
            for(int i=0 ; i< numOfCrawlers ; i++){
                seedsFilePathArr[i]=seedsFilePath+String.valueOf(i)+".txt";
                pcPathArr[i]=pcPath+String.valueOf(i)+".txt";
                if(i == numOfCrawlers-1){
                    System.out.println("The i is "+ i);
                    arrOfPC[i][0]=begin;
                    arrOfPC[i][1] =numOfOriginalSeeds;
                }
                else{
                    arrOfPC[i][0]= begin;
                    arrOfPC[i][1] = begin+incrementer;
                    begin = begin+incrementer;
                }
            }
            for(int i=0 ; i <numOfCrawlers ; i++){
                ManipulateFile.WriteNumberInFile(pcPathArr[i] , arrOfPC[i][0]);
                System.out.println(arrOfPC[i][0]);
                System.out.println(arrOfPC[i][1]);
                System.out.println("---------------------");
            }
            //Put the chosen seeds in the seeds file of each crawler with the actual pc in the pc file
//            for(int i=0 ; i <numOfCrawlers ; i++){
//
//            }

        }
        catch(Exception e){
            System.out.println("Invalid Input , the number will be the default");
        }
        //--Creating Crawlers Threads equal to the specified number
//        for(int i=0 ; i< numOfCrawlers ; i++){
//            (new Thread(new Crawler(seedsFilePath,pcPath))).start();
//        }

    }
}
