package crawler;

import java.util.ArrayList;
import java.util.HashMap;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Crawler {

    public static void main(String[] args) {
        //
        HashMap<String, ArrayList<String>> webRobots = new HashMap<String, ArrayList<String>>();
        // --File Paths
        String seedsFilePath = "./urlSeeds.txt";
        String pcPath = "./pc.txt";
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
                //System.out.println(mainDomainName);
                robotsPage = URLHandle.getProtocol() + "://www." + mainDomainName + "." + TLD + "/robots.txt";
                //System.out.println("FOR THE ROBOTS " + robotsPage);
                // --If the robots.txt of the homepage website already exists
                //System.out.println("domain name is "+mainDomainName);
                if (webRobots.containsKey(mainDomainName)) {
                    // System.out.println("THE ROBOTS EXISTS");
                    // --Check before visiting if it is an allowable page
                    ArrayList<String> disallowedPaths = webRobots.get(mainDomainName);
                    for (int i = 0; i < disallowedPaths.size(); i++) {
                        if (URLHandle.getFile().contains(disallowedPaths.get(i))) {
                            isVisitable = false;
                            System.out.println(disallowedPaths.get(i));
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
                    // System.out.println(gennHTMLString);
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
                            //System.out.println("Beforeee  " + tempURL);
                            tempURL = URLHandle.getProtocol() + "://" + URLHandle.getHost() + m.group(1).trim();
                        }

                        tempURL = tempURL.trim();
                        //System.out.println("THE ADDED WEBSITE IS AFter    "+tempURL);

                        // --If the extracted hyperlink is not present in the url seeds
                        if (!ManipulateFile.IsStringPresent(seedsFilePath, tempURL)) {
                            ManipulateFile.AppendOnFile(seedsFilePath, tempURL);
                            System.out.println(websiteIndex);
                            if(websiteIndex==13)
                                System.out.println("WESELNAAAAAAAAAA" + String.valueOf(URLHandle));
                            ManipulateFile.CreateWebsiteFile(websiteIndex, String.valueOf(URLHandle));
                        }
                    }
                    //System.out.println("--------------------------------------------------------");
                    //System.out.println("THE PC IS       " + websiteIndex);
                }
                else{
                    System.out.println("The pc : " + websiteIndex+" pf the robots file is");
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
    }
}
