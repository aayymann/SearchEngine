package crawler;

import java.util.ArrayList;
import java.util.HashMap;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Crawler implements Runnable{
    // --File Paths
    private String seedsFilePath;
    private String pcPath;
    Crawler(String seedsFile , String programCounter){
        this.seedsFilePath=seedsFile;
        this.pcPath=programCounter;
    }
    public void run() {
        //
        HashMap<String, ArrayList<String>> webRobots = new HashMap<String, ArrayList<String>>();
        // --Read URL seeds from InputFile
        int compareWith = 0;
        int pc=0;
        compareWith = ManipulateFile.ReadNumberFromFile(pcPath); // old value of pc counter
        ManipulateFile.WriteNumberInFile(pcPath, compareWith);
        // --If the end of file is reached or the number of crawls per crawl= 5000
        while (ManipulateFile.ReadFile(seedsFilePath, compareWith)[0] != ""
                && ManipulateFile.ReadNumberFromFile(pcPath) < 5000) {
            String webSiteURl = ManipulateFile.ReadFile(seedsFilePath, compareWith)[0];
            compareWith = ManipulateFile.ReadNumberFromFile(pcPath); // read from the pc file
            int websiteIndex = compareWith;
            compareWith = Integer.parseInt(ManipulateFile.ReadFile(seedsFilePath, compareWith)[1]); // increment it
            ManipulateFile.WriteNumberInFile(pcPath, compareWith); // save it in the pc file
            //System.out.println(webSiteURl);
            // --Visit HomePage to generate the robots.txt map
            boolean isVisitable = true;
            URL URLHandle;

            try {
                // --CHECK ROBOTS.TXT
                URLHandle = new URL(webSiteURl);
                String robotsPage;
                int length = URLHandle.getHost().split("\\.").length;
                String mainDomainName = URLHandle.getHost().split("\\.")[length - 2];
                String TLD = URLHandle.getHost().split("\\.")[length - 1];
                robotsPage = URLHandle.getProtocol() + "://www." + mainDomainName + "." + TLD + "/robots.txt";
                //System.out.println("FOR THE ROBOTS " + robotsPage);
                // --If the robots.txt of the homepage website already exists
                //System.out.println("domain name is "+mainDomainName);
                if (webRobots.containsKey(mainDomainName)) {
                    // --Check before visiting if it is an allowable page
                    ArrayList<String> disallowedPaths = webRobots.get(mainDomainName);
                    for (int i = 0; i < disallowedPaths.size(); i++) {
                        if (URLHandle.getFile().contains(disallowedPaths.get(i))) {
                            isVisitable = false;
                            //System.out.println(disallowedPaths.get(i));
                            break;
                        }
                    }
                } else {
                    //
                    int deleted=websiteIndex+1;
                    //System.out.println("THE ROBOTS DOESN'T EXIST " + deleted);
                    URL robotsURL = new URL(robotsPage);
                    String robotsHTML = URLHandling.OpenURLFn(robotsURL);
                    URLHandling.RobotsChecker(robotsHTML, webRobots, mainDomainName);
                    // --Check before visiting if it is an allowable page
                    ArrayList<String> disallowedPaths = webRobots.get(mainDomainName);
                    for (int i = 0; i < disallowedPaths.size(); i++) {
                        if (URLHandle.getFile().contains(disallowedPaths.get(i))) {
                            isVisitable = false;
                            //System.out.println("heree again "+ URLHandle.getFile());
                            //System.out.println("HEREEE22222 "+disallowedPaths.get(i));
                            break;
                        }
                    }
                }
                // --Check that the page is not in the Robots.txt
                if (isVisitable) {
                    String gennHTMLString = URLHandling.OpenURLFn(URLHandle);
                    // --Get the hyperlinks
                    Pattern p = Pattern.compile("<a[^>]+href=\"(.*?)\"", Pattern.DOTALL);
                    Matcher m = p.matcher(gennHTMLString);

                    while (m.find()) {
                        String tempURL = m.group(1);
                        tempURL = tempURL.trim();

                        // --If the retrieved link does not contain the main sub domain (Concatenate it)
                        //System.out.println("The m group is  " + m.group(1));
                        //if ((!m.group(1).contains("http") || !m.group(1).startsWith("//") )||(!m.group(1).contains("https") || !m.group(1).startsWith("//")))
                        if (!m.group(1).contains("http") ||!m.group(1).contains("https")) {
                            tempURL = URLHandle.getProtocol() + "://" + URLHandle.getHost() + m.group(1).trim();
                        }
                        tempURL = tempURL.trim();

                        // --If the extracted hyperlink is not present in the url seeds
                        if (!ManipulateFile.IsStringPresent(seedsFilePath, tempURL)) {
                            ManipulateFile.AppendOnFile(seedsFilePath, tempURL);
                        }
                    }
                    //--Add the HTML Content of the visited page in a text file
                    ManipulateFile.CreateWebsiteFile(websiteIndex, String.valueOf(URLHandle));
                }
                else{
                    System.out.println("The pc  of the blocked url is: "+ websiteIndex);
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
    }
}
