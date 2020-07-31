class Publication {

    /*****************FOR TESTING*********************/
    /*
    public static void main(String[] args) {
        Publication pub = new Publication("The new Era");
        System.out.println(pub.getInfo());
        System.out.println("*************************");
        pub = new Article("Why the sun is hot", "Dr James Smith");
        System.out.println(pub.getInfo());
        System.out.println("*************************");
        pub = new Announcement("Will sell a house", 30);
        System.out.println(pub.getInfo());
        System.out.println("*************************");
        pub = new Newspaper("Football results;", "Sport news");
        System.out.println(pub.getInfo());
    }
    */

    private String title;

    public Publication(String title) {
        this.title = title;
    }

    public final String getInfo() {
        // write your code here
        return getType() + getDetails();

    }

    public String getType() {
        return "Publication: ";
    }

    public String getDetails() {
        return title.replace(";", "");
    }

}

class Newspaper extends Publication {

    private String source;

    public Newspaper(String title, String source) {
        super(title);
        this.source = source;
    }

    // write your code here
    @Override
    public String getType() {
        return "Newspaper ";
    }

    @Override
    public String getDetails() {
        return  "(source - " + source + "): " + super.getDetails();
    }
}

class Article extends Publication {

    private String author;

    public Article(String title, String author) {
        super(title);
        this.author = author;
    }

    // write your code here


    @Override
    public String getType() {
        return "Article ";
    }

    @Override
    public String getDetails() {
        return "(author - " + author + "): " + super.getDetails();
    }
}

class Announcement extends Publication {

    private int daysToExpire;

    public Announcement(String title, int daysToExpire) {
        super(title);
        this.daysToExpire = daysToExpire;
    }

    // write your code here


    @Override
    public String getType() {
        return "Announcement ";
    }

    @Override
    public String getDetails() {
        return "(days to expire - " + daysToExpire + "): " + super.getDetails();
    }
}