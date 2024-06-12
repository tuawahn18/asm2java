import java.time.LocalDate;
import java.util.Scanner;

public class ArticleController {
    MySqlArticleRepository mySqlArticleRepository = new MySqlArticleRepository();

    public void creatAndSave() {
        Scanner scanner = new Scanner(System.in);
        Article newArticle = new Article();
        System.out.print("Enter Base Url: ");
        newArticle.setBaseUrl(scanner.nextLine());

        System.out.print("Enter Title: ");
        newArticle.setTitle(scanner.nextLine());

        System.out.print("Enter Description: ");
        newArticle.setDescription(scanner.nextLine());

        System.out.print("Enter content: ");
        newArticle.setContent(scanner.nextLine());

        System.out.print("Enter thumbnail: ");
        newArticle.setThumbnail(scanner.nextLine());

        System.out.print("Enter create date (yyyy-MM-DD): ");
        newArticle.setCreatedAt(LocalDate.parse(scanner.nextLine()));

        System.out.print("Enter update date (yyyy-MM-DD): ");
        newArticle.setUpdatedAt(LocalDate.parse(scanner.nextLine()));

        System.out.print("Enter delete date (yyyy-MM-DD): ");
        newArticle.setDeletedAt(LocalDate.parse(scanner.nextLine()));

        System.out.println("1. Active");
        System.out.println("0. Pending");
        System.out.println("-1. Deleted");
        System.out.print("Enter status: ");
        newArticle.setStatus(scanner.nextInt());

        scanner.nextLine();
        System.out.print("Order Information: ");
        System.out.println(newArticle.toString());
        System.out.print("Do you want to save the order? (Y/N): ");
        String choice = scanner.nextLine();
        if (choice.equalsIgnoreCase("y")) {
            mySqlArticleRepository.save(newArticle);
        }

    }
}
