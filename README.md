# SearchEngine
Java Crawler Search Engine

Steps to Run:
1-Open your IDE (prefered IntelliJ) from Server Folder it will ask you to install Gradel packages give it some time to install to be able to run API.
2-you should have MongoDB installed on your PC for the database.
3-Run Crawler for some time to fetch data. (Details below)
4-Run Indexer to process data extracted from crawler. (Details below)
5-Run Api from Folder api
6-Run web Interface
  run command "npm install" in Frontend/google-it folder
  then run command "npm start" in Frontend/google-it folder
  will run on localhost:3000

Crawler:
*The crawler package has Main fn to run it. It asks for the number of threads to generate from the crawler.
*The crawler outputs the fetched data in the out folder.
	Seeds folder -> contains a the file which has all the URL seeds for the crawler
	hyperlinks folder -> visited websites hyperlink
	websites folder-> fetched HTML from this website
	pcFolder -> stoppping counter for each crawler thread
	urlSeeds folder -> file for each crawler thread to take a link from and visit
*If the crawler is stopped and re-runed again with the same number of threads in both runs, it works correctly
 If the crawler is stopped and re-runed again with the different number of threads in both runs, the generated folders from the first run with remain the same and the new threads will continue on them
	some folders may be added or some may be as is from the first run -> according to the larger number of the threads in both runs
*To start a new Crawl from the ground, all files in ./out/->
	pcFolder,hyperlinks,urlSeedsFolder and websites has to be deleted EXCEPT FOR "zTemp.txt".
	The folders themselves are not deleted -> leave folders
-------------------------------------------------------------------------------------------------------------------------------------------------------
Indexer:
*The indexer package has a Main fn to run it.
*The indexer will not run 2 consecutive times if the crawler did not run between them -> Data will not change so no need to read again
 Indexer was implemented as there would be re-crawling and there was no time to change it -> Minimizing unnecessary processing was the best we could do
	Crawler writes a number when it runs, Indexer checks that the number in this file is from the crawler, if not -> run will not happen
-------------------------------------------------------------------------------------------------------------------------------------------------------
