
package cs2114.minesweeper;

import java.util.Random;

/**
 * Creates a MineSweeperBoard object that has a width, height, number of mines
 *
 * @author Krishan Madan
 * @version Jan 27, 2016
 */
public class MineSweeperBoard
    extends MineSweeperBoardBase
{
    private int                 width;
    private int                 height;

    private MineSweeperCell[][] x;


    /**
     * Creates a MineSweeperBoard object that has a width, height, number of
     * mines
     *
     * @param width
     *            is width of board
     * @param height
     *            is height of board
     * @param numberofmines
     *            is the number of mines
     */
    public MineSweeperBoard(int width, int height, int numberofmines)
    {
        this.width = width;
        this.height = height;

        x = new MineSweeperCell[width][height];
        // place mines at random locations
        // cover all the cells
        for (int i = 0; i < width; i++)
        {
            for (int j = 0; j < height; j++)
            {
                x[i][j] = MineSweeperCell.COVERED_CELL;
            }
        }
        Random generator = new Random();
        int y = 0;
        int a = generator.nextInt(width);
        int b = generator.nextInt(height);
        while (y < numberofmines)

        {
            if (x[a][b] != MineSweeperCell.MINE)
            {
                x[a][b] = MineSweeperCell.MINE;

                y++;
            }
            a = generator.nextInt(width);
            b = generator.nextInt(height);
        }

    }

     // ----------------------------------------------------------
    /**
     * Uncover the specified cell. If the cell already contains a flag it should
     * not be uncovered. If there is not a mine under the specified cell then
     * the value in that cell is changed to the number of mines that appear in
     * adjacent cells. If there is a mine under the specified cell the game is
     * over and the player has lost. If the specified cell is already uncovered
     * or is invalid, no change is made to the board.
     *
     * @param c
     *            the column of the cell to be uncovered.
     * @param d
     *            the row of the cell to be uncovered.
     */
    public void uncoverCell(int c, int d)
    {

        if (getCell(c, d) == MineSweeperCell.MINE)
        {
            this.setCell(c, d, MineSweeperCell.UNCOVERED_MINE);
            // isGameLost();
        }
        if (getCell(c, d) == MineSweeperCell.COVERED_CELL)
        {
            setCell(
                c,
                d,
                MineSweeperCell.adjacentTo(numberOfAdjacentMines(c, d)));
        }
    }

    // ----------------------------------------------------------
    /**
     * Count the number of mines that appear in cells that are adjacent to the
     * specified cell.
     *
     * @param c
     *            the column of the cell.
     * @param d
     *            the row of the cell.
     * @return the number of mines adjacent to the specified cell.
     */
    public int numberOfAdjacentMines(int c, int d)
    {
        int count = 0;

        for (int i = c - 1; i <= c + 1; i++)
        {
            for (int j = d - 1; j <= d + 1; j++)
            {

                if (getCell(i, j) == MineSweeperCell.MINE
                    || getCell(i, j) == MineSweeperCell.UNCOVERED_MINE
                    || getCell(i, j) == MineSweeperCell.FLAGGED_MINE)
                {
                    count++;
                }
            }

        }
        System.out.println(count + " ");
        return count;

    }

    /**
     * This method tests FlagCell.
     * @param c is the column
     * @param d is the row
     */
    public void flagCell(int c, int d)
    {
        if (x[c][d] == MineSweeperCell.MINE)
        {
            this.setCell(c, d, MineSweeperCell.FLAGGED_MINE);
        }

        else if (x[c][d] == MineSweeperCell.COVERED_CELL)

        {
            this.setCell(c, d, MineSweeperCell.FLAG);

        }
       /** else if (x[c][d] == MineSweeperCell.FLAG
            || x[c][d] == MineSweeperCell.FLAGGED_MINE)
        {
            return;

        }**/

    }

    /**
     * Get the contents of the specified cell on this MineSweeperBoard. The
     * value returned from this method must be one of the values from the
     * {@link MineSweeperCell} enumerated type.
     *
     * @param c the column containing the cell.
     * @param d the row containing the cell.
     * @return the value contained in the cell specified by x and y, or
     *         INVALID_CELL if the specified cell does not exist.
     */
    public MineSweeperCell getCell(int c, int d)
    {
        if (c < 0 || c >= x.length || d < 0 || d >= x[0].length)
        {
            return MineSweeperCell.INVALID_CELL;
        }

        else
        {
            return x[c][d];
        }

    }

    // ----------------------------------------------------------
    /**
     * Determine if the player has lost the current game. The game is lost if
     * the player has uncovered a mine.
     *
     * @return true if the current game has been lost and false otherwise
     */
    public boolean isGameLost()
    {
        boolean iGL = false;
        for (int i = 0; i < x.length; i++)
        {
            for (int j = 0; j < x[i].length; j++)
            {
                if (x[i][j] == MineSweeperCell.UNCOVERED_MINE)
                {
                    iGL = true;
                }

            }
        }
        return iGL;
    }

    //~ Protected Methods .....................................................

    // ----------------------------------------------------------
    /**
     * Set the contents of the specified cell on this MineSweeperBoard. The
     * value passed in should be one of the defined constants in the
     * {@link MineSweeperCell} enumerated type.
     *
     * @param a the column containing the cell
     * @param b the row containing the cell
     * @param value the value to place in the cell
     */
    protected void setCell(int a, int b, MineSweeperCell value)
    {
        if (getCell(a, b) != MineSweeperCell.INVALID_CELL)
        {
            x[a][b] = value;
        }
    }

    // ----------------------------------------------------------
    /**
     * Determine if the player has won the current game. The game is won when
     * three conditions are met:
     *
     * <ol>
     * <li>Flags have been placed on all of the mines.</li>
     * <li>No flags have been placed incorrectly.</li>
     * <li>All non-flagged cells have been uncovered.</li>
     * </ol>
     *
     * @return true if the current game has been won and false otherwise.
     */
    public boolean isGameWon()
    {
        boolean winner = true;
        for (int i = 0; i < x.length; i++)
        {
            for (int j = 0; j < x[i].length; j++)
            {
                if (x[i][j] == MineSweeperCell.UNCOVERED_MINE
                    || x[i][j] == MineSweeperCell.COVERED_CELL
                    || x[i][j] == MineSweeperCell.FLAG
                    || x[i][j] == MineSweeperCell.MINE)
                {
                    winner = false;
                }

            }
        }
        return winner;
    }

    // ----------------------------------------------------------
    /**
     * Uncover all of the cells on the board.
     */
    public void revealBoard()
    {
        for (int i = 0; i < x.length; i++)
        {
            for (int j = 0; j < x[i].length; j++)
            {
                this.uncoverCell(i, j);
            }

        }
    }


    @Override
    public int width()
    {

        return width;
    }


    @Override
    public int height()
    {

        return height;
    }

}
