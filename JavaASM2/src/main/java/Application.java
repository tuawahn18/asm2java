import java.util.ArrayList;
import java.util.Scanner;

public class Application {
    private static final VnexpressArticleService vnexpressService = new VnexpressArticleService();
    private static final MyArticleService myArticleService = new MyArticleService();
    private static final MySqlArticleRepository repository = new MySqlArticleRepository();

    public static void main(String[] args) {
        generateMenu();
    }

    private static void generateMenu() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("1. Crawl thông tin từ Vnexpress");
            System.out.println("2. Crawl thông tin từ nguồn của tôi");
            System.out.println("3. Hiển thị danh sách tin hiện có");
            System.out.println("4. Thoát chương trình");
            System.out.print("Vui lòng nhập lựa chọn từ 1-4: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    crawlVnexpress();
                    break;
                case 2:
                    crawlMyService();
                    break;
                case 3:
                    displayArticles();
                    break;
                case 4:
                    System.out.println("Thoát chương trình.");
                    return;
                default:
                    System.out.println("Lựa chọn không hợp lệ. Vui lòng chọn lại.");
            }
        }
    }

    private static void crawlVnexpress() {
        String url = "https://vnexpress.net/";
        ArrayList<String> links = vnexpressService.getLinks(url);
        for (String link : links) {
            Article article = vnexpressService.getArticle(link);
            if (article != null) {
                repository.save(article);
            }
        }
        System.out.println("Đã lấy thông tin từ Vnexpress.");
    }

    private static void crawlMyService() {
        String url = "https://thethao247.vn/";
        ArrayList<String> links = myArticleService.getLinks(url);
        for (String link : links) {
            Article article = myArticleService.getArticle(link);
            if (article != null) {
                repository.save(article);
            }
        }
        System.out.println("Đã lấy thông tin từ nguồn của bạn.");
    }

    private static void displayArticles() {
        ArrayList<Article> articles = repository.findAll();
        if (articles.isEmpty()) {
            System.out.println("Không có tin tức nào.");
            return;
        }

        for (Article article : articles) {
            System.out.println("ID: " + article.getId() + ", baseUrl: " + article.getBaseUrl() + ", Title: " + article.getTitle());
        }

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("Nhập mã tin cần xem chi tiết hoặc 'exit' để thoát: ");
            String input = scanner.nextLine();
            if (input.equalsIgnoreCase("exit")) {
                return;
            }

            try {
                Long id = Long.parseLong(input);
                Article article = articles.stream()
//                        .filter(a -> a.getId().equals(id))
                        .findFirst()
                        .orElse(null);
                if (article != null) {
                    displayArticleDetails(article);
                } else {
                    System.out.println("Mã tin không hợp lệ.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Mã tin không hợp lệ.");
            }
        }
    }

    private static void displayArticleDetails(Article article) {
        System.out.println("Title: " + article.getTitle());
        System.out.println("Description: " + article.getDescription());
        System.out.println("Content: " + article.getContent());
        System.out.println("Thumbnail: " + article.getThumbnail());
        System.out.println("Created At: " + article.getCreatedAt());
        System.out.println("Updated At: " + article.getUpdatedAt());
        System.out.println("Status: " + article.getStatus());
        System.out.println("-------------");
    }
    }