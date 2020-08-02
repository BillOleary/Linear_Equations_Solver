import java.util.Arrays;

class Book {

    private String title;
    private int yearOfPublishing;
    private String[] authors;

    public Book(String title, int yearOfPublishing, String[] authors) {
        this.title = title;
        this.yearOfPublishing = yearOfPublishing;
        this.authors = authors;
    }

    @Override
    public String toString() {
        return "title=" + title +
                ",yearOfPublishing=" + yearOfPublishing +
                ",authors=" + Arrays.toString(authors).replace(", ", ",");
    }

//    public static void main(String[] args) {
//        System.out.println(new Book("Java 8 & 9 in Action", 2017, new String[]{"Mario Fusco","Alan Mycroft"}).toString());
//    }
}