
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class WebScraperMarx {

    private static final String startURL = "https://se.indeed.com/";      //Startpage
    private static String job = "programmering";                          //Searchword for job
    private static String place = "uppsala";                              //Searchword for place

    public WebScraperMarx() {
    }

    /**
     * A recursive function that will scan and find all the links, of job ads, on the search results page. The function will call
     * itself until it scans all the result pages.
     * The function will skip the sponsored ads, because they get repeated.
     *
     * @param document the search result page that needs to be scanned.
     * @return and ArrayList of strings that contains all the found links.
     * @throws IOException
     */
    public static ArrayList<String> getPageLinks(Document document) throws IOException {
        ArrayList<String> result = new ArrayList<String>(16); //16 seems to bee maximum size of search results per site
        //Extract the HTML-elements within the ID "resultsCol" and with classname "title"
        Elements titleLink = document.getElementById("resultsCol").getElementsByClass("title");
        for (Element link : titleLink) {
            Elements siblings = link.siblingElements();
            for (Element sibling : siblings) {
                //For each links siblings, extract the sibling with classname ""jobsearch-SerpJobCard-footer", and see if it contains the class " sponsoredGray ".
                if (sibling.hasClass("jobsearch-SerpJobCard-footer")) {
                    if (sibling.getElementsByClass(" sponsoredGray ").size() == 0) {
                        //If it's not a sponsored link, add it's URL to the ArrayList
                        String adURL = link.getElementsByClass("jobtitle turnstileLink ").attr("abs:href");
                        result.add(adURL);
                        //RawAdData ad = getRawAdData(adURL, link.text());
                        //if (ad != null) result.add(ad);
                        //System.out.println(link.text()); //Prints the ad-title
                    }
                }
            }
        }
        //getNext returns a HTML-document with the next page if there is one, otherwise null.
        Document next = getNext(document);
        if (next == null) {
            return result;
        }
        //If there is a next page, call getPageLinks recursively with the next page.
        result.addAll(getPageLinks(next));
        return result;
    }


    /**
     * Open the given ad and store it's title and description in a RawAdData object.
     *
     * @param links
     * @return
     * @throws IOException
     */
    private static ArrayList<RawAdData> getRawAdData(ArrayList<String> links) throws IOException {
        ArrayList<RawAdData> result = new ArrayList<RawAdData>();
        for (String link : links) {
            RawAdData temp = new RawAdData();
            Document adHTML = Jsoup.connect(link).get();

            temp.setTitle(adHTML.getElementsByClass("icl-u-xs-mb--xs icl-u-xs-mt--none jobsearch-JobInfoHeader-title").get(0).text());


            //The description is stored under several p-tags.
            //One link (or at least a very small amount of links) seems to give a nullPointerException. Solved with this try-block
            Elements linkDescription = adHTML.getElementById("jobDescriptionText").getElementsByTag("p");
            if (linkDescription != null) {
                StringBuilder sb = new StringBuilder();
                for (Element text : linkDescription) {
                    sb.append(text.text());
                }
                temp.setDiscription(sb.toString());
                result.add(temp);
            }
        }
        return result;
    }


    /**
     * The function will find the next page, if it exists.
     *
     * @param document the page in which the function will search for a next link
     * @return the next page if found, otherwise null.
     * @throws IOException
     */
    private static Document getNext(Document document) throws IOException {
        //Extract the elements from ID "resultCol" with class "pagination".
        Elements nextLinks = document.getElementById("resultsCol").getElementsByClass("pagination");
        //If there isn't any, return null.
        if (nextLinks.size() == 0) return null;
        else {
            //Otherwise, take the URL from the last link ("Nästa »"), and return the HTML document for that page
            Elements nextLinks2 = nextLinks.get(0).getElementsByTag("a");
            Element nextLink = nextLinks2.get(nextLinks2.size() - 1);
            if (nextLink.text().equals("Nästa »")) {
                Document newDoc = Jsoup.connect(nextLink.attr("abs:href")).get();
                return newDoc;
            }
        }
        return null;
    }


    public static void main(String[] args) throws IOException {
        //Extract the HTML-document from the first search-page
        Document document = Jsoup.connect(startURL + job + "-jobb-i-" + place).get();
        ArrayList<String> links = getPageLinks(document);
        ArrayList<RawAdData> rawData = getRawAdData(links);
        for (int i = 0; i < rawData.size(); i++) {
            System.out.println(rawData.get(i).getTitle());
        }
        System.out.println("size på resultLinks: " + rawData.size());
    }
}