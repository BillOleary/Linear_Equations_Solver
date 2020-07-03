import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        // put your code here
        Scanner readIn = new Scanner(System.in);
        int rowLength = readIn.nextInt();
        int colLength = readIn.nextInt();
        readIn.nextLine();  //move to the next Line - dummy read

        String[][] matrix = new String[rowLength][colLength];
        
        for (int index = 0; index < rowLength; index++) {
            matrix[index] = readIn.nextLine().split(" ");
        }

        //String[][] rotated90Matrix = new String[colLength][rowLength];
        //rotated90Matrix[colIndex][rowIndex] = matrix[rowLength - rowIndex - 1][colIndex];
        for (int colIndex = 0; colIndex < colLength; colIndex++) {
            for (int rowIndex = 0; rowIndex < rowLength; rowIndex++) {
                System.out.print(matrix[rowLength - rowIndex - 1][colIndex] + " ");
            }
            System.out.println();
        }

    }
}