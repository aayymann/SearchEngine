package indexer;


public class Stemmer {
    //Call this function Anywhere pass a String with the Content and it will return the same string with all the words Stemmed
    /*
    Needed files:
    Among
    SnowballProgram
    SnowballStemmer
    ext Folder
     */
    public static String Stemming(String[] Splitted) throws Throwable {
        Class stemClass = Class.forName("indexer.ext.englishStemmer");
        SnowballStemmer stemmer = (SnowballStemmer) stemClass.newInstance();
        StringBuilder result = new StringBuilder();
        for (String s : Splitted) {
            stemmer.setCurrent(s);
            stemmer.stem();
            result.append(stemmer.getCurrent()).append(" ");
        }
        result = new StringBuilder(result.substring(0, result.length() - 1));
        return result.toString();
    }
}
