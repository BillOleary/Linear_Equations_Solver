import java.util.Scanner;

/*
* The program works by calculating a numerical value which represents a pattern for each line.
* A binary 0 represents a '.' character and a binary 1 represents a '*' character.
* The line *.*.* would be represented by a 10101 or decimal 21.
* The values are calculated for half the rows and stored in an array of equivalent length.
* To print out the pattern, we go through the array from 0 to rowLength/2 and then from rowLength/2 - 1 down to 0
* (Values are symmetrical along the middle row.)
*
 */
class Main {

    private static int seedValue;
    public static int matrixWidth;
    private static int midPoint;

    public static void main(String[] args) {
        // put your code here
        Scanner readIn = new Scanner(System.in);
        int matrixSize = readIn.nextInt();
        matrixWidth = matrixSize;
        midPoint =  (int) Math.ceil((double) matrixSize / 2);
        seedValue =  0b0000_0000_0000_0001;
        int mask =  (int) Math.pow(2, matrixSize - 1);


        //Make the last bit (related to the size of the matrix) equal to 1
        seedValue =  seedValue | mask;

        //The outer loop to print store half the bit oriented values in an array
        int smackBangInTheMiddle = midPoint;
        //Store the middle row value - hence we need an array size 1 more.
        int[] listOfStarPositions = new int[midPoint];
        int index = 0;
        while (smackBangInTheMiddle-- > 0) {
            listOfStarPositions[index++] = seedValue;
            //If at the middle row - bit values are all 1's to indicate a printout of "*"
            if (smackBangInTheMiddle <= 1) {
                seedValue =  (int) (Math.pow(2, matrixSize) - 1);
            } else {
                int higherOrderBits =  seedValue >> 1 & mask >> midPoint - smackBangInTheMiddle;
                int lowerOrderBits =  seedValue << 1 &  (int) Math.pow(2, midPoint - smackBangInTheMiddle);
                seedValue =  higherOrderBits | lowerOrderBits; //concatenate two halves to produce a new value.
            }
        }

        //Select which half of the row to print out.
        for (int count = 0; count < 2 * listOfStarPositions.length - 1; count++) {
            if (count < midPoint) {
                printOutPattern(listOfStarPositions[count]);
            } else {
                printOutPattern(listOfStarPositions[matrixSize - count - 1]);   //offset the printout by 1 to account
                                                                                //for the middle row of "*******"'s
            }
        }
    }

    /*
    * Given a patten as a number, print out the value as a combination of dots and stars.
    * @param value
    * Output to CLI
     */
    private static void printOutPattern(int value) {
        int width = matrixWidth;
        int count = 0;
        int newValue = value;
        while (width-- != 0) {
            System.out.print(++count == midPoint ? "* " : (newValue & 0x01) == 0 ? ". " : "* "); //value & 0x1 tests
                                                                                            // the if bit 0 is 0 or 1
            newValue = newValue >> 1;   //shift the value over to the right to get the new value to print
        }
        System.out.println();
    }

    /**************************NOT USED******************************/
    //Given a number find the position where the bit is 1
    public static int mapBitPositions ( short number){
        int count = 1;
        if (number == 0) {
            return -1;
        }
        else {
            if (number == 1) {
                return 1;
            }
        }
        while (number != 1) {
            number = (short) (number >> 1);
            count++;
            if (number == 1)
                break;
        }
        return count;
    }
    /************************END************************/

}

