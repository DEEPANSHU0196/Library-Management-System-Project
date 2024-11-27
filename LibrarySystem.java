import java.util.Scanner;

class Book {
    String title;
    String author;
    String genre;
    String isbn;
    String publicationDate;
    boolean isAvailable;
    String borrowedBy;
    String reservedBy;
    int dueDate;

    public Book(String title, String author, String genre, String isbn, String publicationDate) {
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.isbn = isbn;
        this.publicationDate = publicationDate;
        this.isAvailable = true;
        this.borrowedBy = null;
        this.reservedBy = null;
        this.dueDate = -1;
    }
}

public class LibrarySystem {
    static Book[] books = new Book[100];
    static int bookCount = 0;

    // Librarian methods
    public static void addBook(String title, String author, String genre, String isbn, String publicationDate) {
        books[bookCount++] = new Book(title, author, genre, isbn, publicationDate);
        System.out.println("Book added successfully!");
    }

    public static void editBook(String isbn, String newTitle, String newAuthor, String newGenre, String newPublicationDate) {
        for (int i = 0; i < bookCount; i++) {
            if (books[i].isbn.equals(isbn)) {
                books[i].title = newTitle;
                books[i].author = newAuthor;
                books[i].genre = newGenre;
                books[i].publicationDate = newPublicationDate;
                System.out.println("Book details updated successfully!");
                return;
            }
        }
        System.out.println("Book not found.");
    }

    public static void removeBook(String query) {
        boolean removed = false;
        for (int i = 0; i < bookCount; i++) {
            if (books[i].title.equalsIgnoreCase(query) || books[i].genre.equalsIgnoreCase(query)) {
                for (int j = i; j < bookCount - 1; j++) {
                    books[j] = books[j + 1];
                }
                books[--bookCount] = null;
                i--;
                removed = true;
            }
        }
        if (removed) {
            System.out.println("Book(s) removed successfully!");
        } else {
            System.out.println("No book found with the specified title or genre.");
        }
    }

    // User methods
    public static void searchBook(String query) {
        System.out.println("Search Results:");
        for (int i = 0; i < bookCount; i++) {
            if (books[i].title.contains(query) || books[i].author.contains(query) ||
                books[i].genre.contains(query) || books[i].isbn.contains(query)) {
                System.out.println(books[i].title + " by " + books[i].author +
                        " (Genre: " + books[i].genre + ", ISBN: " + books[i].isbn +
                        ", Publication Date: " + books[i].publicationDate +
                        ", Available: " + books[i].isAvailable + ")");
            }
        }
    }

    public static void borrowBook(String title, String user, int currentDay, int returnAfterDays) {
        for (int i = 0; i < bookCount; i++) {
            if (books[i].title.equalsIgnoreCase(title) && books[i].isAvailable) {
                books[i].isAvailable = false;
                books[i].borrowedBy = user;
                books[i].dueDate = currentDay + returnAfterDays;
                System.out.println("Book borrowed successfully! Due date: " + books[i].dueDate);
                return;
            }
        }
        System.out.println("Book is not available or does not exist.");
    }

    public static void returnBook(String title, String user, int currentDay) {
        for (int i = 0; i < bookCount; i++) {
            if (books[i].title.equalsIgnoreCase(title) && books[i].borrowedBy != null &&
                books[i].borrowedBy.equals(user)) {
                if (currentDay > books[i].dueDate) {
                    int overdueDays = currentDay - books[i].dueDate;
                    System.out.println("Book is overdue by " + overdueDays + " days. Penalty: " + (overdueDays * 10) + " units.");
                }
                books[i].isAvailable = true;
                String reservedBy = books[i].reservedBy;
                books[i].borrowedBy = null;
                books[i].dueDate = -1;

                System.out.println("Book returned successfully!");

                if (reservedBy != null) {
                    System.out.println("The book was reserved by '" + reservedBy + "'. Notifying them...");
                    System.out.println("User '" + reservedBy + "' has been notified that the book is now available.");
                    books[i].reservedBy = null;
                }
                return;
            }
        }
        System.out.println("Book not found or user mismatch.");
    }

