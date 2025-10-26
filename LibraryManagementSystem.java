import java.sql.*;
import java.util.Scanner;

public class LibraryManagement {
    private static final String URL = "jdbc:mysql://localhost:3306/librarydb";
    private static final String USER = "root";
    private static final String PASSWORD = "yourpassword";

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
            while (true) {
                System.out.println("\n===== LIBRARY MANAGEMENT SYSTEM =====");
                System.out.println("1. Add Book");
                System.out.println("2. View Books");
                System.out.println("3. Issue Book");
                System.out.println("4. Return Book");
                System.out.println("5. Exit");
                System.out.print("Enter your choice: ");
                int choice = sc.nextInt();

                switch (choice) {
                    case 1 -> addBook(conn, sc);
                    case 2 -> viewBooks(conn);
                    case 3 -> issueBook(conn, sc);
                    case 4 -> returnBook(conn, sc);
                    case 5 -> {
                        System.out.println("Thank you for using the system!");
                        return;
                    }
                    default -> System.out.println("Invalid choice! Try again.");
                }
            }
        } catch (SQLException e) {
            System.out.println("Database connection error: " + e.getMessage());
        }
    }

    static void addBook(Connection conn, Scanner sc) throws SQLException {
        System.out.print("Enter Book ID: ");
        int id = sc.nextInt();
        sc.nextLine();
        System.out.print("Enter Book Title: ");
        String title = sc.nextLine();
        System.out.print("Enter Author: ");
        String author = sc.nextLine();

        String query = "INSERT INTO books (id, title, author, issued) VALUES (?, ?, ?, false)";
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setInt(1, id);
        ps.setString(2, title);
        ps.setString(3, author);
        ps.executeUpdate();
        System.out.println("Book added successfully!");
    }

    static void viewBooks(Connection conn) throws SQLException {
        String query = "SELECT * FROM books";
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery(query);

        System.out.println("\n--- BOOK LIST ---");
        while (rs.next()) {
            System.out.printf("ID: %d | Title: %s | Author: %s | Issued: %s%n",
                    rs.getInt("id"), rs.getString("title"), rs.getString("author"),
                    rs.getBoolean("issued") ? "Yes" : "No");
        }
    }

    static void issueBook(Connection conn, Scanner sc) throws SQLException {
        System.out.print("Enter Book ID to issue: ");
        int id = sc.nextInt();
        String query = "UPDATE books SET issued = true WHERE id = ?";
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setInt(1, id);
        int rows = ps.executeUpdate();
        System.out.println(rows > 0 ? "Book issued successfully!" : "Book not found!");
    }

    static void returnBook(Connection conn, Scanner sc) throws SQLException {
        System.out.print("Enter Book ID to return: ");
        int id = sc.nextInt();
        String query = "UPDATE books SET issued = false WHERE id = ?";
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setInt(1, id);
        int rows = ps.executeUpdate();
        System.out.println(rows > 0 ? "Book returned successfully!" : "Book not found!");
    }
}
