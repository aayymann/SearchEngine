package crawler;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        int numOfCrawlers = 1;
        Scanner scanner = new Scanner(System.in);
        System.out.println("How many Crawler threads do you want to generate? (Default is 1)");
        try {
            numOfCrawlers = scanner.nextInt();
            if (numOfCrawlers == 0)
                numOfCrawlers = 1;
        } catch (Exception e) {
            System.out.println("Invalid Input , the number will be the default");
        }
        //--INDICATOR FOR THE SYNC BETWEEN THE INDEXER AND THE CRAWLER AS THE CRAWLER WAS THE LAST TO WORK
        ManipulateFile.WriteNumberInFile("./out/indicator.txt",234);
        //
        ManipulateFile.WriteNumberInFile("./src/crawler/numOfCrawlers.txt", numOfCrawlers);
        // --Num of crawlers has to be at most equal to the number of url links in the
        // url seeds file
        int numOfOriginalSeeds = ManipulateFile.GetNumberOfUrlSeeds("./seeds/urlSeeds.txt");
        while (numOfCrawlers > numOfOriginalSeeds)
            numOfCrawlers--;
        // --Create numOfCrawlers seeds files and numOfCrawlers pc files for each
        // crawler thread to read from
        String seedsFilePath = "./out/urlSeedsFolder/urlSeeds";
        String pcPath = "./out/pcFolder/pc";
        String[] pcPathArr = new String[numOfCrawlers];
        String[] seedsFilePathArr = new String[numOfCrawlers];
        int incrementer = numOfOriginalSeeds / numOfCrawlers;
        int[][] arrOfPC = new int[numOfCrawlers][2];
        int begin = 0;
        for (int i = 0; i < numOfCrawlers; i++) {
            seedsFilePathArr[i] =  seedsFilePath + String.valueOf(i) + ".txt";
            pcPathArr[i] = pcPath + String.valueOf(i) + ".txt";
            if (i == numOfCrawlers - 1) {
                arrOfPC[i][0] = begin;
                arrOfPC[i][1] = numOfOriginalSeeds;
            } else {
                arrOfPC[i][0] = begin;
                arrOfPC[i][1] = begin + incrementer;
                begin = begin + incrementer;
            }
        }
        // --Populate the seeds file and the pc files
        for (int i = 0; i < numOfCrawlers; i++) {
            String[] recStringFromFileRead;
            int temp = ManipulateFile.ReadNumberFromFile(pcPathArr[i]);
            if (temp == -1)
                temp = 0;
            // System.out.println("The temp is " + temp);
            // --Populate the seeds files in case this is the first crawl and the crawler is
            // not interupted in a previous run
            if (temp != 0) {
                break;
            }
            ManipulateFile.WriteNumberInFile(pcPathArr[i], temp);
            recStringFromFileRead = ManipulateFile.ReadFile("./seeds/urlSeeds.txt", arrOfPC[i][0]);
            ManipulateFile.WriteInFile(seedsFilePathArr[i], recStringFromFileRead[0]);
            int newIndex = arrOfPC[i][0];
            if (incrementer > 1) {
                int stop;
                if (i == numOfCrawlers - 1)
                    stop = numOfOriginalSeeds;
                else
                    stop = incrementer;

                for (int j = 1; j < stop; j++) {
                    newIndex++;
                    recStringFromFileRead = ManipulateFile.ReadFile("./seeds/urlSeeds.txt", newIndex);
                    ManipulateFile.AppendOnFile(seedsFilePathArr[i], recStringFromFileRead[0]);
                }
            }
        }
       // --Creating Crawlers Threads equal to the specified number
        for (int i = 0; i < numOfCrawlers; i++) {
            (new Thread(new Crawler(seedsFilePathArr[i], pcPathArr[i], i))).start();
        }
    }
}
