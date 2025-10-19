class Main {
    public static void main(String[] args) {
        // Create book and library instances
        Book book1 = new Book("Harry Potter", "J.K. Rowling", 10.0, 2);
        Library lib = new Library(book1, "Perpustakaan Kota");

        // Display initial information
        System.out.println("--- Initial Book Information ---");
        lib.showLibraryInfo();
        System.out.println(); // a blank line for spacing

        // Add more stock
        book1.adjustStock(5);
        System.out.println(); // a blank line for spacing

        // Display updated information
        System.out.println("--- Updated Book Information ---");
        lib.showLibraryInfo();
    }
}