import java.util.ArrayList;

public interface ArticleService {
//    Cho phép lấy danh sách link từ một link nguồn
    ArrayList<String> getLinks(String url);
//    Cho phép lấy thông tin chi tiết từ một link bài viết chi tiết
    Article getArticle(String url);
}
