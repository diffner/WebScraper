import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.print.StreamPrintService;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class WebScraperMarx {

    private ArrayList<String> titles;
    private static String startURL = "https://se.indeed.com/";
    private static String job = "programmering";
    private static String place = "eskilstuna";

    public WebScraperMarx() {
        titles = new ArrayList<>();
    }

    //Extract titles from the search
    public void getAdTitle(String URL) {
        try {
            Document HTML = Jsoup.connect(URL).get();
            Elements titles = HTML.select("a[href^=\"https://www.mkyong.com/page/\"]");
            //System.out.println(otherLinks);

//            for (Element page : otherLinks) {
//                if (links.add(URL)) {
//                    //Remove the comment from the line below if you want to see it running on your editor
//                    System.out.println(URL);
//                }
//                getPageLinks(page.attr("abs:href"));
//            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }


    //Find all URLs that start with "http://www.mkyong.com/page/" and add them to the HashSet
    public ArrayList<String> getPageLinks(Document document) throws IOException {
        //System.out.println("recursion");
        ArrayList<String> result = new ArrayList<String>(16);
        Elements links = document.getElementById("resultsCol").getElementsByClass("title");
        for (Element link : links) {
            if (!link.siblingElements().hasClass(" sponsoredGray ")) {
                result.add(link.getElementsByClass("jobtitle turnstileLink ").get(0).attr("abs:href"));
                System.out.println(link.text());
            }
        }
        Document next = getNext(document);
        if (next == null) {
            return result;
        }
        result.addAll(getPageLinks(next));
        return result;

//getElementsByClass("jobsearch-SerpJobCard unifiedRow row result clickcard");


//        Elements links = document.getElementById("resultsCol").getElementsByClass("jobtitle turnstileLink ");
//        for (Element link : links) {
//            result.add(link.attr("abs:href"));
//            System.out.println(link.text());
//        }
//        Document next = getNext(document);
//        if (next == null) {
//            return result;
//        }
//        result.addAll(getPageLinks(next));
//        return result;
    }

    private Document getNext(Document document) throws IOException {
        Elements links = document.getElementById("resultsCol").getElementsByClass("pagination");
        if (links.size() == 0) return null;
        else {
            Elements newLinks = links.get(0).getElementsByTag("a");
            Element nextLink = newLinks.get(newLinks.size() - 1);
            if (nextLink.text().equals("Nästa »")) {
                Document newDoc = Jsoup.connect(nextLink.attr("abs:href")).get();
                return newDoc;
            }
        }
        return null;

//        for (Element link : links) {
//            //System.out.println("1");
//            System.out.println(link.childNodeSize());
//            Elements childLinks = link.children();
//            for (Element finLink : childLinks) {
//                System.out.println(finLink);
//                if (finLink.text().equals("Nästa ")) {
//                    Document newDoc = Jsoup.connect(finLink.attr("abs:href")).get();
//                    System.out.println(finLink.attr("abs:href"));
//                    return newDoc;
//                }
//
//            }
//        }
//        System.out.println("getnext null");
    }

    public static void main(String[] args) throws IOException {
        WebScraperMarx bwc = new WebScraperMarx();
        Document document = Jsoup.connect(startURL + job + "-jobb-i-" + place).get();
        ArrayList<String> resultLinks = bwc.getPageLinks(document);
        for (int i = 0; i < resultLinks.size(); i++) {
            System.out.println(resultLinks.get(i));
        }
        System.out.println("size på resultLinks: " +resultLinks.size());
    }
}
