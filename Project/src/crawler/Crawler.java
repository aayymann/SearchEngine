package crawler;

import java.util.ArrayList;
import java.util.HashMap;
import java.net.MalformedURLException;
import java.net.URL;


public class Crawler {

    public static void main(String[] args) {
        HashMap <String, ArrayList<String>> webRobots = new HashMap<String, ArrayList<String>>();
        //--File Paths
        String seedsFilePath="./urlSeeds.txt";
        String pcPath="./pc.txt";

        //--Read URL seeds from InputFile
        int compareWith=0;
        compareWith = ManipulateFile.ReadFromPCFile(pcPath); // old value of pc counter
        ManipulateFile.WriteInPCFile(pcPath,compareWith);
        System.out.println(compareWith);
        while(ManipulateFile.ReadFile(seedsFilePath,compareWith)[0]!=""){
            String webSiteURl =ManipulateFile.ReadFile(seedsFilePath,compareWith)[0];
            compareWith=ManipulateFile.ReadFromPCFile(pcPath); //read from the pc file
            compareWith= Integer.parseInt(ManipulateFile.ReadFile(seedsFilePath,compareWith)[1]);  // increment it
            ManipulateFile.WriteInPCFile(pcPath,compareWith); // save it in the pc file
            System.out.println(webSiteURl);
            //--Visit HomePage to generate the robots.txt map
            boolean isVisitable = true;
            URL URLHandle;
            try {
                URLHandle = new URL(webSiteURl);
                String robotsPage= URLHandle.getProtocol()+"://"+URLHandle.getHost() +"/robots.txt";
                //--If the robots.txt of the homepage website already exists
                if (webRobots.containsKey(URLHandle.getHost())) {
                    //--Check before visiting if it is an allowable page
                    System.out.println("THE ROBOTS EXISTS");
                    ArrayList<String> disallowedPaths = webRobots.get(URLHandle.getHost());
                    for (int i = 0 ; i< disallowedPaths.size() ; i++){
                        if (URLHandle.getFile().matches(disallowedPaths.get(i).replace("*",".*"))){
                            isVisitable = false;
                            break;
                        }
                    }
                } else {
                    //
                    System.out.println("THE ROBOTS DOESN'T EXIST");
                    URL robotsURL = new URL(robotsPage);
                    String robotsHTML=URLHandling.OpenURLFn(robotsURL);
                    String mapKey = URLHandle.getHost();
                    URLHandling.RobotsChecker(robotsHTML,webRobots,mapKey);
                    //--Check before visiting if it is an allowable page
                    ArrayList<String> disallowedPaths = webRobots.get(mapKey);
                    for (int i = 0 ; i< disallowedPaths.size() ; i++){
                        if (URLHandle.getFile().matches(disallowedPaths.get(i).replace("*",".*"))){
                            isVisitable = false;
                            break;
                        }
                    }
                }
                if(isVisitable){
                    //--TODO::Check if this website is not in the visitable file
                    String gennHTMLString=URLHandling.OpenURLFn(URLHandle);
                    System.out.println("This host is not on the robots page so we will visit");
                    System.out.println(webRobots);
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
    }
}
