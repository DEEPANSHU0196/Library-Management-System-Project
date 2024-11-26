import java.util.Scanner;

class Book {
    String title;
    String author;
    String genre;
    String isbn;
    boolean isAvailable;
    String borrowedBy;
    String reservedBy;
    int dueDate;

    public Book(String title, String author, String genre, String isbn) {
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.isbn = isbn;
        this.isAvailable = true;
        this.borrowedBy = null;
        this.reservedBy = null;
        this.dueDate = -1;
    }
}

public class LibrarySystem {
    static Book[] books = new Book[100];
    static int bookCount = 0;

    public static void addBook(String title, String author, String genre, String isbn) {
        books[bookCount++] = new Book(title, author, genre, isbn);
        System.out.println("Book added successfully!");
    }

    public static void searchBook(String query) {
        System.out.println("Search Results:");
        for (int i = 0; i < bookCount; i++) {
            if (books[i].title.contains(query) || books[i].author.contains(query) ||
                books[i].genre.contains(query) || books[i].isbn.contains(query)) {
                System.out.println(books[i].title + " by " + books[i].author +
                        " (Genre: " + books[i].genre + ", ISBN: " + books[i].isbn +
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
                System.out.println("Book borrowed successfully! Due in " + returnAfterDays + " days.");
                return;
            }
        }
        System.out.println("Book is not available or does not exist.");
    }

    public static void returnBook(String title, String user, int currentDay) {
        for (int i = 0; i < bookCount; i++) {
            if (books[i].title.equalsIgnoreCase(title) && user.equals(books[i].borrowedBy)) {
                if (currentDay > books[i].dueDate) {
                    int daysOverdue = currentDay - books[i].dueDate;
                    System.out.println("Book is overdue! Penalty: " + daysOverdue * 10 + " units.");
                }

                books[i].isAvailable = true;
                books[i].borrowedBy = null;
                books[i].dueDate = -1;

                if (books[i].reservedBy != null) {
                    System.out.println("Book reserved by " + books[i].reservedBy + " will be notified.");
                    books[i].isAvailable = false;
                    books[i].borrowedBy = books[i].reservedBy;
                    books[i].reservedBy = null;
                }

                System.out.println("Book returned successfully!");
                return;
            }
        }
        System.out.println("Book not found or user mismatch.");
    }

    public static void reserveBook(String title, String user) {
        for (int i = 0; i < bookCount; i++) {
            if (books[i].title.equalsIgnoreCase(title) && !books[i].isAvailable && books[i].reservedBy == null) {
                books[i].reservedBy = user;
                System.out.println("Book reserved successfully!");
                return;
            }
        }
        System.out.println("Book is either available or already reserved.");
    }

    public static void main(String[] args) {
        addBook("Java Programming", "John Doe", "Programming", "12345");
        addBook("Cyber Security Essentials", "Deepanshu Yadav", "Cybersecurity", "67890");
        addBook("Data Structures", "Alice Smith", "Programming", "11111");
        addBook("Operating Systems", "Bob Johnson", "Technology", "22222");
        addBook("Artificial Intelligence", "Carol Lee", "AI", "33333");
        addBook("Networking Basics", "David Kim", "Networking", "44444");
        addBook("Database Management", "Eve Adams", "Database", "55555");
        addBook("Web Development", "Frank Wright", "Web", "66666");
        addBook("Machine Learning", "Grace Hopper", "AI", "77777");
        addBook("Cyber Defense", "Alan Turing", "Cybersecurity", "88888");

        System.out.println("Library Management System");
        System.out.println("1. Search  2. Borrow  3. Return  4. Reserve  5. Add Book  6. Exit");

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("\nEnter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Enter search query: ");
                    searchBook(scanner.nextLine());
                    break;
                case 2:
                    System.out.print("Enter title, user, current day (e.g., 1-365), return after days: ");
                    borrowBook(scanner.nextLine(), scanner.nextLine(), scanner.nextInt(), scanner.nextInt());
                    scanner.nextLine();
                    break;
                case 3:
                    System.out.print("Enter title, user, and current day: ");
                    returnBook(scanner.nextLine(), scanner.nextLine(), scanner.nextInt());
                    scanner.nextLine();
                    break;
                case 4:
                    System.out.print("Enter title and user: ");
                    reserveBook(scanner.nextLine(), scanner.nextLine());
                    break;
                case 5:
                    System.out.print("Enter title, author, genre, and ISBN: ");
                    addBook(scanner.nextLine(), scanner.nextLine(), scanner.nextLine(), scanner.nextLine());
                    break;
                case 6:
                    System.out.println("Exiting system...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }
}


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

    public static void removeBook(String isbn) {
        for (int i = 0; i < bookCount; i++) {
            if (books[i].isbn.equals(isbn)) {
                for (int j = i; j < bookCount - 1; j++) {
                    books[j] = books[j + 1]; // Shift books to fill the gap
                }
                books[--bookCount] = null; // Reduce book count and clear the last element
                System.out.println("Book removed successfully!");
                return;
            }
        }
        System.out.println("Book not found.");
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

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Pre-load some books
        addBook("Java Programming", "John Doe", "Programming", "12345", "2020-01-01");
        addBook("Cyber Security Essentials", "Deepanshu Yadav", "Cybersecurity", "67890", "2021-05-15");
        addBook("Data Structures", "Alice Smith", "Programming", "11111", "2019-03-22");

        System.out.println("Welcome to the Library Management System!");
        System.out.print("Enter role (user/librarian): ");
        String role = scanner.nextLine().trim().toLowerCase();

        if (role.equals("user")) {
            // User Menu
            System.out.println("User Menu: 1. Search  2. Exit");
            while (true) {
                System.out.print("\nEnter your choice: ");
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                if (choice == 1) {
                    System.out.print("Enter search query: ");
                    searchBook(scanner.nextLine());
                } else if (choice == 2) {
                    System.out.println("Exiting...");
                    break;
                } else {
                    System.out.println("Invalid choice!");
                }
            }
        } else if (role.equals("librarian")) {
            // Librarian Menu
            System.out.println("Librarian Menu: 1. Add Book  2. Edit Book  3. Remove Book  4. Search  5. Exit");
            while (true) {
                System.out.print("\nEnter your choice: ");
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline

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
                        System.out.print("Enter ISBN of the book to remove: ");
                        removeBook(scanner.nextLine());
                        break;
                    case 4:
                        System.out.print("Enter search query: ");
                        searchBook(scanner.nextLine());
                        break;
                    case 5:
                        System.out.println("Exiting...");
                        return;
                    default:
                        System.out.println("Invalid choice!");
                }
            }
        } else {
            System.out.println("Invalid role! Exiting...");
        }
        scanner.close();
    }
}
