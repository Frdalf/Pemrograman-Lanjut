// class Library to store library location and a book
class Library {
    private Book book;
    private String location;

    public Library(Book book, String location) {
        this.setBook(book);
        this.setLocation(location);
    }

    // Display library and book information
    public void showLibraryInfo() {
        System.out.println("Library Location: " + getLocation());
        getBook().displayInfo();
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}