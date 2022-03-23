# Java SearchEngine - *Google it*
## **Crawler Module** to Crawl the pages by
    1- Visiting url seeds
    2- Extracting hyperlinks and adding them
    3- Checking robots file to esnure this action is allowed
    4- Saving HTML Pages
## **Indexer Module** to generate database Words for search
    1- Parse HTML to String
    2- Remove stopping words , hyperlinks and stem words
    3- Save unqiue words in MongoDB database
    4- Store Normailzed Term Frequency and IDF for each word
## **API Module** 
    1- Query processor searchs in the data base for the searched for words.
    2- Returns JSON to the front-end service
## **Interface Module**
    1- HTML and CSS for display of the returned pages from the query processor

