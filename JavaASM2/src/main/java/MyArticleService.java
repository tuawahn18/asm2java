import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;

public class MyArticleService implements ArticleService {

    @Override
    public ArrayList<String> getLinks(String url) {
        HashSet<String> links = new HashSet<>();
        try {
            Document document = Jsoup.connect(url).get();
            Elements elements = document.getElementsByTag("a");
            for (Element element : elements) {
                String href = element.attr("href");
                if (href.contains("https://thethao247.vn") && href.contains(".html")) {
                    links.add(href);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return new ArrayList<>(links);
    }

    @Override
    public Article getArticle(String url) {
        try {
            Document doc = Jsoup.connect(url).get();
            String title = doc.select("h1.title-detail").text();
            String description = doc.select("p.description").text();
            String content = doc.select("article.fck_detail").text();
            String thumbnail = doc.select("picture img[itemprop=contentUrl]").attr("data-src");

            // Assuming createdAt, updatedAt, and status are set to current date and active status for simplicity
            LocalDate currentDate = LocalDate.now();

            Article article = new Article();
            article.setBaseUrl(url);
            article.setTitle(title);
            article.setDescription(description);
            article.setContent(content);
            article.setThumbnail(thumbnail);
            article.setCreatedAt(currentDate);
            article.setUpdatedAt(currentDate);
            article.setStatus(1); // Active status

            return article;
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}