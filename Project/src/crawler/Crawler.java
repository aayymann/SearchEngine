package crawler;

import java.util.ArrayList;
import java.util.HashMap;
import java.net.MalformedURLException;
import java.net.URL;


public class Crawler {

    public static void main(String[] args) {
        HashMap <String, ArrayList<String>> webRobots = new HashMap<String, ArrayList<String>>();
        ArrayList<String> webSiteURls = new ArrayList<>();
        //--Read URL seeds from InputFile
        ManipulateFile.ReadUrlSeeds(webSiteURls);
        //--Visit HomePage to generate the robots.txt map
        for (int k = 0; k < webSiteURls.size(); k++) {
            boolean isVisitable = true;
            URL URLHandle;
            try {
                URLHandle = new URL(webSiteURls.get(k));
                String robotsPage= URLHandle.getProtocol()+"://"+URLHandle.getHost() +"/robots.txt";
                //--If the robots.txt of the homepage website already exists
                if (webRobots.containsKey(URLHandle.getHost())) {
                    //--Check before visiting if it is an allowable page
                    System.out.println("KEY EXISTS");
                    ArrayList<String> disallowedPaths = webRobots.get(URLHandle.getHost());
                    for (int i = 0 ; i< disallowedPaths.size() ; i++){
                        if (URLHandle.getFile().matches(disallowedPaths.get(i).replace("*",".*"))){
                            isVisitable = false;
                            break;
                        }
                    }
                } else {
                    //
                    System.out.println("KEY DOESN'T EXIST");
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
                    System.out.println(webRobots);
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
    }
}
