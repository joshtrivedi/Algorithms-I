import java.util.Arrays;
import java.util.ArrayList;


public class Board {
    private final int n;
    private final int[][] board;

    public Board(int[][] tiles)
    {
        n = tiles.length;
        board = copyBoardContent(tiles);
    }

    public String toString()
    {
        StringBuilder s = new StringBuilder();
        s.append(n + "\n");

        for(int i = 0 ; i < n ; i++) 
        {
            for(int j = 0 ; j < n ; j++)
            {
                s.append(String.format("%2d ", board[i][j]));
            } 
            s.append("\n");
        }
        return s.toString();
    }

    public int dimension() {
        return n;
    }

    //TODO DISTANCES

//is this board the one we need
    public boolean isGoal() {
        for (int row = 0; row < n; row++)
            for (int column = 0; column < n; column++)
                if (board[row][column] != solution(row, column))
                    return false;
        return true;
    }
//does this board equal y
    public boolean equals(Object object)
    {
        if (object == this) return true;
        if (object == null) return false;
        if (object.getClass() != this.getClass()) return false;

        Board otherBoard = (Board) object;

        return Arrays.deepEquals(this.board, otherBoard.board);
    }

    public Iterable<Board> neighbors() {
        int blankRow = 0, blankCol = 0;
        boolean blankFound = false;
        ArrayList<Board> neighbors = new ArrayList<Board>();

        for (blankRow = 0; blankRow < n; blankRow++) {
            for (blankCol = 0; blankCol < n; blankCol++)
                if (board[blankRow][blankCol] == 0){
                    blankFound = true;
                    break;
                }
            if (blankFound) break;
        }
        // Top neighbor
        if (blankRow > 0)
            neighbors.add(copyFromOriginalAndSwapTiles(blankRow, blankCol, blankRow - 1, blankCol));

        // Left neighbor
        if (blankCol > 0)
            neighbors.add(copyFromOriginalAndSwapTiles(blankRow, blankCol, blankRow, blankCol - 1));

        // Right neighbor
        if (blankCol < (n - 1))
            neighbors.add(copyFromOriginalAndSwapTiles(blankRow, blankCol, blankRow, blankCol + 1));

        // Bottom neighbor
        if (blankRow < (n - 1))
            neighbors.add(copyFromOriginalAndSwapTiles(blankRow, blankCol, blankRow + 1, blankCol));

        return neighbors;
    }
    public Board twin() {
        int swappingRow, swappingCol, twinRow, twinCol;
        swappingRow = swappingCol = 0;
        twinCol = twinRow = n - 1;

        if (board[swappingRow][swappingCol] == 0)
            swappingCol++;

        if (board[twinRow][twinCol] == 0)
            twinCol--;

        return copyFromOriginalAndSwapTiles(swappingRow, swappingCol, twinRow, twinCol);
    }

    private Board copyFromOriginalAndSwapTiles(int originalRow, int originalCol, int newRow, int newCol) {
        int[][] boardCopy = copyBoardContent(board);

        boardCopy[originalRow][originalCol] = board[newRow][newCol];
        boardCopy[newRow][newCol] = board[originalRow][originalCol];

        return new Board(boardCopy);
    }

    private int[][] copyBoardContent(int[][] original) {
        int[][] destination = new int[n][n];

        for (int row = 0; row < n; row++)
            for (int col = 0; col < n; col++)
                destination[row][col] = original[row][col];

        return destination;
    }

    private int solution(int row, int column) {
        if (row == (n-1) && column == (n-1)) return 0;
        return row*n + column + 1;
    }

    

}