    public static void reserveBook(String title, String user) {
        for (int i = 0; i < bookCount; i++) {
            if (books[i].title.equalsIgnoreCase(title)) {
                if (!books[i].isAvailable) {
                    if (books[i].reservedBy == null) {
                        books[i].reservedBy = user;
                        System.out.println("Book reserved successfully! User '" + user + "' will be notified when it's available.");
                    } else {
                        System.out.println("Book is already reserved by another user: " + books[i].reservedBy);
                    }
                    return;
                } else {
                    System.out.println("Book is available. No need to reserve.");
                    return;
                }
            }
        }
        System.out.println("Book not found.");
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        addBook("Java Programming", "John Doe", "Programming", "12345", "2020-01-01");
        addBook("Cyber Security Essentials", "Deepanshu Yadav", "Cybersecurity", "67890", "2021-05-15");
        addBook("Data Structures", "Alice Smith", "Programming", "11111", "2019-03-22");

        System.out.println("Welcome to the Library Management System!");

        while (true) {
            System.out.print("Enter role (user/librarian/exit): ");
            String role = scanner.nextLine().trim().toLowerCase();

            if (role.equals("exit")) {
                System.out.println("Exiting the system. Goodbye!");
                break;
            } else if (role.equals("user")) {
                System.out.println("User Menu: 1. Search  2. Borrow Book  3. Return Book  4. Exit to Role Menu");
                while (true) {
                    System.out.print("\nEnter your choice: ");
                    int choice = scanner.nextInt();
                    scanner.nextLine();

                    if (choice == 4) break;

                    switch (choice) {
                        case 1:
                            System.out.print("Enter search query: ");
                            searchBook(scanner.nextLine());
                            break;
                        case 2:
                            System.out.print("Enter title, user, current day (1-365), and days to borrow: ");
                            borrowBook(scanner.nextLine(), scanner.nextLine(), scanner.nextInt(), scanner.nextInt());
                            scanner.nextLine();
                            break;
                        case 3:
                            System.out.print("Enter title, user, and current day: ");
                            returnBook(scanner.nextLine(), scanner.nextLine(), scanner.nextInt());
                            scanner.nextLine();
                            break;
                        default:
                            System.out.println("Invalid choice!");
                    }
                }
            } else if (role.equals("librarian")) {
                System.out.println("Librarian Menu: 1. Add Book  2. Edit Book  3. Remove Book  4. Search  5. Reserve Book  6. Exit to Role Menu");
                while (true) {
                    System.out.print("\nEnter your choice: ");
                    int choice = scanner.nextInt();
                    scanner.nextLine();

                    if (choice == 6) break;

                    switch (choice) {
                        case 1:
                            System.out.print("Enter title, author, genre, ISBN, and publication date (yyyy-mm-dd): ");
                            addBook(scanner.nextLine(), scanner.nextLine(), scanner.nextLine(), scanner.nextLine(), scanner.nextLine());
                            break;
                        case 2:
                            System.out.print("Enter ISBN of the book to edit: ");
                            String isbn = scanner.nextLine();
                            System.out.print("Enter new title, author, genre, and publication date (yyyy-mm-dd): ");
                            editBook(isbn, scanner.nextLine(), scanner.nextLine(), scanner.nextLine(), scanner.nextLine());
                            break;
                        case 3:
                            System.out.print("Enter title or genre to remove books: ");
                            removeBook(scanner.nextLine());
                            break;
                        case 4:
                            System.out.print("Enter search query: ");
                            searchBook(scanner.nextLine());
                            break;
                        case 5:
                            System.out.print("Enter title of the book and user name to reserve: ");
                            reserveBook(scanner.nextLine(), scanner.nextLine());
                            break;
                        default:
                            System.out.println("Invalid choice!");
                    }
                }
            } else {
                System.out.println("Invalid role!");
            }
        }
        scanner.close();
    }
}
