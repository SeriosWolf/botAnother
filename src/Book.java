import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class Book {
    private Document document;

    public Book(){
        connect();
    }

    private void connect(){
        try{
            document = Jsoup.connect("http://film-like.com/movie/6697-kozerog-1").get();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getTitle(){
        return document.title();
    }

    public String getIMDB(){
        Elements elements = document.getElementsByClass("value");
        return elements.text();
    }

    public String getDescription(){
        Elements elements = document.getElementsByClass("description wborder");
        return elements.text();
    }



    public String getFilmsLike() {
        Elements elements = document.getElementsByClass("h-content");
        return elements.text();
    }


}
