class Book {
    String title;
    String author;
    String genre;
    String isbn;
    boolean isAvailable;
    String borrowedBy;
    String reservedBy;
    int dueDate; // Represented as days (e.g., day of the year)

    public Book(String title, String author, String genre, String isbn) {
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.isbn = isbn;
        this.isAvailable = true;
        this.borrowedBy = null;
        this.reservedBy = null;
        this.dueDate = -1; // -1 means no due date set
    }
}

public class LibrarySystem {
    static Book[] books = new Book[100]; // Fixed size array for simplicity
    static int bookCount = 0;

    // Add a book to the system
    public static void addBook(String title, String author, String genre, String isbn) {
        books[bookCount++] = new Book(title, author, genre, isbn);
        System.out.println("Book added successfully!");
    }

    // Search for a book by title, author, genre, or ISBN
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

    // Borrow a book
    public static void borrowBook(String title, String user, int currentDay, int returnAfterDays) {
        for (int i = 0; i < bookCount; i++) {
            if (books[i].title.equalsIgnoreCase(title) && books[i].isAvailable) {
                books[i].isAvailable = false;
                books[i].borrowedBy = user;
                books[i].dueDate = currentDay + returnAfterDays; // Set the due date
                System.out.println("Book borrowed successfully! Due in " + returnAfterDays + " days.");
                return;
            }
        }
        System.out.println("Book is not available or does not exist.");
    }

    // Return a book
    public static void returnBook(String title, String user, int currentDay) {
        for (int i = 0; i < bookCount; i++) {
            if (books[i].title.equalsIgnoreCase(title) && user.equals(books[i].borrowedBy)) {
                // Check for overdue
                if (currentDay > books[i].dueDate) {
                    int daysOverdue = currentDay - books[i].dueDate;
                    System.out.println("Book is overdue! Penalty: " + daysOverdue * 10 + " units.");
                }

                // Reset book status
                books[i].isAvailable = true;
                books[i].borrowedBy = null;
                books[i].dueDate = -1;

                // Notify reserved user if applicable
                if (books[i].reservedBy != null) {
                    System.out.println("Book reserved by " + books[i].reservedBy + " will be notified.");
                    books[i].isAvailable = false; // Book goes to reserved user
                    books[i].borrowedBy = books[i].reservedBy;
                    books[i].reservedBy = null;
                }

                System.out.println("Book returned successfully!");
                return;
            }
        }
        System.out.println("Book not found or user mismatch.");
    }

    // Reserve a book
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
        // Add sample books
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
        System.out.println("1. Search  2. Borrow  3. Return  4. Reserve  5. Exit");

        java.util.Scanner scanner = new java.util.Scanner(System.in);

        while (true) {
            System.out.print("\nEnter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter search query: ");
                    searchBook(scanner.nextLine());
                    break;
                case 2:
                    System.out.print("Enter title, user, current day (e.g., 1-365), return after days: ");
                    borrowBook(scanner.nextLine(), scanner.nextLine(), scanner.nextInt(), scanner.nextInt());
                    scanner.nextLine(); // Consume newline
                    break;
                case 3:
                    System.out.print("Enter title, user, and current day: ");
                    returnBook(scanner.nextLine(), scanner.nextLine(), scanner.nextInt());
                    scanner.nextLine(); // Consume newline
                    break;
                case 4:
                    System.out.print("Enter title and user: ");
                    reserveBook(scanner.nextLine(), scanner.nextLine());
                    break;
                case 5:
                    System.out.println("Exiting system...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }
}
