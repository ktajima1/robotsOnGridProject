import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
/* Use memorization
 * Maybe store cache in another matrix?
 * Split the illegal robot method from the legal (make another recursive method with a copy of matrix)
 */
public class robots {
    public static void main(String[] args) {
        /*
        - starts from char[0][0]
        - reaching char[n][n] signifies the end goal has been reached: equivalent to char[map.length-1][map.length-1]
        - perhaps if row && column = map.length-1, then the edge of map has been reached (map[n][n])
        - recursive method must check whether the next space exists or not

         */
        try {
            /* Create a char[][] to the specified size (determined by first line of input) and fill the grid with the
             * given input Strings. The first line of the input will be one number (n) that sets the height (rowSize)
             * and length (columnSize) of the grid. The input will have to be parsed and respectively stored into
             * rowSize and columnSize variables which will be used to make the char[][] array. This method assumes that
             * the first line of the input will always include size specifications for rowSize and columnSize.
             *
             * After creating an empty char[rowSize][columnSize] matrix, the method fills the grid with the
             * given String inputs by calling br.readLine() in a while loop until the readLine() methods reaches the end
             * of the input (empty line). */
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            String currentLine;
            if((currentLine = br.readLine())!=null) {
                //If the input is empty, then terminate the program
                if(currentLine.equals("")) return;
                //Set rowSize to equal the first integer of the input
                int sizeSetting = Integer.parseInt(currentLine);
                /* Create a char matrix with rowSize * columnSize specifications */
                char[][] map = new char[sizeSetting][sizeSetting];

                /* Record the remaining string inputs (that describe the map of space) into the char matrix */
                String currentRow;
                for (int i = 0; i < sizeSetting; i++) {
                    currentRow = br.readLine();
                    map[i] = currentRow.toCharArray();
                }

                int pathCount = findPath(map);
                if(pathCount>0) { System.out.println(pathCount);}
                else {
                    if(illegalFindPath(map)!=0) {System.out.println("THE GAME IS A LIE");}
                    else{System.out.println("INCONCEIVABLE");}
                }

                //Used for printing matrix content
                for (int i = 0; i < map.length; i++) {
                    for (int j = 0; j < map[i].length; j++)
                        System.out.print(map[i][j] + " ");
                    System.out.println();
                }
            }
        } catch(IOException e) {
            System.out.println("Problem reading map");
            e.printStackTrace();
        }
    }

    //calls recursiveFindPath() without left and upward movement enabled
    static int findPath(char[][] map) {
        char[][] memoGrid = new char[map.length][map.length];
        return recursiveFindPath(map, 0, 0, false);
    }

    //calls recursiveFindPath() with cheats enabled
    static int illegalFindPath(char[][] map) {
        char[][] memoGrid = new char[map.length][map.length];
        return recursiveFindPath(map, 0, 0, true);
    }
    /*
    Problem: should we consider whether robot is going in the same direction is a new path?
     */
    static int recursiveFindPath(char[][] map, int rowIndex, int columnIndex, boolean leftAndUpEnabled) {
        //Robot has moved out of bounds (off the map), go back to where you came!
        if(rowIndex<0 || columnIndex<0 || rowIndex>=map.length || columnIndex>=map[0].length) { return 0; }
        //The way is blocked, or the space has been visited previously (not a path!), go back
        if(map[rowIndex][columnIndex]=='#' || map[rowIndex][columnIndex]=='O') {return 0;}
        //A path has successfully been found! Return a '1' to increment the path count
        if((rowIndex == map.length-1) && (columnIndex==map.length-1)) {return 1;}

        //The path is clear! However, the robot can only move rightward and downward, so
        // recursively find a path to the right and bottom! Essentially, go right and
        // down the matrix to explore remaining paths
        if(map[rowIndex][columnIndex]=='.' && !leftAndUpEnabled) {
            return recursiveFindPath(map, rowIndex+1, columnIndex, leftAndUpEnabled)
                    + recursiveFindPath(map, rowIndex, columnIndex+1, leftAndUpEnabled);
        }
        //The path is clear! The robot is able to move in all directions as well, so recursively find a path by exploring
        //the left, right, upward, and downward path! However, to prevent the robot from running in circles, any space
        //already traversed will be marked as visited via the 'O' character. So, all '.' will be changed to 'O'. This way,
        //the robot will not get stuck
        if(map[rowIndex][columnIndex]=='.' && leftAndUpEnabled) {
            map[rowIndex][columnIndex]='O';
            return recursiveFindPath(map, rowIndex+1, columnIndex, leftAndUpEnabled)
                    + recursiveFindPath(map, rowIndex-1, columnIndex, leftAndUpEnabled)
                    + recursiveFindPath(map, rowIndex, columnIndex+1, leftAndUpEnabled)
                    + recursiveFindPath(map, rowIndex, columnIndex-1, leftAndUpEnabled);
        }

        //Needs a return statement here, this is placeholder for now, not sure what to put instead
        return 0;
    }
}
