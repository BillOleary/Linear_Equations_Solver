class Cat {

    // write static and instance variables
    private String name;
    private int age;
    private static int counter;


    public Cat(String name, int age) {
        // implement the constructor
        this.name = name;
        this.age = age;
        counter++;
        if (getNumberOfCats() > 5) {
            System.out.println("You have too many cats");
        }
    }

    public static int getNumberOfCats() {
        // implement the static method
        return counter;
    }
}