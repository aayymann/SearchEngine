package crawler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;


public class URLHandling {
    static String OpenURLFn(URL url) {
        String gennHTMLString = "";
        try {
            URLConnection conn = url.openConnection();
            // open the stream and put it into BufferedReader
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;
            while ((inputLine = br.readLine()) != null) {
                gennHTMLString += inputLine;
                gennHTMLString += "\n";
            }
            br.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
//            e.printStackTrace();
//            System.out.println("Page not found.");
        }
        return gennHTMLString;
    }

    static void RobotsChecker(String robotsHTML, HashMap<String, ArrayList<String>> map, String key) {
        robotsHTML = robotsHTML.toLowerCase();
        map.put(key, new ArrayList<String>());
        int begin = robotsHTML.indexOf("user-agent: *");
        if (begin != -1) {
            begin += 14;
            //
            String myUserAgent = robotsHTML.substring(begin).split("user-agent:")[0];  //always @ userAgents[0]
            String[] agentSetup = myUserAgent.split("\\r?\\n");
            for (int i = 0; i < agentSetup.length; i++) {
                String[] option = agentSetup[i].split("\\s");
                if (option[0].contains("disallow:") && option.length >= 2) {
                    map.get(key).add(option[1]);
                }
            }
        }

    }
}
