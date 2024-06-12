import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class MySqlArticleRepository implements ArticleRepository {

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private final String MYSQL_CONNECTION_STRING = "jdbc:mysql://localhost:3306/vnexpress";
    private final String MYSQL_USERNAME = "root";
    private final String MYSQL_PASSWORD = "";

    @Override
    public ArrayList<Article> findAll() {
        ArrayList<Article> articles = new ArrayList<>();
        try {
            Connection connection = DriverManager.getConnection(MYSQL_CONNECTION_STRING, MYSQL_USERNAME, MYSQL_PASSWORD);
            String prepareSql = "SELECT * FROM article";
            PreparedStatement preparedStatement = connection.prepareStatement(prepareSql);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                String baseUrl = resultSet.getString("baseUrl");
                String title = resultSet.getString("title");
                String description = resultSet.getString("description");
                String content = resultSet.getString("content");
                String thumbnail = resultSet.getString("thumbnail");
                LocalDate createdAt = LocalDate.parse(resultSet.getString("createdAt"), formatter);
                LocalDate updatedAt = LocalDate.parse(resultSet.getString("updatedAt"), formatter);
                LocalDate deletedAt = resultSet.getString("deletedAt") != null ? LocalDate.parse(resultSet.getString("deletedAt"), formatter) : null;
                int status = resultSet.getInt("status");

                Article article = new Article(id, baseUrl, title, description, content, thumbnail, createdAt, updatedAt, deletedAt, status);
                articles.add(article);
            }

            connection.close();
        } catch (SQLException e) {
            System.out.println("Có lỗi xảy ra, vui lòng thử lại sau.");
            e.printStackTrace();
        }
        return articles;
    }

    @Override
    public Article findByUrl(String url) {
        try {
            Connection connection = DriverManager.getConnection(MYSQL_CONNECTION_STRING, MYSQL_USERNAME, MYSQL_PASSWORD);
            String prepareSql = "SELECT * FROM article WHERE baseUrl = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(prepareSql);
            preparedStatement.setString(1, url);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                Long id = resultSet.getLong("id");
                String baseUrl = resultSet.getString("baseUrl");
                String title = resultSet.getString("title");
                String description = resultSet.getString("description");
                String content = resultSet.getString("content");
                String thumbnail = resultSet.getString("thumbnail");
                LocalDate createdAt = LocalDate.parse(resultSet.getString("createdAt"), formatter);
                LocalDate updatedAt = LocalDate.parse(resultSet.getString("updatedAt"), formatter);
                LocalDate deletedAt = resultSet.getString("deletedAt") != null ? LocalDate.parse(resultSet.getString("deletedAt"), formatter) : null;
                int status = resultSet.getInt("status");

                Article article = new Article(id, baseUrl, title, description, content, thumbnail, createdAt, updatedAt, deletedAt, status);
                connection.close();
                return article;
            }

            connection.close();
        } catch (SQLException e) {
            System.out.println("Có lỗi xảy ra, vui lòng thử lại sau.");
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Article save(Article article) {
        try {
            Connection connection = DriverManager.getConnection(MYSQL_CONNECTION_STRING, MYSQL_USERNAME, MYSQL_PASSWORD);
            String prepareSql = "INSERT INTO article (baseUrl, title, description, content, thumbnail, createdAt, updatedAt, deletedAt, status) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(prepareSql);

            preparedStatement.setString(1, article.getBaseUrl());
            preparedStatement.setString(2, article.getTitle());
            preparedStatement.setString(3, article.getDescription());
            preparedStatement.setString(4, article.getContent());
            preparedStatement.setString(5, article.getThumbnail());
            preparedStatement.setString(6, article.getCreatedAt().toString());
            preparedStatement.setString(7, article.getUpdatedAt().toString());
            preparedStatement.setString(8, article.getDeletedAt() != null ? article.getDeletedAt().toString() : null);
            preparedStatement.setInt(9, article.getStatus());

            preparedStatement.execute();
            connection.close();
        } catch (SQLException e) {
            System.out.println("Có lỗi xảy ra, vui lòng thử lại sau.");
            e.printStackTrace();
        }
        return article;
    }

    @Override
    public Article update(Article article) {
        try {
            Connection connection = DriverManager.getConnection(MYSQL_CONNECTION_STRING, MYSQL_USERNAME, MYSQL_PASSWORD);
            String prepareSql = "UPDATE article SET baseUrl = ?, title = ?, description = ?, content = ?, thumbnail = ?, createdAt = ?, updatedAt = ?, deletedAt = ?, deleted_at = ? WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(prepareSql);

            preparedStatement.setString(1, article.getBaseUrl());
            preparedStatement.setString(2, article.getTitle());
            preparedStatement.setString(3, article.getDescription());
            preparedStatement.setString(4, article.getContent());
            preparedStatement.setString(5, article.getThumbnail());
            preparedStatement.setString(6, article.getCreatedAt().toString());
            preparedStatement.setString(7, article.getUpdatedAt().toString());
            preparedStatement.setString(8, article.getDeletedAt() != null ? article.getDeletedAt().toString() : null);
            preparedStatement.setInt(9, article.getStatus());
            preparedStatement.setLong(10, article.getId());

            preparedStatement.execute();
            connection.close();
        } catch (SQLException e) {
            System.out.println("Có lỗi xảy ra, vui lòng thử lại sau.");
            e.printStackTrace();
        }
        return article;
    }

    @Override
    public void deleteByUrl(String url) {
        try {
            Connection connection = DriverManager.getConnection(MYSQL_CONNECTION_STRING, MYSQL_USERNAME, MYSQL_PASSWORD);
            String prepareSql = "DELETE FROM article WHERE baseUrl = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(prepareSql);
            preparedStatement.setString(1, url);

            preparedStatement.execute();
            System.out.println("Xóa thành công");
            connection.close();
        } catch (SQLException e) {
            System.out.println("Có lỗi xảy ra, vui lòng thử lại sau.");
            e.printStackTrace();
        }

    }
}
