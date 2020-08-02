class Time {

    private int hours;
    private int minutes;
    private int seconds;

    public Time(int hours, int minutes, int seconds) {
        this.hours = hours;
        this.minutes = minutes;
        this.seconds = seconds;
    }

    @Override
    public String toString() {
        return  (hours / 10 == 0 ? ("0" + hours) : hours) + ":" +
                (minutes / 10 == 0 ? ("0" + minutes) : minutes) + ":" +
                (seconds / 10 == 0 ? ("0" + seconds) : seconds);

    }

//    public static void main(String[] args) {
//        Time t = new Time(23, 59, 59);
//        System.out.println(t.toString());
//    }
}