import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/*
*   The code works by first finding a solution to which rows should be checked.
* If a row has less seats than the number of consecutive seats it is discarded.
* A Map containing the required rows to check is built up and this is then
* checked for matching adjacent seats.
 */
class Main {
    public static void main(String[] args) {
        // put your code here
        Scanner readIn = new Scanner(System.in);

        int rowLength = readIn.nextInt();
        int colLength = readIn.nextInt();
        readIn.nextLine();
        String[][] cinemaSeating = new String[rowLength][colLength];
        int index = 0;
        int numberOfConsecutiveSeatsAtRow = 0;
        int nextIndex;
        Map<Integer,Integer> emptySeats = new LinkedHashMap<>();

        while (index != rowLength) {
            cinemaSeating[index] = readIn.nextLine().split(" ");
            //count the number of empty seats in each row.  We will use this to prune row elements
            int numberOfEmptySeats = (int) Stream.of(cinemaSeating[index]).
                    filter(element -> element.matches("0")).
                    count();
            emptySeats.put(index,numberOfEmptySeats);
            index++;
        }
        //read the number or consecutive seats required by the user.
        int kConsecutiveFreeSeats = readIn.nextInt();

        //Get a map of all elements where the number of free seats is greater than the number of consecutive free seats.
        //Helps in weeding out rows which do not need to be checked
        Map<Integer, Integer> onlyRowsWithGreaterElements = emptySeats.
                entrySet().
                stream().
                filter(elements -> elements.getValue() >= kConsecutiveFreeSeats).
                collect(Collectors.toMap(k -> k.getKey(), v -> v.getValue()));
        //readIn.nextLine();  //dummy read
        int sum = 0;
        //start reading
        int[] testSeating = new int[kConsecutiveFreeSeats]; //Array of k seats

        //If there are NO elements in the map then we have NO rows with the required seats
        if (onlyRowsWithGreaterElements.size() == 0) {
            numberOfConsecutiveSeatsAtRow = 0;
        } else {
            for(int key : onlyRowsWithGreaterElements.keySet()){
                sum = 0;
                //get the row to be tested
                for (int colIndex = 0; colIndex < colLength; colIndex++) {
                    if (colIndex < kConsecutiveFreeSeats) {
                        testSeating[colIndex] = Integer.parseInt(cinemaSeating[key][colIndex]);
                        sum += testSeating[colIndex];
                        continue;
                    } else {
                        if (sum == 0) {
                            break;
                        }
                        nextIndex = colIndex % kConsecutiveFreeSeats;
                        //Save the old value
                        int prevValue = testSeating[nextIndex];
                        int currentValue = Integer.parseInt(cinemaSeating[key][colIndex]);
                        testSeating[nextIndex] = currentValue;
                        //Testing array
                        sum += currentValue - prevValue;
                    }
                }//end for
                if (sum == 0){
                    numberOfConsecutiveSeatsAtRow = key + 1;
                    break;
                }
            }//end for
        }//end else
        System.out.println(numberOfConsecutiveSeatsAtRow);
    }
}