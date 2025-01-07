import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DatabaseValidation {

    public static void main(String[] args) {
        // إعداد Selenium (إذا احتجت تشغيل المتصفح)
        System.setProperty("webdriver.chrome.driver", "path/to/chromedriver");
        WebDriver driver = new ChromeDriver();

        // إعداد الاتصال بقاعدة البيانات
        String url = "jdbc:mysql://localhost:3306/Warehouse";
        String user = "root";
        String password = "root";

        try {
            Connection conn = DriverManager.getConnection(url, user, password);

            executeQuery(conn, "SELECT * FROM Warehouse;");

            executeQuery(conn, "SELECT * FROM Warehouse WHERE IsActivated = 1;");

            executeQuery(conn, "SELECT * FROM Warehouse WHERE IsDeleted = 1;");

            executeQuery(conn, "SELECT * FROM Warehouse WHERE Code IS NULL OR Code = '';");

            executeQuery(conn, "SELECT * FROM Warehouse WHERE HasSerials = 1;");

            conn.close();
            driver.quit();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void executeQuery(Connection conn, String query) {
        try {
            PreparedStatement pstmt = conn.prepareStatement(query);
            ResultSet rs = pstmt.executeQuery();

            int rowCount = 0;
            while (rs.next()) {
                rowCount++;
                System.out.println("Row " + rowCount + ": " +
                        rs.getInt("Id") + " - " +
                        rs.getString("Name") + " - " +
                        rs.getString("Code"));
            }
            if (rowCount == 0) {
                System.out.println("No data found for query: " + query);
            } else {
                System.out.println("Query executed successfully: " + query);
            }

            rs.close();
            pstmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
